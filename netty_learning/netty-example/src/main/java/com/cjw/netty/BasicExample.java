package com.cjw.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author chenjw
 * @version 1.0
 * @date 2022/6/30 15:32
 */
public class BasicExample {
    public static void main(String[] args) {
        // 创建 mainReactor
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 创造工作线程组
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                // 组装 NioEventGroup
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                // 设置连接配置参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast();
                        ch.pipeline().addLast();
                    }
                });
        int port = 8080;
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口：【" + port + "】绑定成功");
            } else {
                System.out.println("端口：【" + port + "】绑定失败");
            }
        });
    }
}
