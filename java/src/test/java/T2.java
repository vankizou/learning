import java.util.concurrent.Semaphore;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-09 11:16
 **/
public class T2 {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(2);
        System.out.println(semaphore.availablePermits());
        semaphore.release();
        semaphore.release();
        semaphore.release();
        semaphore.release();
        System.out.println(semaphore.availablePermits());
        semaphore.acquire();
        semaphore.acquire();
        semaphore.acquire();
        semaphore.acquire();
        semaphore.acquire();
        System.out.println(semaphore.availablePermits());
        semaphore.acquire();
        System.out.println(semaphore.availablePermits());
        semaphore.acquire();
        System.out.println(semaphore.availablePermits());
        semaphore.acquire();
        System.out.println(semaphore.availablePermits());
    }
}
