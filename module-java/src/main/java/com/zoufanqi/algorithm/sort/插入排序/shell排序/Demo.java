package com.zoufanqi.algorithm.sort.插入排序.shell排序;

/**
 * 希尔排序
 * 也称 递减增量排序算法，是插入排序的一种更高效的改进版本。希尔排序是 非稳定排序算法。
 * 希尔排序是基于插入排序的以下两点性质而提出改进方法的：
 * - 插入排序在对几乎已经排好序的数据操作时，效率高，即可以达到线性排序的效率
 * - 但插入排序一般来说是低效的，因为插入排序每次只能将数据移动一
 * <p>
 * 排序步骤：
 * 将待排序数组按照步长gap进行分组，然后将每组的元素利用直接插入排序的方法进行排序；每次再将gap折半减小，循环上述操作；当gap=1时，利用直接插入，完成排序。
 * <p>
 * 排序复杂度：
 * 平均时间复杂度	最好情况	 最坏情况	  空间复杂度
 * O(nlog2n)	O(n)	 O(n²)    O(1)
 *
 * @author: ZOUFANQI
 * @create: 2021-08-30 10:55
 **/
public class Demo {
    public static void main(String[] args) {
        int[] arr = {123, 43, 5, 34, 54, 34, 523, 4, 23, 2, 3423, 4, 23, 34};
        sort(arr);
        for (int e : arr) {
            System.out.printf(e + " ");
        }
    }

    private static void sort(int[] arr) {
        for (int i = arr.length / 2; i > 0; i /= 2) {
            for (int j = i; j < arr.length; j++) {
                for (int k = j; k > 0 && k - i >= 0; k -= i) {
                    if (arr[k] < arr[k - i]) {
                        arr[k] = arr[k] ^ arr[k - 1];
                        arr[k - 1] = arr[k] ^ arr[k - 1];
                        arr[k] = arr[k] ^ arr[k - 1];
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
