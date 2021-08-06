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
        for (int i = 0; i < 10; i++) {
            UserThread thread = new UserThread("thread" + i);
            thread.start();
            thread.join();
        }
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
