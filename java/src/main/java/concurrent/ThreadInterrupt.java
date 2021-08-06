package concurrent;

/**
 * 线程中断
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 21:56
 **/
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        UserThread thread = new UserThread();
        thread.start();
        Thread.sleep(1000);
        // 这里只是对线程作一个中断标记
        thread.interrupt();
    }

    private static class UserThread extends Thread {
        @Override
        public void run() {
            // 如果在循环内部有对当前线程的操作（如：sleep），则可能会被循环内部业务捕获到中断标记
            while (!isInterrupted()) {
                System.out.println("你不中断我就一直干~~ " + this.isInterrupted());
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("====== sleep捕获到中断，并且当前Thread的中断状态变成了false：" + isInterrupted());
                    // 因为sleep捕获到了外部的中断标记，并将中断标记重新重置为false，所以要再调用中断，以确保业务是一定要中断
                    // 这么做是为了让线程变成协作式，而不是生硬的一刀切，比如，正在读写文件，这时直接中断则可能会搞成文件的缺失
                    interrupt();
                }
            }
        }
    }
}
