package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 线程顺序执行
 *
 * @author: ZOUFANQI
 * @create: 2021-08-03 10:20
 **/
public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        UserThread t1 = new UserThread("thread1");
        UserThread t2 = new UserThread("thread2");
        UserThread t3 = new UserThread("thread3");
        UserThread t4 = new UserThread("thread4");
        UserThread t5 = new UserThread("thread5");

        // 指定顺序 5 -> 4 -> 无序

        // 等t5跑完
        t5.start();
        t5.join();

        // 等t4跑完
        t4.start();
        t4.join();

        // 无序
        t3.start();
        t2.start();
        t1.start();
    }

    private static class UserThread extends Thread {
        public UserThread(String threadName) {
            this.setName(threadName);
        }

        @Override
        public void run() {
            System.out.println(String.format("%s: 我开始跑任务了...", this.getName()));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
