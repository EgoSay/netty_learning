package com.cjw.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author chenjw
 * @version 1.0
 * @date 2022/4/12 17:12
 */
public class AsyncTimeServerHandler implements Runnable {

    private int port;

    CountDownLatch latch;
    AsynchronousServerSocketChannel asyncServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            asyncServerSocketChannel= AsynchronousServerSocketChannel.open();
            asyncServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("【AIO】The time server is start in port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        latch = new CountDownLatch(1);
        asyncServerSocketChannel.accept(this, new AcceptCompletionHandler());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
