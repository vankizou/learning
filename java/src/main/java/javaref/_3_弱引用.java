package javaref;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-04 13:55
 **/
public class _3_弱引用 {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 1. 执行代码前先设置 vm options：
         * -Xmx10M (应用程序内存在8M左右)
         */

        // 2. 三个弱引用，1M、2M、3M
        WeakReference<byte[]> wr1 = new WeakReference<>(new byte[1024 * 1024 * 1]);

        byte[] b2 = new byte[1024 * 1024 * 2];
        WeakReference<byte[]> wr2 = new WeakReference<>(b2);
        WeakReference<byte[]> wr3 = new WeakReference<>(new byte[1024 * 1024 * 3]);

        /**
         * 3. 每秒一次循环，添加512K数据
         *
         * 在第3次的时候触发gc，将软引用变量【a】中的值直接回收了
         * wr2有个强引用指向它，所以不会被回收
         *
         */
//         执行结果：
//        1: wr1地址：[B@5e2de80c, wr2地址：[B@1d44bcfa, wr3地址：[B@266474c2, b地址：[B@6f94fa3e
//        2: wr1地址：[B@5e2de80c, wr2地址：[B@1d44bcfa, wr3地址：[B@266474c2, b地址：[B@5e481248
//        3: wr1地址：null, wr2地址：[B@1d44bcfa, wr3地址：null, b地址：[B@66d3c617
//        4: wr1地址：null, wr2地址：[B@1d44bcfa, wr3地址：null, b地址：[B@63947c6b
//        5: wr1地址：null, wr2地址：[B@1d44bcfa, wr3地址：null, b地址：[B@2b193f2d
//        6: wr1地址：null, wr2地址：[B@1d44bcfa, wr3地址：null, b地址：[B@355da254
//        7: wr1地址：null, wr2地址：[B@1d44bcfa, wr3地址：null, b地址：[B@4dc63996
//        8: wr1地址：null, wr2地址：[B@1d44bcfa, wr3地址：null, b地址：[B@d716361
//        9: wr1地址：null, wr2地址：[B@1d44bcfa, wr3地址：null, b地址：[B@6ff3c5b5
//        10: wr1地址：null, wr2地址：[B@1d44bcfa, wr3地址：null, b地址：[B@3764951d
//        11: wr1地址：null, wr2地址：[B@1d44bcfa, wr3地址：null, b地址：[B@4b1210ee
//        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//        at javaref._3_弱引用.main(_3_弱引用.java:55)

        List<byte[]> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            byte[] b = new byte[1024 * 512];
            System.out.println(String.format("%s: wr1地址：%s, wr2地址：%s, wr3地址：%s, b地址：%s", i, wr1.get(), wr2.get(), wr3.get(), b));
            list.add(b);
            TimeUnit.SECONDS.sleep(1);
        }

    }

}
