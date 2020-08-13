package com.banglib.leetcode.editor.cn;

//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。 
//
// 示例: 
//
// 输入: [-2,1,-3,4,-1,2,1,-5,4]
//输出: 6
//解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
// 
//
// 进阶: 
//
// 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。 
// Related Topics 数组 分治算法 动态规划 
// 👍 2289 👎 0

/**
 *@name:最大子序和
 *@author:banglib
 *
 **/

    public class MaximumSubarray{
      public static void main(String[] args) {
          Solution solution = new MaximumSubarray().new Solution();
          //TODO 测试一下吧
          int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
          int maxSubArray = solution.maxSubArray(nums);
          System.out.println(maxSubArray);

      }
      //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int maxSubArray(int[] nums) {

        //1.初始化变量:上一次的结果,最大值
        int pre = 0,ans = nums[0];
        for(int num:nums){
            //2.找到上一次最大值和当前值的比较
            pre = Math.max(pre+num,num);
            //3.和最大值比较
            ans = Math.max(pre,ans);
        }
        return ans;

    }
}
//leetcode submit region end(Prohibit modification and deletion)

  }