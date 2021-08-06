package concurrent;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 通过fork/join 统计
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 14:19
 **/
public class ForkJoinCase {
    private static final Random random = new Random();

    public static void main(String[] args) {
        long[] arr = new long[1000000];
        long expectSum = 0;
        for (int i = 0, len = arr.length; i < len; i++) {
            arr[i] = random.nextInt(100000);
            expectSum += arr[i];
        }
        System.out.println("期望值：" + expectSum);

        /**
         * fork/join
         */
        ForkJoinTask<Long> task = new SumTask(arr, 0, arr.length);
        long s = System.currentTimeMillis();
        long result = ForkJoinPool.commonPool().invoke(task);
        long e = System.currentTimeMillis();
        System.out.println(String.format("fork/join计算值：%s, time: %sms", result, (e - s)));
    }
}

class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 500;   // 任务细化阈值，超过该值任务做拆分
    private final long[] arr;
    private final int start;
    private final int end;

    public SumTask(long[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (this.end - this.start <= THRESHOLD) {
            /**
             * 在阈值内，做计算
             */
            long sum = 0;
            for (int i = this.start; i < this.end; i++) {
                sum += this.arr[i];
            }
            return sum;
        }

        /**
         * 超出阈值，做任务拆分
         */
        int mid = (this.end + this.start) / 2;
        SumTask task1 = new SumTask(this.arr, this.start, mid);
        SumTask task2 = new SumTask(this.arr, mid, this.end);
        // 任务拆分
        invokeAll(task1, task2);

        // 等待任务向上抛结果
        Long result1 = task1.join();
        Long result2 = task2.join();

        return result1 + result2;
    }
}
