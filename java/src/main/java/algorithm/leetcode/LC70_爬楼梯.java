package algorithm.leetcode;

//假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
//
// 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
//
// 注意：给定 n 是一个正整数。
//
// 示例 1：
//
// 输入： 2
//输出： 2
//解释： 有两种方法可以爬到楼顶。
//1.  1 阶 + 1 阶
//2.  2 阶
//
// 示例 2：
//
// 输入： 3
//输出： 3
//解释： 有三种方法可以爬到楼顶。
//1.  1 阶 + 1 阶 + 1 阶
//2.  1 阶 + 2 阶
//3.  2 阶 + 1 阶
//
// Related Topics 记忆化搜索 数学 动态规划
// 👍 1780 👎 0

/**
 * https://leetcode-cn.com/problems/climbing-stairs/
 *
 * @author: ZOUFANQI
 * @create: 2021-08-03 18:29
 **/
public class LC70_爬楼梯 {
    public static void main(String[] args) {
        int stairs = 5;
        System.out.println(new LC70_爬楼梯().climbStairs(stairs));
    }

    /**
     * 1: 1
     * 2: 2
     * 3: f(1) + f(2)
     * 4: f(2) + f(3)
     * n: f(n-2) + f(n-1)
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n <= 2) return n;
        int f1 = 1;
        int f2 = 2;
        int f3 = 3;
        while ((n = n - 3) > 0) {
            f3 = f1 + f2;
            f1 = f2;
            f2 = f3;
        }
        return f3;
    }
}
