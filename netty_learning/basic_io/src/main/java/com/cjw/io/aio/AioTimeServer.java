package com.cjw.io.aio;

/**
 * @author chenjw
 * @version 1.0
 * @date 2022/4/12 17:07
 */
public class AioTimeServer {

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

        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler").start();
    }
}
