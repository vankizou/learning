package com.zoufanqi.algorithm.leetcode;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2022/12/29 15:05
 **/
public class T {
    public static void main(String[] args) {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 1, TimeUnit.DAYS, new ArrayBlockingQueue<>(100));
        tpe.submit(new A());
        tpe.shutdown();
    }
}

class A implements Runnable {

    @Override
    public void run() {
        int a = 1 / 0;
    }
}
