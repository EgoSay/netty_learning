package com.ego.netty.BIO;

import java.net.ServerSocket;

/**
 * @author Ego
 * @version 1.0
 * @date 2019-06-28 10:13
 * @Decription 同步阻塞式I/O创建的TimeServer
 */
public class TimeServer {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        ServerSocket server = null;
    }
}
