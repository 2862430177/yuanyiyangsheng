package com.ruoyi.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;

/**
 * @Description 线程工厂
 * @Author 41976
 * @Date 2024/7/10 11:37
 */
@Slf4j
public class MyThreadFactory implements ThreadFactory {

    private ThreadFactory original;

    public MyThreadFactory(ThreadPoolTaskExecutor executor) {
        this.original = executor;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = original.newThread(r);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());//异常捕获
        return thread;
    }
}

@Slf4j
class MyUncaughtExceptionHandler  implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread {} ", t.getName(), e);
    }

}

