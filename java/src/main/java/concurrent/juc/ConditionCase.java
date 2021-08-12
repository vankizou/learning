package concurrent.juc;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 显示锁实现生产者、消费者
 *
 * wait、notify 换成 condition
 *
 * @author: ZOUFANQI
 * @create: 2021-08-09 14:29
 **/
public class ConditionCase {
    private static final LinkedBlockingQueue<String> QUEUE = new LinkedBlockingQueue<>();
    private static final Lock LOCK = new ReentrantLock(true);

    // 队列为空条件
    private static final Condition EMPTY_COND = LOCK.newCondition();

    public static void main(String[] args) {
        new ConditionCase.Producer().start();
        new ConditionCase.Producer().start();
        new ConditionCase.Consumer().start();
        new ConditionCase.Consumer().start();
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        LOCK.lock();
                        while (QUEUE.size() <= 0) {
                            EMPTY_COND.await(); // 队列为空等待
                        }
                        System.out.println("消费数据【" + currentThread() + "】：" + QUEUE.poll());
                        TimeUnit.SECONDS.sleep(1);
                    } finally {
                        LOCK.unlock();
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
                int count = 0;
                while (true) {
                    try {
                        LOCK.lock();
                        String data = Thread.currentThread().toString() + ": " + count++;
                        System.out.println("生产数据【" + currentThread() + "】：" + data);
                        QUEUE.put(data);
                        EMPTY_COND.signal();
                        TimeUnit.SECONDS.sleep(1);
                    } finally {
                        LOCK.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
