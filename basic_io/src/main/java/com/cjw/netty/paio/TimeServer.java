package com.cjw.netty.paio;

import com.cjw.netty.bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author Ego
 * @version 1.0
 * @date 2019-07-11 20:33
 * @Description: 利用线程池实现的伪异步IO
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        // 设置默认监听端口
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port:" + port);

            Socket socket = null;
            TimeServerHandlerExecutePool timeServerHandlerExecutePool = new TimeServerHandlerExecutePool(2,2, 2);
            while (true) {
                socket = server.accept();
                timeServerHandlerExecutePool.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                System.out.println("Now Times is: " + new Date());
                server.close();
                server = null;
            }
        }
    }
}
