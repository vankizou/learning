package com.zoufanqi.algorithm.leetcode;

//编写一个函数来查找字符串数组中的最长公共前缀。
//
// 如果不存在公共前缀，返回空字符串 ""。
//
//
//
// 示例 1：
//
//
//输入：strs = ["flower","flow","flight"]
//输出："fl"
//
//
// 示例 2：
//
//
//输入：strs = ["dog","racecar","car"]
//输出：""
//解释：输入不存在公共前缀。
//
//
//
// 提示：
//
//
// 1 <= strs.length <= 200
// 0 <= strs[i].length <= 200
// strs[i] 仅由小写英文字母组成
//
// Related Topics 字符串 👍 1783 👎 0


/**
 * @author: ZOUFANQI
 * @create: 2021-09-18 17:27
 **/
public class LC14_最长公共前缀 {

    public static void main(String[] args) {
        String[] strs = {"flower", "flow", "flight"};
    }

    public String longestCommonPrefix(String[] strs) {
        final StringBuilder result = new StringBuilder(64);
        int cursor = 0;
        String curS = null;
        out:
        for (; ; ) {
            for (String s : strs) {
                if (cursor > s.length() - 1) {
                    break out;
                }
                curS = s.substring(cursor, cursor + 1);
            }
        }
        return result.toString();
    }

}
