package algorithm.sort.选择排序.直接选择;

/**
 * 直接选择排序
 * <p>
 * 排序步骤：
 * 每次去未排序的队列找最小的放到已排序的最后
 * <p>
 * 排序复杂度：
 * 平均时间复杂度	最好情况	最坏情况	空间复杂度
 * O(n²)	    O(n²)	O(n²)	O(1)
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
        for (int i = 0, len = arr.length; i < len; i++) {
            int min = i;

            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min == i) {
                continue;
            }

            arr[min] = arr[min] ^ arr[i];
            arr[i] = arr[min] ^ arr[i];
            arr[min] = arr[min] ^ arr[i];
        }
    }
}
