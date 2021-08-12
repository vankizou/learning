package concurrent.base;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Wait、Notify 实现生产者消费者
 *
 * @author: ZOUFANQI
 * @create: 2021-08-04 20:10
 **/
public class WaitNotify {
    private static final LinkedBlockingQueue<String> QUEUE = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        new Consumer().start();
        new Consumer().start();
        new Producer().start();
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            try {
                synchronized (QUEUE) {
                    while (true) {
                        while (QUEUE.size() <= 0) {
                            QUEUE.wait();
                        }
                        System.out.println("消费数据：" + QUEUE.poll());
                        TimeUnit.SECONDS.sleep(1);
                        QUEUE.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Producer extends Thread {
        @Override
        public void run() {
            try {
                synchronized (QUEUE) {
                    int count = 0;
                    while (true) {
                        if (QUEUE.size() > 1) {
                            QUEUE.wait();
                        }
                        String data = Thread.currentThread().toString() + ": " + count++;
                        System.out.println("生产数据：" + data);
                        QUEUE.put(data);
                        TimeUnit.SECONDS.sleep(1);
                        QUEUE.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
