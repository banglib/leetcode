#### 题解

本题需要解决正则表达式匹配的问题，需要实现实现一个支持 '.' 和 '*' 的正则表达式匹配。我看了大多数题解直接就开始定义dp数组，让人不知道是怎么来。我觉得一个优秀的题解应该是侧重于思考方式的讲解以及解题经验的总结，而不只是答案的说明。那对于本题来说，我们是怎么思考的呢？

要明确一点，本题归根结底是一个字符串匹配的基本问题，只是需要实现其中的特殊情况， '.' 和 '*'。

##### 问题一、普通的两个字符串如何匹配？

首先，对于字符串s与p，如何进行两个字符串匹配呢？

###### 方法1 ：迭代法

将两个字符串的每个对应元素一一对比，如果两个字符串长度相等，且整个过程每个字符都能对上则说明两个字符串匹配上了

```java
if(s.length() != p.length()) return false;
for(int i = 0,j = 0; i<s.length() && j < p.length(); i++,j++){
    if(s.charAt(i) != s.charAt(j)) return false;
}
return true;
```

上述的迭代过程i和j其实是一样的，可以使用一个i来表示，但是为了下文叙述*dp[i][j]*方便，我这里特别的写了两个。

###### 方法二：递归法

递归的一个非常重要的点就是：**不去管函数的内部细节是如何处理的，我们只看其函数作用以及输入与输出**。那现在来设计递归关系。

设计函数match：

- 函数作用：判断两个字符字符串是否匹配
- 输入：两个字符串s、p，字符串匹配的起始位置i、j
- 输出：匹配成功输出true，匹配失败输出false；

```java
public boolean match(String s, String p, int i, int j){
    //如果两个字符串长度不相等，肯定不能匹配
    if(s.length() != p.length()) return false;
    //如果两个字符都是null，那么也是匹配的
    if(s.equals("") && s.equals("")) return true;
    //判断两个字符串的第一个字符是否相等
    //如果相等，则比较后面的字符串
    //如果不相等，直接返回false
    boolean result = true;
    if(i < s.length() && j < p.length()){
        if(s.charAt(i) == p.charAt(j)) match(s,p,i+1,j+1);
        else result = false;
    }    
    return result;
}
```



##### 问题二、向动态规划的过渡

如果能写成递归，基本就能改成动态规划，本题是能改成动态规划的，还是上面最基本的问题：两个字符串如何匹配？

因为是两个字符串，这里我们需要声明一个二维数组布尔数组*dp[i][j]*

*dp[i][j]*：**表示字符串中s中的前i个字符与字符串p中的前j个字符是否能够匹配**。如果能匹配则为true，反之为false；

假如我们已经算出了前i-1个字符与前j-1个字符的匹配情况了，那如何计算*dp[i][j]*呢？

- 如果*s[i] == p[j]*，说明*dp[i][j]*取决于*dp[i-1][j-1]*
- 如果![s\[i\]\nep\[j\] ](./p__s_i__ne_p_j__.png) ，说明两个字符串不匹配

###### 对于 '.' 和 '*'的处理

由于'.' 和 '*'都是在p中，所以

- 当*p[j] == '.'*的时候，说明这个字符什么都可以当，和之前*s[i] == p[j]*是一样的，故*dp[i][j] == dp[i-1][j-1]*.

- 当*p[j] == '*' *的时候这要分两种情况：

  如果*前面的字符*p[j-1]*能与s当前的字符*s[i]*匹配上的话，那就*dp[i][j]*的状态就去取决于*dp[i-1][j]*，也是就相当于看前面的状态。假如不能匹配的话，是不是就代表匹配失败了呢？不是的，因为这毕竟是一个 \* 号而不是真正的要匹配的字符,**说白了大不了我不用他来匹配了，也就是使用0次**，，那就*dp[i][j]*的状态就去取决于*dp[i][j-2]*。那这两种状态只要能满足其中一种就可以了，即：

  
  ![dp\[i\]\[j\]\begin{cases}dp\[i-1\]\[j\]\\||\\dp\[i\]\[j-2\],\\s\[i\]==p\[j-1\]\\dp\[i\]\[j-2\],\\s\[i\]\nep\[j-1\]\\\end{cases} ](./p_____dp_i__j____begin{cases}___dp_i-1__j____||___dp_i__j-2_,____s_i__==_p_j-1____dp_i__j-2_,____s_i__ne_p_j-1____end{cases}____.png) 
  

![10_2.png](https://pic.leetcode-cn.com/f3df9407f81b2327d2ac21a6e896da4f741a1439cbe2869f5b2b452db7bb7560-10_2.png)


接下来把上述过程翻译成代码即可。

#### 代码

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean [m+1][n+1];
        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if(p.charAt(j-1) != '*'){
                    dp[i][j] = match(s,p,i,j) ? dp[i-1][j-1]:false;
                }else{
                    dp[i][j] = match(s,p,i,j-1) ? (dp[i][j-2] || dp[i-1][j]) : dp[i][j-2];
                }
            }
        }
        return dp[m][n];

    }
    public boolean match(String s, String p, int i, int j){
        if(i == 0) return false;
        if(p.charAt(j-1) == '.') return true;
        return s.charAt(i-1) == p.charAt(j-1);
    }
}
```


