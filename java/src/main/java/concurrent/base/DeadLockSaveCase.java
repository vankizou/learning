package concurrent.base;

import java.util.concurrent.locks.ReentrantLock;


/**
 * 规避多线程死锁例子：尝试拿锁机制
 *
 * @author: ZOUFANQI
 * @create: 2021-08-12 18:36
 **/
public class DeadLockSaveCase {
    private static final ReentrantLock lock1 = new ReentrantLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        new UserThread1().start();
        new UserThread2().start();
    }

    private static class UserThread1 extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    // 尝试获取锁
                    if (lock1.tryLock()) {
                        try {
                            System.out.println("已获得锁：lock1，正在等待锁：lock2...");

                            // 尝试获取锁
                            if (lock2.tryLock()) {
                                try {
                                    System.out.println("-------------- 获取到所有锁，处理userThread111业务");
                                    break;
                                } finally {
                                    lock2.unlock();
                                }
                            }
                        } finally {
                            lock1.unlock();
                        }
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class UserThread2 extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    // 尝试获取锁
                    if (lock2.tryLock()) {
                        try {
                            System.out.println("已获得锁：lock1，正在等待锁：lock2...");

                            // 尝试获取锁
                            if (lock1.tryLock()) {
                                try {
                                    System.out.println("-------------- 获取到所有锁，处理userThread222业务");
                                    break;
                                } finally {
                                    lock1.unlock();
                                }
                            }
                        } finally {
                            lock2.unlock();
                        }
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
