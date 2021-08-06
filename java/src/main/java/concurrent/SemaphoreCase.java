package concurrent;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

/**
 * 信号量，一般用于控制资源访问、限流等，比如：数据库连接
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 21:45
 **/
public class SemaphoreCase {
    private static final Semaphore semaphore = new Semaphore(10);
    private static final Queue connPool = new LinkedBlockingQueue();

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Integer> q = new SynchronousQueue<>();
        q.put(1);
        q.put(1);
    }

    private static class Conn extends Thread {
        private Semaphore semaphore;

        public Conn(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {



        }
    }

}
