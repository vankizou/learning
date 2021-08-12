package javaref;

/**
 * ThreadLocal内存泄漏
 *
 * 数据结构：数组
 *      例：table[16]{Entry<ThreadLocal>(value)}
 *
 * set步骤：
 *  1. idx = hash(key) % 15 找出table对应的下标
 *  2. 通过table[idx]取一次值，
 *      如果不为空，循环找（idx+1，超出数组索引最大时idx=0）为空的地方存（中间会判断key引用是否被回收，回收作清理）；
 *      如果为空，添加新Entry，并作数据阈值判断，超出阈值做resize
 * get步骤：
 *  1. idx = hash(key) % 15 找出table对应的下标
 *  2. 取table[idx]值，
 *      如果不为空，返回取值
 *      如果为空，1: map或entry为空，作map初始化，返回null；2: map和entry不为空找 table[idx+1]的Entry，且key相等的值，取到返回值，取不到返回null
 *
 * @author: ZOUFANQI
 * @create: 2021-08-04 13:55
 **/
public class ThreadLocalMemoryLeak {

    public static void main(String[] args) {
        /**
         * 执行代码前先设置 vm options：
         * -Xmx10M (应用程序内存在8M左右)
         */

        // step1
        ThreadLocal<byte[]> tl = new ThreadLocal<>();
        // 第一次set一个3M的数据
        tl.set(new byte[1024 * 1024 * 2]);

        System.out.println("step1: " + tl.get());

        /**
         * step2
         *
         * 第二次改变ThreadLocal指针
         *
         * 第二次设置的值因为是垃圾，gc可以回收(回收场景：1. get第一次hash取值为null时，2. set添加数据超过阈值做resize，3. remove())
         * 所以ThreadLocal在每个Entry<ThreadLocal, Object>上都设置为软引用，这样就能让没有强引用的垃圾被回收，
         * 而Value因为会有强引用而一直不会回收，从而造成内存泄漏
         */
//        tl.remove();  解决办法：在不用的时候remove一下
        tl = new ThreadLocal<>();
        tl.set(new byte[1024 * 1024 * 2]);
        System.out.println("step2: " + tl.get());

        // step3
        // 其它业务追加的业务使用内存
        byte[] b = new byte[1024 * 1024 * 2];
        System.out.println("step3: " + tl.get());

        // step4
        // 在这儿做了一次minor gc，第一次生成 ThreadLocal 引用被回收掉了，此时产生了内存泄漏
        b = new byte[1024 * 1024 * 1];
        System.out.println("step4: " + tl.get());

        // step5
        b = new byte[1024 * 1024 * 2];
        System.out.println("step6: " + tl.get());

        // 上面的内存使用流程：
        // step1: +2M（第一次ThreadLocal的内存泄漏不会被回收）
        // step2: +2M（第二次TL，强引用，正常）
        // step3: +2M（创建的字节数组）
        // step4: +1M（再次追加1M字节数组数据到内存，但内存已不足，触发gc）
        // step4: -1M（此时触发minor gc，回收第一次创建的字节数组，注意：第一次的TL对象因为内存泄露没有被回收）
        // step5: +2M（最后一次加2M数据到堆，但此时已无内存，抛出OOM）

    }

}
