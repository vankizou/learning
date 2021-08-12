package concurrent.juc.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-11 14:37
 **/
public class SynchronousQueueCase {
    private static final SynchronousQueue queue = new SynchronousQueue();

    public static void main(String[] args) throws InterruptedException {
        new UserThread().start();
        new UserThread().start();

        Thread.sleep(100);
        System.out.println(queue.offer("1"));   // true
        Thread.sleep(100);
        System.out.println(queue.offer("2"));   // true
        Thread.sleep(100);
        System.out.println(queue.offer("3"));   // false
    }

    private static class UserThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread() + " - " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
