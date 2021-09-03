package com.zoufanqi.concurrent.juc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可重入锁
 *
 * @author: ZOUFANQI
 * @create: 2021-08-09 13:53
 **/
public class ReentrantLockCase2 {
    private static final ReentrantLock lock = new ReentrantLock();
    private static int count = 0;

    public static void main(String[] args) {
        opt(0);
        System.out.println(count);
    }

    /**
     * 同一个线程可以重复获取当前锁，无需竞争
     *
     * @param deep
     */
    private static void opt(int deep) {
        if (deep > 10) {
            return;
        }
        try {
            lock.lock();
            count++;
            // 递归，线程是一样的，可重入
            opt(++deep);
        } finally {
            lock.unlock();
        }
    }
}
