package algorithm.leetcode;

//给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
//
// 示例:
//
// 输入: [0,1,0,3,12]
//输出: [1,3,12,0,0]
//
// 说明:
//
//
// 必须在原数组上操作，不能拷贝额外的数组。
// 尽量减少操作次数。
//
// Related Topics 数组 双指针
// 👍 1151 👎 0

/**
 * https://leetcode-cn.com/problems/move-zeroes/
 *
 * @author: ZOUFANQI
 * @create: 2021-08-02 17:20
 **/
public class LC283_移动零 {
    public static void main(String[] args) {
        int[] arr = {123, 22, 12312, 0, 0, 0, 1, 0, 3, 12};
//        int[] arr = {0, 0};
//        int[] arr = {2, 1};
        new LC283_移动零().moveZeroes(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public void moveZeroes(int[] nums) {
        int currFirstZeroIdx = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            if (nums[i] != 0) {
                if (i != currFirstZeroIdx) {
                    nums[currFirstZeroIdx] = nums[i];
                    nums[i] = 0;
                }
                currFirstZeroIdx++;
            }
        }
    }
}
