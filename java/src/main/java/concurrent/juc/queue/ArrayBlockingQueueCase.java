package concurrent.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-11 14:04
 **/
public class ArrayBlockingQueueCase {
    private static final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

    public static void main(String[] args) throws InterruptedException {
        queue.put(1);
        queue.put(2);
        queue.put(3);
        System.out.println(queue.take());

        // 放置在idx=0上
        queue.put(4);
        System.out.println(queue.peek());
        System.out.println(queue.peek());
        System.out.println(queue.size());
    }
}
