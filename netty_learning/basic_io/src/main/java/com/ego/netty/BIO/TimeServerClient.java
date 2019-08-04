package com.ego.netty.BIO;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * @author Ego
 * @version 1.0
 * @date 2019-07-02 20:12
 * @Description
 */
public class TimeServerClient {

    public  void RunTimeServerClient() {
        int port = 8080;

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            out.println("QUERY TIME ORDER");
            System.out.println("Send Order to Server Success, Now Times is " + new Date());
            String respon = in.readLine();
            System.out.println("Now is:" + respon);
            System.out.println("Now Times is: " + new Date());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                in = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
