package com.zoufanqi.concurrent.juc;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池ThreadPoolExecutor
 *
 * 线程池状态：
 *     int RUNNING    = -1 << COUNT_BITS;  // 正常运行
 *     int SHUTDOWN   =  0 << COUNT_BITS;  // 关闭中（shutdown()进入该状态），队列入口关闭，在运行中和队列中的任务会调用线程interrupt()方法通知中断，若不理会线程任务会继续执行
 *     int STOP       =  1 << COUNT_BITS;  // 已关闭（shutdownNow()直接强制关闭任务进入该状态），队列与worker都空闲后为该状态
 *     int TIDYING    =  2 << COUNT_BITS;  // 清理线程相关的钩子
 *     int TERMINATED =  3 << COUNT_BITS;  // 彻底关闭
 *
 * @author: ZOUFANQI
 * @create: 2021-08-11 15:58
 **/
public class ThreadPoolCase {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                1,      // 核心线程数，提交的任务会优先创建，直到核心数满再将数据放到队列中
                2,  // 最大线程数，当队列数据满了后会开启新线程，数量最多到最大线程数
                30,   // 当队列线程空闲后，超时回收时间
                TimeUnit.SECONDS, // keepAliveTime成对
                new ArrayBlockingQueue<>(1),    // 存储任务的阻塞队列
                new MyThreadFactory(),  // 自定义线程工厂
                new MyRejectedExecutionHandler()    // 阻塞队列满了后的拒绝策略
        ) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(String.format("~~~ before【%s】线程【%s】开始跑了，执行work: %s...", new Date(), r, t));
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println(String.format("~~~ end【%s】线程【%s】跑完了！共处理数据：%s", new Date(), r, this.getCompletedTaskCount() + 1));
            }
        };

        // corePoolSize范围内
        Future<String> user1Future = threadPool.submit(new UserThreadCallable("1"));

        // 会开新work
        Future<String> user2Future = threadPool.submit(new UserThreadCallable("2"));

        // 超出work，入队列
        Future<String> user3Future = threadPool.submit(new UserThreadCallable("3"));

        // 超出队列，走拒绝策略
        Future<String> user4Future = threadPool.submit(new UserThreadCallable("4"));

        // 发出关闭请求后，任务3在队列中还未执行，但会等到任务3执行完成后再关闭
        threadPool.shutdown();

        System.out.println("阻塞队列中的任务数：" + threadPool.getQueue().size());

        // 线程池已关闭，走拒绝策略
        Future<String> user5Future = threadPool.submit(new UserThreadCallable("5"));

        System.out.println("=== return【user1】: " + user1Future.get());
        System.out.println("=== return【user2】: " + user2Future.get());
        System.out.println("=== return【user3】: " + user3Future.get());

        // 被拒绝的任务，这里监测不到，会一直阻塞，直到超时
        System.out.println("=== return【user4】: " + user4Future.get(5, TimeUnit.SECONDS));
        System.out.println("=== return【user5】: " + user5Future.get(5, TimeUnit.SECONDS));
    }

    private static class UserThreadCallable implements Callable<String> {
        final private String name;

        UserThreadCallable(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            System.out.println("-----start: " + this.name + ", 线程是否中断：" + Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-----end: " + this.name + ", 线程是否中断：" + Thread.currentThread().isInterrupted());
            return this.toString() + " -- 完成！";
        }

        @Override
        public String toString() {
            return "Runnable业务：" + name;
        }
    }

    /**
     * 自定义拒绝策略
     */
    private static class MyRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (executor.isTerminating()) {
                System.out.println(String.format("线程池已关闭！该数据扔掉了：【%s】", r));
            } else {
                System.out.println(String.format("数据太多，队列满了！该数据扔掉了：【%s】", r));
            }
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
