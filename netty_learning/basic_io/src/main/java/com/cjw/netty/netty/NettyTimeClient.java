package com.cjw.netty.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author chenjw
 * @version 1.0
 * @date 2022/7/13 17:46
 */
public class NettyTimeClient {

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new NettyTimeClientHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect(host, port).sync();
        future.channel().closeFuture().sync();


        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        new NettyTimeClient().connect(8081, "127.0.0.1");
    }


}
