package com.ego.netty.PAIO;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ego
 * @version 1.0
 * @date 2019-07-11 20:52
 * @Description
 */
public class TimeServerHandlerExecutePool {
    private Executor executor;

    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize,
                120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    public void execute(java.lang.Runnable task) {
        executor.execute(task);
    }
}
