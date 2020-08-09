package com.banglib.leetcode.editor.cn;

//将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。 
//
// 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下： 
//
// L   C   I   R
// E T O E S I I G
// E   D   H   N
// 
//
// 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。 
//
// 请你实现这个将字符串进行指定行数变换的函数： 
//
// string convert(string s, int numRows); 
//
// 示例 1: 
//
// 输入: s = "LEETCODEISHIRING", numRows = 3
//输出: "LCIRETOESIIGEDHN"
// 
//
// 示例 2: 
//
// 输入: s = "LEETCODEISHIRING", numRows = 4
//输出: "LDREOEIIECIHNTSG"
//解释:
//
//L     D     R
//E   O E   I I
//E C   I H   N
//T     S     G 
// Related Topics 字符串 
// 👍 777 👎 0

import java.util.ArrayList;
import java.util.List;

/**
 *@name:Z 字形变换
 *@author:banglib
 *
 **/

    public class ZigzagConversion{
      public static void main(String[] args) {
          Solution solution = new ZigzagConversion().new Solution();
          System.out.println(solution.convert("PAYPALISHIRING",3));
      }
      //leetcode submit region begin(Prohibit modification and deletion)
class Solution {

          /***
           * info
           * 		解答成功:
           * 		执行耗时:22 ms,击败了16.64% 的Java用户
           * 		内存消耗:40.9 MB,击败了8.69% 的Java用户
           *
           */
    public String convert(String s, int numRows) {

        //1.初始化一个结果数组,长度为行数的长度
        if(s.length() <= numRows || numRows == 1){
            return s;
        }
        String[] result= new String[s.length()];
        int flagPlace = 0;
        boolean positiveCycle = true;
        //2.循环字符数组,并且在循环的过程中记录结果数组每次的位置,直到字符数组放入结果数组完毕
        for(int i = 0;i<s.length();i++){
             result[flagPlace] = (result[flagPlace] == null?"":result[flagPlace]) + s.charAt(i);
             if(positiveCycle){
                 if(flagPlace == numRows - 1){
                     flagPlace  = flagPlace -1;
                     positiveCycle = false;
                 }else{
                     flagPlace = flagPlace + 1;
                 }
             }else{
                 if(flagPlace == 0){
                     flagPlace  = flagPlace + 1;
                     positiveCycle = true;
                 }else{
                     flagPlace = flagPlace - 1;
                 }
             }
        }
        //3.从结果数组中,依次向下获取值
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            stringBuilder.append(result[i]);
        }
        return stringBuilder.toString();
    }


          /***
           * info
           * 		解答成功:
           * 		执行耗时:7 ms,击败了73.97% 的Java用户
           * 		内存消耗:39.9 MB,击败了84.92% 的Java用户
           *
           */
      public String convert2(String s, int numRows){
        //1.边界条件判断
        if(numRows  == 1){
            return s;
        }
        //2.初始化结果数组
          List<StringBuilder> result = new ArrayList<>(numRows);
          for (int i = 0; i < numRows; i++) {
              result.add(i,new StringBuilder());
          }
         //3.反转记录条件
         int place = 0,flag = -1;
          for (int i = 0; i < s.length(); i++) {
              result.get(place).append(s.charAt(i));
              if(place == 0 || place == numRows - 1){
                  flag = -flag;
              }
              place = place + flag;
          }
          //记录结果
          StringBuilder combineResult = new StringBuilder();
          for (int i = 0; i < result.size(); i++) {
              combineResult.append(result.get(i));
          }
          return combineResult.toString();
      }



      }
//leetcode submit region end(Prohibit modification and deletion)

  }