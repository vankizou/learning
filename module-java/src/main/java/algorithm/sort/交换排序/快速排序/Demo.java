package algorithm.sort.交换排序.快速排序;

/**
 * 快速排序
 * <p>
 * 快速排序使用分治策略来把一个序列（list）分为两个子序列（sub-lists）。
 * 排序步骤：
 * 1.分：设定一个分割值，并根据它将数据分为两部分
 * 2.治：分别在两部分用递归的方式，继续使用快速排序法
 * 3.合：对分割的部分排序直到完成
 * 总结：找个基值，比值小的放左边，比值大的放右边，如此往复进行递归。
 *
 * <p>
 * 算法复杂度：
 * 平均时间复杂度	最好情况	    最坏情况	空间复杂度
 * O(nlog2n)	O(nlog2n)	O(n²)	O(1)
 *
 * @author: ZOUFANQI
 * @create: 2021-08-30 11:09
 **/
public class Demo {
    public static void main(String[] args) {
        int[] arr = {123, 43, 5, 34, 54, 34, 523, 4, 23, 2, 3423, 4, 23, 34};
        sort(arr, 0, arr.length - 1);
        for (int e : arr) {
            System.out.printf(e + " ");
        }
    }

    private static void sort(int[] arr, int start, int end) {
        if (end - start < 2) {
            return;
        }
        int mid = divide(arr, start, end);
        sort(arr, start, mid);
        sort(arr, mid + 1, end);
    }

    private static int divide(int[] arr, int start, int end) {
        int pivot = arr[start];

        while (start < end) {
            while (start < end && arr[end] >= pivot) {
                end--;
            }
            if (start < end) {
                arr[start] = arr[end];
            }

            while (start < end && arr[start] < pivot) {
                start++;
            }
            if (start < end) {
                arr[end] = arr[start];
            }
        }
        arr[start] = pivot;
        return start;
    }
}
