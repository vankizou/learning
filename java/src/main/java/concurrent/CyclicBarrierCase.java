package concurrent;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 循环栅栏，一般用于分步任务场景
 * <p>
 * thread1: -------|    ↓↓                     ->  step2|---         |   ↓↓
 * thread2: --     | -> 分业务全部完成，开始后续业务 -> step2|-------     | -> 分业务全部完成，开始后续业务
 * thread3: ----   |    ↑↑                     -> step2|------------|    ↑↑
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 21:14
 **/
public class CyclicBarrierCase {
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
        @Override
        public void run() {
            System.out.println("所有分步都执行完了，我就出来冒个泡~");
        }
    });

    public static void main(String[] args) {
        // 线程数必须与CyclicBarrier的parties保持一致
        new UserThread(cyclicBarrier).start();
        new UserThread(cyclicBarrier).start();
        new UserThread(cyclicBarrier).start();
    }

    static class UserThread extends Thread {
        private CyclicBarrier cyclicBarrier;

        public UserThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(String.format("【%s】开始干活啦！stepA...", currentThread()));
                TimeUnit.SECONDS.sleep(1);
                this.cyclicBarrier.await();
                TimeUnit.SECONDS.sleep(1);
                System.out.println(String.format("【%s】开始干活啦！stepB...", currentThread()));
                this.cyclicBarrier.await();
                System.out.println(String.format("【%s】所有人都干完活了！", currentThread()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

}
