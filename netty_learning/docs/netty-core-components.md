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

#### ChannelHandler 接口
充当了所有处理入站和出站数据的逻辑容器。ChannelHandler 主要用来处理各种事件，这里的事件很广泛，比如可以是连接、数据接收、异常、数据转换等


#### ChannelHandlerContext
保存Channel相关的所有上下文信息，同时关联一个ChannelHandler对象

#### ChannelPipeline 接口
提供了 ChannelHandler 链路的容器，并定义了用于在该链上传播入站和出站事件的流的 API, 当 channel 创建时，就会被自动分配到它专属的 ChannelPipeline.
当 ChannelHandler 被添加到 ChannelPipeline 时，它将会被分配一个 ChannelHandlerContext, 代表两者间的绑定关系，主要被用于写出站数据

#### 编码器和解码器
使用 Netty 发送或接收消息的时候，都会发生数据交换，其中就涉及到数据格式的编解码过程，Netty 为编码器和解码器提供了不同类型的抽象类