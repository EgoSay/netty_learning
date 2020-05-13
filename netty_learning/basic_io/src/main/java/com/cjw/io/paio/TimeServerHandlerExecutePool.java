package com.cjw.io.paio;

import java.util.concurrent.*;

/**
 * @author Ego
 * @version 1.0
 * @date 2019-07-11 20:52
 * @Description
 */
public class TimeServerHandlerExecutePool {
    private ThreadPoolExecutor executor;

    public TimeServerHandlerExecutePool(int coreSize, int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(coreSize, maxPoolSize,
                120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
    }

    public void execute(java.lang.Runnable task) {
        executor.execute(task);
    }
}
