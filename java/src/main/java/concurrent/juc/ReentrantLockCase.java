package concurrent.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可重入锁的使用
 *
 * @author: ZOUFANQI
 * @create: 2021-08-09 13:53
 **/
public class ReentrantLockCase {
    private static final Lock lock = new ReentrantLock();
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        new UserThread().start();
        new UserThread().start();
        new UserThread().start();
        new UserThread().start();
        Thread.sleep(1000);
        System.out.println(count);
    }

    private static class UserThread extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (i++ < 100000) {
                try {
                    lock.lock();
                    count++;
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
