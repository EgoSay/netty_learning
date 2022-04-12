package com.cjw.io.nio;

import com.cjw.io.paio.TimeServerHandlerExecutePool;

/**
 * @author chenjw
 * @version 1.0
 * @date 2022/4/11 19:47
 */
public class NioTimeClient {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        for (int i = 0; i < 10; i++) {
            new Thread(new NioTimeClientHandle("127.0.0.1", port, "QUERY TIME ORDER " + i)).start();
        }
    }
}
