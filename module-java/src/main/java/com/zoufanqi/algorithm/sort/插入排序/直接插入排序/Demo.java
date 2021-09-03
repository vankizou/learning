package com.zoufanqi.algorithm.sort.插入排序.直接插入排序;

/**
 * 直接插入排序
 *
 * 排序步骤：
 * 1. 从第一个元素开始，该元素可以认为已经被排序
 * 2. 取出下一个元素，在已经排序的元素序列中从后向前扫描
 * 3. 如果该元素（已排序）大于新元素，将该元素移到下一位置
 * 4. 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
 * 5. 将新元素插入到该位置后
 * 6. 重复步骤2~5
 *
 * 排序复杂度：
 * 平均时间复杂度	最好情况	最坏情况	空间复杂度
 * O(n²)	    O(n)	O(n²)	O(1)
 *
 * @author: ZOUFANQI
 * @create: 2021-08-30 10:15
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
        for (int i = 1, len = arr.length; i < len; i++) {
            int idxCursor = i;          // 移动游标

            for (int j = i - 1; j >= 0; j--) {
                if (arr[idxCursor] >= arr[j]) {
                    // 已经放到自己该待的位置
                    break;
                } else {
                    arr[j] = arr[j] ^ arr[idxCursor];
                    arr[idxCursor] = arr[j] ^ arr[idxCursor];
                    arr[j] = arr[j] ^ arr[idxCursor];
                    idxCursor--;
                }
            }
        }
    }
}
