package javaref;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-04 13:55
 **/
public class _2_软引用 {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 1. 执行代码前先设置 vm options：
         * -Xmx10M (应用程序内存在8M左右)
         */

        // 2. 三个软引用，1M、2M、3M
        SoftReference<byte[]> sr1 = new SoftReference<>(new byte[1024 * 1024 * 1]);
        SoftReference<byte[]> sr2 = new SoftReference<>(new byte[1024 * 1024 * 2]);
        SoftReference<byte[]> sr3 = new SoftReference<>(new byte[1024 * 1024 * 3]);

        /**
         * 3. 每秒一次循环，添加512K数据
         *
         * 在第5次的时候因为内存不足，将软引用变量【a】中的值直接回收了（一次性全回收了）
         *
         */
//         执行结果：
//        1: sr1地址：[B@5e2de80c, sr2地址：[B@1d44bcfa, sr3地址：[B@266474c2, b地址：[B@6f94fa3e
//        2: sr1地址：[B@5e2de80c, sr2地址：[B@1d44bcfa, sr3地址：[B@266474c2, b地址：[B@5e481248
//        3: sr1地址：[B@5e2de80c, sr2地址：[B@1d44bcfa, sr3地址：[B@266474c2, b地址：[B@66d3c617
//        4: sr1地址：[B@5e2de80c, sr2地址：[B@1d44bcfa, sr3地址：[B@266474c2, b地址：[B@63947c6b
//        5: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@2b193f2d
//        6: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@355da254
//        7: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@4dc63996
//        8: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@d716361
//        9: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@6ff3c5b5
//        10: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@3764951d
//        11: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@4b1210ee
//        12: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@4d7e1886
//        13: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@3cd1a2f1
//        14: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@2f0e140b
//        15: sr1地址：null, sr2地址：null, sr3地址：null, b地址：[B@7440e464
//        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//        at javaref.软引用.main(软引用.java:53)

        List<byte[]> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            byte[] b = new byte[1024 * 512];
            System.out.println(String.format("%s: sr1地址：%s, sr2地址：%s, sr3地址：%s, b地址：%s", i, sr1.get(), sr2.get(), sr3.get(), b));
            list.add(b);
            TimeUnit.SECONDS.sleep(1);
        }

    }

}
