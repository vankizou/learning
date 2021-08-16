package concurrent.base;

/**
 * volatile可见性、有序性
 *
 * 实现原理：
 *  有volatile变量修饰的共享变量进行写操作的时候会使用CPU提供的Lock前缀指令
 *  - 将当前处理器缓存行的数据写回到系统内存
 *  - 这个写回内存的操作会使在其他CPU里缓存了该内存地址的数据无效
 *
 * @author: ZOUFANQI
 * @create: 2021-08-09 14:55
 **/
public class VolatileCase {
    /**
     * 线程中断标志，带volatile声明
     */
    private static volatile boolean flagVolatile = false;
    /**
     * 线程中断标志，不带volatile声明
     */
    private static boolean flagNonVolatile = false;

    public static void main(String[] args) throws InterruptedException {
        UserThreadVolatile userThreadVolatile = new UserThreadVolatile();
        UserThreadNonVolatile userThreadNonVolatile = new UserThreadNonVolatile();

        userThreadVolatile.start();
        userThreadNonVolatile.start();

        Thread.sleep(100);
        flagVolatile = true;
        flagNonVolatile = true;
    }

    private static final class UserThreadVolatile extends Thread {
        @Override
        public void run() {
            while (!flagVolatile) ;
            System.out.println("flagVolatile已设置为true, 死循环退出！");
        }
    }

    private static final class UserThreadNonVolatile extends Thread {
        @Override
        public void run() {
            while (!flagNonVolatile) ;
            System.out.println("flagNonVolatile虽然设置为true，但看不到，所以一直在死循环...");

        }
    }
}
