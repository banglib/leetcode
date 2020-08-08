package com.banglib.leetcode.editor.cn;

//给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。 
//
// 示例 1： 
//
// 输入: "babad"
//输出: "bab"
//注意: "aba" 也是一个有效答案。
// 
//
// 示例 2： 
//
// 输入: "cbbd"
//输出: "bb"
// 
// Related Topics 字符串 动态规划 
// 👍 2533 👎 0

/**
 *@name:最长回文子串
 *@author:banglib
 *使用动态规划,判断一个字符串是不是会问字符串,那么其依次缩小的子串也必定是一个回文字符串,如果依次
 * 缩小的字符串不是一个回文字符串,那么该字符串也不是一个回文字符串
 * @see com.banglib.leetcode.editor.cn.zui-chang-hui-wen-zi-chuan-by-leetcode-solution.md
 **/

public class LongestPalindromicSubstring{
  public static void main(String[] args) {
      Solution solution = new LongestPalindromicSubstring().new Solution();
      //TODO 测试一下吧
      System.out.println(solution.longestPalindrome("aaabaaaa"));
//      System.out.println(solution.longestPalindrome("babad"));

  }
      //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String longestPalindrome(String s) {
        //1.判断字符串长度,如果长度小于等于1,那么该字符一定是一个回文字符串
        if(s.length() < 2){
            return s;
        }
        //2.设置初始值,记录开始位置,和回文字符串的最大位置,max = 1 是因为会s.substring方法会排除掉末位置字符
        int begin =0,max = 1;
        //3.初始化一个二维数组,这个二维数组用来确定一个区间,如果一个字符串是回文字符串,
        // 那么该回文字符串的区间内一定是一个回文字符串
        Boolean[][] dp = new Boolean[s.length()][s.length()];
        //4.循环确定字符串
        for(int i = 1;i<s.length();i++){
            for(int j = 0;j<i;j++){
                if(s.charAt(i) != s.charAt(j)){
                    dp[j][i] = false;
                }else{
                    if((i - 1) - (j + 1) < 1){
                        dp[j][i] = true;
                    }else{
                        dp[j][i] = dp[j+1][i-1];
                    }
                }
                if(dp[j][i] && i - j + 1 > max){
                    max = i - j + 1;
                    begin = j;
                }
            }
        }
        //返回最长的回文字符串
        return s.substring(begin,begin + max);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

  }