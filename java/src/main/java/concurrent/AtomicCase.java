package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomic CAS自旋乐观锁
 *
 * CAS问题：
 * 1. 开销问题
 * 2. 只能保证一个共享变量的操作
 * 3. ABA问题
 *
 * @author: ZOUFANQI
 * @create: 2021-08-09 10:43
 **/
public class AtomicCase {
    private static AtomicInteger AI = new AtomicInteger();
    private static CountDownLatch CDL = new CountDownLatch(5);

    public static void main(String[] args) throws InterruptedException {
        /**
         * 多线程操作共享数据累加计数
         */
        new UserThread().start();
        new UserThread().start();
        new UserThread().start();
        new UserThread().start();
        new UserThread().start();

        CDL.await();
        System.out.println(AI);

        /**
         * 将旧值100修改成200
         */
        AtomicInteger ai2 = new AtomicInteger(100);
        System.out.println(ai2);
        System.out.println(ai2.compareAndSet(100, 200));
        System.out.println(ai2);
    }

    private static class UserThread extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (i++ < 1000000) {
                AI.incrementAndGet();
            }
            CDL.countDown();
        }
    }
}
