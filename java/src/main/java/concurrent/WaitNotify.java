package concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Wait、Notify 实现生产者消费者
 *
 * @author: ZOUFANQI
 * @create: 2021-08-04 20:10
 **/
public class WaitNotify {
    private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        new Consumer().start();
        new Producer().start();
        new Producer().start();
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            try {
                synchronized (queue) {
                    while (true) {
                        if (queue.size() <= 0) {
                            queue.wait();
                        }
                        System.out.println("消费数据：" + queue.poll());
                        TimeUnit.SECONDS.sleep(1);
                        queue.notifyAll();
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
                synchronized (queue) {
                    int count = 0;
                    while (true) {
                        if (queue.size() > 1) {
                            queue.wait();
                        }
                        String data = Thread.currentThread().toString() + ": " + count++;
                        System.out.println("生产数据：" + data);
                        queue.put(data);
                        TimeUnit.SECONDS.sleep(1);
                        queue.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
