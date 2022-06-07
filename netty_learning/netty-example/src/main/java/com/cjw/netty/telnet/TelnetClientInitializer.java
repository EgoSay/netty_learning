package com.cjw.netty.telnet;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslContext;

/**
 * @author chenjw
 * @version 1.0
 * @date 2022/4/27 17:27
 */
public class TelnetClientInitializer implements ChannelHandler {
    public TelnetClientInitializer(SslContext sslContext) {
    }

    /**
     * Gets called after the {@link ChannelHandler} was added to the actual context and it's ready to handle events.
     *
     * @param ctx
     */
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * Gets called after the {@link ChannelHandler} was removed from the actual context and it doesn't handle events
     * anymore.
     *
     * @param ctx
     */
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * Gets called if a {@link Throwable} was thrown.
     *
     * @param ctx
     * @param cause
     * @deprecated if you want to handle this event you should implement {@link ChannelInboundHandler} and
     * implement the method there.
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
