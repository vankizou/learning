package javaref;

/**
 * ThreadLocal内存泄漏
 *
 * @author: ZOUFANQI
 * @create: 2021-08-04 13:55
 **/
public class ThreadLocalMemoryLeak {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 执行代码前先设置 vm options：
         * -Xmx10M (应用程序内存在8M左右)
         */

        // step1
        ThreadLocal<byte[]> tl = new ThreadLocal<>();
        // 第一次set一个3M的数据
        tl.set(new byte[1024 * 1024 * 2]);

        /**
         * 第二次改变ThreadLocal指针
         *
         * 理论上第二次设置的值因为是垃圾，gc应该可以回收，但实际上却并没有
         * 所以ThreadLocal在每个Entry<ThreadLocal, Object>上都设置为软引用，这样就能让没有强引用的垃圾被回收，
         * 而Value因为会有强引用而一直不会回收，从而造成内存泄漏
         */
//        tl.remove();  解决办法：在不用的时候remove一下

        // step2
        tl = new ThreadLocal<>();
        tl.set(new byte[1024 * 1024 * 2]);

        // step3
        byte[] b = new byte[1024 * 1024 * 1];

        // step4
        b = new byte[1024 * 1024 * 1];
        System.out.println(tl.get());

        // step5
        // 在这儿做了一次minor gc，第一次生成 ThreadLocal 引用被回收掉了，此时产生了内存泄漏
        b = new byte[1024 * 1024 * 1];
        System.out.println(tl.get());

        // step6
        b = new byte[1024 * 1024 * 2];
        System.out.println(tl.get());

        // 上面的内存使用流程：
        // step1: +2M（第一次ThreadLocal的内存泄漏不会被回收）
        // step2: +2M（第二次TL，强引用，正常）
        // step3: +1M（创建的字节数组）
        // step4: +1M（字节数组第二次替换引用）
        // step5: +1M（再次追加1M字节数组数据到内存，但内存已不足，触发gc）
        // step5: -1M（此时触发minor gc，回收第一次创建的字节数组，注意：第一次的TL对象因为内存泄露没有被回收）
        // step6: +2M（最后一次加2M数据到堆，但此时已无内存，抛出OOM）

    }

}
