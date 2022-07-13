# netty 的组件和设计

## 核心接口设计
### Channel、EventLoop 和 Future/ChannelFuture 接口
#### Channel
Netty网络通信的组件，能够用于执行网络I/O操作, 大大降低了 Java 的网络编程中直接使用 Socket 开发的复杂性, 基本功能：                                    
- 当前网络连接的通道的状态（例如是否打开？是否已连接？）
- 网络连接的配置参数 （例如接收缓冲区大小）
- 提供异步的网络I/O操作(如建立连接，读写，绑定端口)，异步调用意味着任何I / O调用都将立即返回，并且不保证在调用结束时所请求的I / O操作已完成。调用立即返回一个ChannelFuture实例，通过注册监听器到ChannelFuture上，可以I / O操作成功、失败或取消时回调通知调用方。
- 支持关联I/O操作与对应的处理程序

一些 Channel 类型
> 1. EmbeddedChannel
> 2. LocalServerChannel
> 3. NioSctpChannel
> 4. NioSocketChannel

#### EventLoop
定义了 Netty 的核心抽象，用于处理连接的生命周期中所发生的的事情，控制流、多线程处理、并发
> 1. 一个或者多个 EventLoop 组成一个 EventLoopGroup 
> 2. 一个 EventLoop 在它的生命周期内只和一个 Thread 绑定，所有的 I/O 事件都将在这个专有的 Thread 上处理
> 3. 一个 Channel 在它的生命周期内只注册于一个 EventLoop, 不过一个 EventLoop 可能会被分配给多个 Channel

#### Future/ChannelFuture
在Netty中所有的IO操作都是异步的，不能立刻得知消息是否被正确处理，但是可以过一会等它执行完成或者直接注册一个监听，具体的实现就是通 过Future 和 ChannelFutures，他们可以注册一个监听，当操作执行成功或失败时监听会自动触发注册的监听事件



## 模块组件
### Bootstrap、ServerBootstrap
一个Netty应用通常由一个Bootstrap开始，主要作用是配置整个Netty程序，串联各个组件，Netty中Bootstrap类是客户端程序的启动引导类，ServerBootstrap是服务端启动引导类。

### Future、ChannelFuture
参考 [Future/ChannelFuture](#Future/ChannelFuture)

### Channel 组件
参考 [Channel 接口](#Channel)

### Selector
Netty基于Selector对象实现I/O多路复用，通过 Selector, 一个线程可以监听多个连接的Channel事件, 当向一个Selector中注册Channel 后，Selector 内部的机制就可以自动不断地查询(select) 这些注册的Channel是否有已就绪的I/O事件(例如可读, 可写, 网络连接完成等)，这样程序就可以很简单地使用一个线程高效地管理多个 Channel 

### EventLoop 和 EventLoopGroup
参考 [EventLoop 接口](#EventLoop)

### ChannelHandler/ChannelPipeline
> 管理数据流以及执行应用程序处理逻辑的组件

#### ChannelOption 类
一些常见参数:
- `SO_RCVBUF` 和 `SO_SNDBUF`: 这两个为TCP传输选项，每个TCP socket（套接字）在内核中都有一个发送缓冲区和一个接收缓冲区，这两个选项就是用来设置TCP连接的两个缓冲区大小的
- `TCP_NODELAY`: TCP传输选项，如果设置为true就表示立即发送数据。TCP_NODELAY用于开启或关闭 Nagle算法。如果要求高实时性，有数据发送时就马上发送，就将该选项设置为true（关闭Nagle算法）；如果要减少发送次数、减少网络交互，就设置为false（开启Nagle算法），等累积一定大小的数据后再发送          
> Nagle算法将小的碎片数据连接成更大的报文（或数据包）来最小化所发送报文的数量，如果需要发送一些较小的报文，则需要禁用该算法。Netty默认禁用Nagle算法，报文会立即发送出去，从而最小化报文传输的延时

- `SO_KEEPALIVE`: 表示是否开启TCP的心跳机制。true为连接保持心跳，默认值为false。启用该功能时，TCP会主动探测空闲连接的有效性。需要注意的是：默认的心跳间隔是7200秒，即2小时。Netty默认关闭该功能
- `SO_REUSEADDR`: 表示允许重复使用本地地址和端口
    >有四种情况需要用到这个参数设置： 
    > 1. 当有一个地址和端口相同的连接socket1处于TIME_WAIT状态时，而又希望启动一个新的连接socket2要占用该地址和端口。
    > 2. 有多块网卡或用IP Alias技术的机器在同一端口启动多个进程，但每个进程绑定的本地IP地址不能相同。
    > 3. 同一进程绑定相同的端口到多个socket（套接字）上，但每个socket绑定的IP地址不同。
    > 4. 完全相同的地址和端口的重复绑定，但这只用于UDP的多播，不用于TCP

- `SO_LINGER`: 用来控制socket.close()方法被调用后的行为，包括延迟关闭时间, 默认值为-1，表示禁用该功能
    > 1. 此选项设置为-1，就表示socket.close()方法在调用后立即返回，但操作系统底层会将发送缓冲区的数据全部发送到对端
    > 2. 设置为0，就表示socket.close()方法在调用后会立即返回，但是操作系统会放弃发送缓冲区数据，直接向对端发送RST包，对端将收到复位错误
    > 3. 此选项设置为非 0 整数值，就表示调用socket.close()方法的线程被阻塞，直到延迟时间到来，发送缓冲区中的数据发送完毕，若超时，则对端会收到复位错误

- `SO_BACKLOG`: 表示服务端接收连接的队列长度，如果队列已满，客户端连接将被拒绝, 对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列
- `SO_BROADCAST`: TCP传输选项，表示设置为广播模式

#### ChannelHandler 接口
充当了所有处理入站和出站数据的逻辑容器。ChannelHandler 主要用来处理各种事件，这里的事件很广泛，比如可以是连接、数据接收、异常、数据转换等


#### ChannelHandlerContext
保存Channel相关的所有上下文信息，同时关联一个ChannelHandler对象

#### ChannelPipeline 接口
提供了 ChannelHandler 链路的容器，并定义了用于在该链上传播入站和出站事件的流的 API, 当 channel 创建时，就会被自动分配到它专属的 ChannelPipeline.
当 ChannelHandler 被添加到 ChannelPipeline 时，它将会被分配一个 ChannelHandlerContext, 代表两者间的绑定关系，主要被用于写出站数据

#### 编码器和解码器
使用 Netty 发送或接收消息的时候，都会发生数据交换，其中就涉及到数据格式的编解码过程，Netty 为编码器和解码器提供了不同类型的抽象类