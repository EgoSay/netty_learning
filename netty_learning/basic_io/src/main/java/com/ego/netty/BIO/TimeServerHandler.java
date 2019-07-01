package com.ego.netty.BIO;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Ego
 * @version 1.0
 * @date 2019-06-30 09:55
 * @Description
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {

        } catch (Exception e) {

        }
    }
}
