## netty 核心组件

### Channel、EventLoop 和 ChannelFuture
- Channel 接口，大大降低了 Java 的网络编程中直接使用 Socket 开发的复杂性
    > 1. EmbeddedChannel
    > 2. LocalServerChannel
    > 3. NioSctpChannel
    > 4. NioSocketChannel

- EventLoop, 定义了 Netty 的核心抽象，用于处理连接的生命周期中所发生的的事情，控制流、多线程处理、并发
    > 1. 一个或者多个 EventLoop 组成一个 EventLoopGroup 
    > 2. 一个 EventLoop 在它的生命周期内只和一个 Thread 绑定，所有的 I/O 事件都将在这个专有的 Thread 上处理
    > 3. 一个 Channel 在它的生命周期内只注册于一个 EventLoop, 不过一个 EventLoop 可能会被分配给多个 Channel

- ChannelFuture, Netty 中所有的 I/O 操作都是异步的，所以需要一个回调科技

### ChannelHandler 和 ChannelPipeline
> 管理数据流以及执行应用程序处理逻辑的组件

- ChannelHandler 接口
充当了所有处理入站和出站数据的逻辑容器。ChannelHandler 主要用来处理各种事件，这里的事件很广泛，比如可以是连接、数据接收、异常、数据转换等

- ChannelPipeline 接口
提供了 ChannelHandler 链路的容器，并定义了用于在该链上传播入站和出站事件的流的 API, 当 channel 创建时，就会被自动分配到它专属的 ChannelPipeline.
当 ChannelHandler 被添加到 ChannelPipeline 时，它将会被分配一个 ChannelHandlerContext, 代表两者间的绑定关系，主要被用于写出站数据

#### 编码器和解码器
使用 Netty 发送或接收消息的时候，都会发生数据交换，其中就涉及到数据格式的编解码过程，Netty 为编码器和解码器提供了不同类型的抽象类