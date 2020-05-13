package com.cjw.io.test;


import com.cjw.io.bio.TimeServerClient;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Ego
 * @version 1.0
 * @date 2019-08-04 18:49
 * @Description
 */
public class TestIO {
    private static AtomicInteger count = new AtomicInteger(0);
    @Test
    public void test() {
        while (true){

            try {
                long startTime = System.currentTimeMillis();
                TimeServerClient timeServerClient = new TimeServerClient();
                timeServerClient.RunTimeServerClient();
                count.incrementAndGet();
                long endTime = System.currentTimeMillis();
                System.out.println("client:" + count + " is Running" + ", it cost " + (endTime - startTime) + "ms");

            } catch (Exception e) {
                System.out.println("this client need wait.....");
            }finally {

            }

        }
    }
}
