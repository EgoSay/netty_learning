package com.cjw.netty.nio;

/**
 * @author chenjw
 * @version 1.0
 * @date 2022/4/11 15:50
 */
public class NioTimeServer {

    public static void main(String[] args) {
        // 设置默认监听端口
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer").start();
    }
}
