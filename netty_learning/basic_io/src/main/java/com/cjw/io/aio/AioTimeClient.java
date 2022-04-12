package com.cjw.io.aio;

/**
 * @author chenjw
 * @version 1.0
 * @date 2022/4/12 17:57
 */
public class AioTimeClient {

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
            new Thread(new AsyncTimeClientHandler("127.0.0.1", port, "QUERY TIME ORDER " + i)).start();
        }
    }
}
