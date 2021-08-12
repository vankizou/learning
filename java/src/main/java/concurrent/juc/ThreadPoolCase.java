package concurrent.juc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-11 15:58
 **/
public class ThreadPoolCase {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                1,      // 核心线程数，提交的任务会优先创建，直到核心数满再将数据放到队列中
                2,  // 最大线程数，当队列数据满了后会开启新线程，数量最多到最大线程数
                30,   // 当队列空闲后，多久后会
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new MyThreadFactory(),
                new MyRejectedExecutionHandler()
        ) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(String.format("线程【%s】开始跑了，执行work: %s...", r, t));
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println(String.format("线程【%s】跑完了！共处理数据：%s", r, this.getCompletedTaskCount() + 1));
            }
        };

        // corePoolSize范围内
        threadPool.execute(new UserThread("1"));

        // 会开新work
        threadPool.execute(new UserThread("2"));

        // 超出work，入队列
        threadPool.execute(new UserThread("3"));

        // 超出队列，走拒绝策略
        threadPool.execute(new UserThread("4"));

        threadPool.shutdown();
    }

    private static class UserThread implements Runnable {
        final private String name;

        UserThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "业务：" + name;
        }
    }

    /**
     * 自定义拒绝策略
     */
    private static class MyRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(String.format("数据太多，队列满了！该数据扔掉了：【%s】", r));
        }
    }

    /**
     * 自定义工作线程工厂
     */
    private static class MyThreadFactory implements ThreadFactory {
        private final AtomicInteger count = new AtomicInteger(0);
        private final ThreadGroup group = new ThreadGroup("vanki");

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, "my-thread-pool-" + count.getAndIncrement(), 0);
            t.setDaemon(false);
            t.setPriority(Thread.NORM_PRIORITY);
            System.out.println("创建线程：" + t);
            return t;
        }
    }
}
