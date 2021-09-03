package com.zoufanqi.algorithm.leetcode;

//给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i,
//ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
//
// 说明：你不能倾斜容器。
//
//
//
// 示例 1：
//
//
//
//
//输入：[1,8,6,2,5,4,8,3,7]
//输出：49
//解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
//
// 示例 2：
//
//
//输入：height = [1,1]
//输出：1
//
//
// 示例 3：
//
//
//输入：height = [4,3,2,1,4]
//输出：16
//
//
// 示例 4：
//
//
//输入：height = [1,2,1]
//输出：2
//
//
//
//
// 提示：
//
//
// n = height.length
// 2 <= n <= 3 * 104
// 0 <= height[i] <= 3 * 104
//
// Related Topics 贪心 数组 双指针
// 👍 2662 👎 0

/**
 * https://leetcode-cn.com/problems/container-with-most-water/
 *
 * @author: ZOUFANQI
 * @create: 2021-08-03 11:28
 **/
public class LC11_盛最多水的容器 {
    public static void main(String[] args) {
//        int[] arr = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int[] arr = {2, 3, 4, 5, 18, 17, 6};
        System.out.println(new LC11_盛最多水的容器().maxArea(arr));
    }

    public int maxArea(int[] height) {
        return maxArea2(height);
    }

    public int maxArea2(int[] height) {
        int max = 0;
        for (int i = 0, j = height.length - 1; i < j; ) {
            int minHeight = height[i] < height[j] ? height[i++] : height[j--];
            int area = (j - i + 1) * minHeight;
            max = Math.max(area, max);
        }
        return max;
    }

    /**
     * 超时了
     *
     * @param height
     * @return
     */
    public int maxArea1(int[] height) {
        int max = 0;
        for (int i = 0, len = height.length; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int currArea = (j - i) * (height[i] > height[j] ? height[j] : height[i]);
                if (currArea > max) {
                    max = currArea;
                }
            }
        }
        return max;
    }

}
