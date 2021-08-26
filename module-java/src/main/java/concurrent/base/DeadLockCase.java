package concurrent.base;

/**
 * 多线程死锁
 *
 * @author: ZOUFANQI
 * @create: 2021-08-12 18:36
 **/
public class DeadLockCase {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        new UserThread1().start();
        new UserThread2().start();
    }

    private static class UserThread1 extends Thread {
        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("已获得锁：lock1，正在等待锁：lock2...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("-------------- 获取到所有锁，处理userThread111业务");
                }
            }
        }
    }

    private static class UserThread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("已获得锁：lock2，正在等待锁：lock1...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("-------------- 获取到所有锁，处理userThread222业务");
                }
            }
        }
    }
}
