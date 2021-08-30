package algorithm.sort.交换排序.冒泡排序;

/**
 * 冒泡排序
 * <p>
 * 排序步骤：
 * 1. 比较相邻的元素，如果第一个比第二个大，就交换他们两个
 * 2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这步做完后，最后的元素会是最大的数
 * 3. 针对所有的元素重复以上的步骤，除了最后一个
 * 4. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较
 *
 * 算法复杂度：
 * 平均时间复杂度	最好情况	最坏情况	空间复杂度
 * O(n²)	    O(n)	O(n²)	O(1)
 *
 * @author: ZOUFANQI
 * @create: 2021-08-30 11:09
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
        for (int i = 0, len = arr.length - 1; i < len; i++) {
            for (int j = 0; j < len - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    arr[j] = arr[j] ^ arr[j + 1];
                    arr[j + 1] = arr[j] ^ arr[j + 1];
                    arr[j] = arr[j] ^ arr[j + 1];
                }
            }
        }
    }
}
