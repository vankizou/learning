package com.zoufanqi.concurrent.juc;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * 信号量，一般用于控制资源访问、限流等，比如：数据库连接
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 21:45
 **/
public class SemaphoreCase {
    private static final Semaphore SEMAPHORE = new Semaphore(3);
    private static final Queue<Conn> CONN_POOL = new LinkedBlockingQueue<Conn>() {{
        try {
            /**
             * 初始化连接池，与信号量保持一致
             */
            this.add(new Conn());
            this.offer(new Conn());
            this.put(new Conn());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }};

    private static final CountDownLatch CDL = new CountDownLatch(30);

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            new UserThread(SEMAPHORE, CONN_POOL).start();
        }
    }

    private static class UserThread extends Thread {
        private static final Random random = new Random();
        private Semaphore semaphore;
        private Queue<Conn> connPool;

        public UserThread(Semaphore semaphore, Queue<Conn> connPool) {
            this.semaphore = semaphore;
            this.connPool = connPool;
        }

        @Override
        public void run() {

            try {
                if (semaphore.availablePermits() == 0) {
                    System.out.println("连接池暂无连接，请耐心等待！");
                }
                semaphore.acquire();
                Conn conn = this.connPool.poll();
                System.out.println(String.format("获取到连接：%s，连接池剩余连接：%s", conn, connPool.size()));
                sleep(random.nextInt(1000));
                connPool.add(conn);
                System.out.println(String.format("归还连接：%s，连接池剩余连接：%s", conn, connPool.size()));
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    private static class Conn {
    }
}
