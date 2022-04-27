## 基础概念
网络IO的本质是 `socket` 的读取, `socket`在`linux`系统被抽象为流，故对网络IO的操作可以理解为对流的操作。

对于一次IO访问，比如以`read`操作为例，数据会先被拷贝到操作系统内核的缓冲区，然后才会从内核缓冲区拷贝到进程的用户层，即应用程序的地址空间。
故当一个read操作发生时，其实是经历了两个阶段：
1. **内核缓冲区的数据就位**
2. **数据从内核缓冲区拷贝到用户程序地址空间**

那么具体到`socket io`的一次`read`操来说，这两步分别是:
1. **等待网络上的数据分组到达，然后复制到内核缓冲区中**
2. **数据从内核缓冲区拷贝到用户程序的地址空间(缓冲区)**

## IO模型
- 同步模型（synchronous IO）
    1. 阻塞IO（bloking IO）
    2. 非阻塞IO（non-blocking IO）
    3. 多路复用IO（multiplexing IO）
    4. 信号驱动式IO（signal-driven IO）

- 异步IO（asynchronous IO）

## 参考链接
> 1. [聊聊Linux 五种IO模型](https://www.jianshu.com/p/486b0965c296)
> 2. [聊聊同步、异步、阻塞与非阻塞](https://my.oschina.net/xianggao/blog/661085)





