package com.cjw.netty.telnet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author chenjw
 * @version 1.0
 * @date 2022/4/15 15:43
 */
public class TelnetClient {

    private static final boolean SSL = System.getProperty("ssl") != null;
    private static final String HOST = System.getProperty("host", "127.0.0.1");
    private static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8992" : "8023"));

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            final SslContext sslContext = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new TelnetClientInitializer(sslContext));

            // Start the connection
            Channel ch = bootstrap.connect(HOST, PORT).sync().channel();

            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }

                lastWriteFuture = ch.writeAndFlush(line + "\r\n");
                if ("bye".equalsIgnoreCase(line)) {
                    ch.closeFuture().sync();
                    break;
                }
            }

            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
