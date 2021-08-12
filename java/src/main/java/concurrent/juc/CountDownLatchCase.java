package concurrent.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CDL分流聚合
 * <p>
 * thread1: ---------结束     |    ↓↓
 * thread2: --------------结束| -> 分业务全部完成，开始后续业务
 * thread3: ----结束          |    ↑↑
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 10:22
 **/
public class CountDownLatchCase {
    // 10个要处理的业务
    private static final CountDownLatch CDL = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        // 线程数与 CDL count数无必然关系
        new UserThread().start();
        new UserThread().start();
        new UserThread().start();
        new UserThread().start();
        new UserThread().start();

        CDL.await();
        System.out.println("所有事儿都处理完了，很棒！");
    }

    private static class UserThread extends Thread {
        @Override
        public void run() {
            try {
                // 业务1处理
                TimeUnit.SECONDS.sleep(1);
                System.out.println(String.format("处理完【%s】业务111~~ cdl count: %s", currentThread(), CDL.getCount()));
                CDL.countDown();    // 完成业务，计数 -1

                // 业务2处理
                TimeUnit.SECONDS.sleep(1);
                System.out.println(String.format("处理完【%s】业务222~~ cdl count: %s", currentThread(), CDL.getCount()));
                CDL.countDown();    // 完成业务，计数 -1
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
