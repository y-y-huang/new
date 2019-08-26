//递归爬楼梯
class Solution {
    public int climbStairs(int n) {
        if(n==1){
            return 1;
        }
        //写在一起，如果输入2，返回1，不对，明确基本单元结果，明确状态转化方程，递归的核心
        if(n==2){
            return 2;
        }
        return climbStairs(n-1)+climbStairs(n-2);
        
    }
}

//回溯求解八皇后
public class WolfQueen {
    int max = 8;      //皇后的值
    
    int[] array = new int[max];    //结果数组
 
    public static void main(String[] args) {
        new WolfQueen().check(0);
    }
 
    /**
     * n代表当前是第几个皇后
     * @param n
     * 皇后n在array[n]列
     */
    private void check(int n) {
        //终止条件是最后一行已经摆完，由于每摆一步都会校验是否有冲突，所以只要最后一行摆完，说明已经得到了一个正确解
        if (n == max) {
            print();
            return;
        }
        //从第一列开始放值，然后判断是否和本行本列本斜线有冲突，如果OK，就进入下一行的逻辑
        for (int i = 0; i < max; i++) {
            array[n] = i;
            if (judge(n)) {
                check(n + 1);
            }
        }
    }
 
    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }
 
    private void print()  {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + 1 + " ");
        }
        System.out.println();
    }

}


//回溯解决0-1背包

public class Zero_One {
private static int[] p;//物品的价值数组
private static int[] w;//物品的重量数组
private static int c;//最大可以拿的重量
private static int count;//物品的个数

private static int cw;//当前的重量
private static int cp;//当前的价值
static int bestp;//目前最优装载的价值
private static int r;//剩余物品的价值

private static int[] cx;//存放当前解
private static int[] bestx;//存放最终解

public static int Loading(int[] ww,int[] pp, int cc) {
    //初始化数据成员，数组下标从1开始
    count = ww.length - 1;
    w = ww;
    p = pp;
    c = cc;
    cw = 0;
    bestp = 0;
    cx = new int[count+1];
    bestx = new int [count+1];

    //初始化r，即剩余最大价格
    for(int i = 1;i<=count;i++) {
        r += p[i];
    }

    //调用回溯法计算
    BackTrack(1);
    return bestp;   
}

/**
 * 回溯
 * @param t
 */
public static void BackTrack(int t) {
    if(t>count) {//到达叶结点
        if(cp>bestp) {
            for(int i = 1;i<=count;i++) {
                bestx[i] = cx[i];
            }

            bestp = cp;
        }
        return;
    }

    r -= p[t];
    if(cw + w[t] <= c) {//搜索左子树
        cx[t] = 1;
        cp += p[t];
        cw += w[t];
        BackTrack(t+1);
        cp -= p[t];//恢复现场
        cw -= w[t];//恢复现场

    }

    if(cp + r >bestp) {//剪枝操作
        cx[t] = 0;//搜索右子树
        BackTrack(t+1);
    }
    r += p[t];//恢复现场
}

public static void main(String[] args) {
    //测试
    int[] w1 = {0,15,25,40,20,15,24};
    int[] p1 = {0,10,5,20,2,14,23};
    int c1 = 30;
    Loading(w1,p1,c1);
    System.out.println("最优装载为：" + bestp);
    for(int i =1;i<=count;i++) {
        System.out.print(bestx[i] + " ");
    }           
}
}

//分治算法求逆序对个数
public class Main{
    public long count = 0;   //全局变量，使用合并排序，计算逆序对数
    //使用归并排序方法计算数组A中的逆序对数
    public void getReverseCount(int[] A) {
        if(A.length > 1) {
            int[] leftA = getHalfArray(A, 0);   //数组A的左半边元素
            int[] rightA = getHalfArray(A, 1);  //数组A的右半边元素
            getReverseCount(leftA);
            getReverseCount(rightA);
            mergeArray(A, leftA, rightA);
        }
    }
    //根据judge值判断，获取数组A的左半边元素或者右半边元素
    public int[] getHalfArray(int[] A, int judge) {
        int[] result;
        if(judge == 0) {   //返回数组A的左半边
            result = new int[A.length / 2];
            for(int i = 0;i < A.length / 2;i++)
                result[i] = A[i];
        } else {    //返回数组的右半边
            result= new int[A.length - A.length / 2];
            for(int i = 0;i < A.length - A.length / 2;i++)
                result[i] = A[A.length / 2 + i];
        }
        return result;
    }
    //合并数组A的左半边和右半边元素，并按照非降序序列排列
    public void mergeArray(int[] A, int[] leftA, int[] rightA) {
        int len = 0;
        int i = 0;
        int j = 0;
        int lenL = leftA.length;
        int lenR = rightA.length;
         while(i < lenL && j < lenR) {
             if(leftA[i] > rightA[j]) {
                 A[len++] = rightA[j++]; //将rightA[j]放在leftA[i]元素之前，那么leftA[i]之后lenL - i个元素均大于rightA[j]
                 count += (lenL - i);   //合并之前，leftA中元素是非降序排列，rightA中元素也是非降序排列。所以，此时就新增lenL -　i个逆序对
             } else {
                 A[len++] = leftA[i++];
             }
         }
         while(i < lenL)
             A[len++] = leftA[i++];
         while(j < lenR)
             A[len++] = rightA[j++];
    }
    //获取一个随机数数组
    public int[] getRandomArray(int n) {
        int[] result = new int[n];
        for(int i = 0;i < n;i++) {
            result[i] = (int)( Math.random() * 50);  //生成0~50之间的随机数
        }
        return result;
    }
    
    public static void main(String[] args){
        long t1 = System.currentTimeMillis();
        Main test = new Main();
        int[] A = test.getRandomArray(50000);
        test.getReverseCount(A);
        long t2 = System.currentTimeMillis();
        System.out.println("分治法得到结果："+test.count+"， 耗时："+(t2 - t1)+"毫秒");
    }
}

//动态规划
//解决0-1背包
public class Knapsack{
    /** 物品重量 */
    private int weight;
    /** 物品价值 */
    private int value;

    public Knapsack(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
public class DTGHProblem {

    // 所有的物品
    private Knapsack[] bags;
    // 物品的数量
    private int n;
    // 背包总承重
    private int totalWeight;
    // 第一维：当前第几个物品；第二维：当前的背包承重；值：当前背包最大价值
    private int[][] bestValues;
    // 最终背包中最大价值
    private int bestValue;

    public DTGHProblem(Knapsack[] bags, int totalWeight) {
        this.bags = bags;
        this.totalWeight = totalWeight;
        this.n = bags.length;
        if (bestValues == null) {
            // 考虑0的状态+1，防止数组角标越界
            bestValues = new int[n + 1][totalWeight + 1];
        }
    }

    public void solve() {
        // 遍历背包的承重
        for (int j = 0; j <= totalWeight; j++) {
            // 遍历指定物品
            for (int i = 0; i <= n; i++) {
                // 当背包不放入物品或承重为0时，其最大价值均为0
                if (i == 0 || j == 0) {
                    bestValues[i][j] = 0;
                } else {
                    // 如果第 i个物品重量大于总承重，则最优解存在于前 i-1 个背包中
                    if (j < bags[i - 1].getWeight()) {
                        bestValues[i][j] = bestValues[i - 1][j];
                    } else {
                        // 如果第 i个物品不大于总承重，则最优解要么是包含第 i个背包的最优解，
                        // 要么是不包含第 i个背包的最优解， 取两者最大值
                        int weight = bags[i - 1].getWeight();
                        int value = bags[i - 1].getValue();
                        bestValues[i][j] = Math.max(bestValues[i - 1][j], value
                                + bestValues[i - 1][j - weight]);
                    }
                }
            }
        }

        bestValue = bestValues[n][totalWeight];
    }

    public int getBestValue() {
        return bestValue;
    }

}
public class DTGHTest {

public static void main(String[] args) {
    Knapsack[] bags = new Knapsack[] { new Knapsack(2, 13),
            new Knapsack(1, 10), new Knapsack(3, 24), new Knapsack(2, 15),
            new Knapsack(4, 28), new Knapsack(5, 33), new Knapsack(3, 20),
            new Knapsack(1, 8) };
    int totalWeight = 12;
    DTGHProblem problem = new DTGHProblem(bags, totalWeight);

    problem.solve();
    System.out.println(problem.getBestValue());
}
}

//最小路径之和
public class MinPath {
    public static void main(String[] args) {
        int[][] grid = {
                {1,2},
                {5,6},
                {1,1}
        };
        System.out.println(minPathSum(grid));
    }
    public static int minPathSum(int[][] grid) {
        if(grid.length == 0){
            return 0;
        }
        int[][] dp = new int[grid.length][grid[0].length];
        for(int i = 0;i < dp.length;i++) {
            for(int j = 0; j < dp[i].length;j++) {
                if(i == 0 && j != 0){
                    dp[i][j] = dp[i][j-1]+grid[i][j];
                }else if(j == 0 && i != 0){
                    dp[i][j] = dp[i-1][j]+grid[i][j];
                }else if(i == 0 && j == 0){
                    dp[i][j] = 0+grid[i][j];
                }else {
                    dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1])+grid[i][j];
                }
            }
        }
 
        return dp[grid.length-1][grid[0].length-1];
    }
}

//莱文斯坦最短编辑距离
public class MinDistance {  
    public static void main(String[] args) {  
        String str1 = "sailn";  
        String str2 = "failing";  
        int[][] dp = new int[str1.length()+1][str2.length()+1];  
        int dis = calculateDis(str1, str1.length(), str2, str2.length(), dp);  
        display(dp,str1,str2);  
        System.out.println("最短编辑距离为:"+dis);  
          
    }  
      
    public static int calculateDis(String str1,int index1,String str2,int index2,int[][] dp){  
        if(index1==0 && index2==0){  
            dp[index1][index2] = 0;  
            return 0;  
        }  
          
        if(index1==0 && index2>0){  
            dp[index1][index2] = index2;  
            return index2;  
        }  
          
        if(index1>0 && index2==0){  
            dp[index1][index2] = index1;  
            return index1;  
        }  
          
        int t1 = calculateDis(str1, index1-1, str2, index2, dp)+1;  
        int t2 = calculateDis(str1, index1, str2, index2-1, dp)+1;  
        int t3 = calculateDis(str1, index1-1, str2, index2-1, dp);  
        if(str1.charAt(index1-1)!=str2.charAt(index2-1)){  
            t3 = t3+1;  
        }  
        int result =  min(t1,t2,t3);  
        dp[index1][index2] = result;  
        return result;  
          
    }  
      
    private static int min(int a,int b,int c){  
        return a<b?(a<c?a:c):(b<c?b:c);  
    }  
      
    private static void display(int[][] dp,String str1,String str2){  
        System.out.print("\t\t");  
        for(char a :str2.toCharArray()){  
            System.out.print(a+"\t");  
        }  
        System.out.println();  
        int count = -1;  
        for(int[] a : dp){  
            if(count>=0){  
                System.out.print(str1.charAt(count));  
            }  
            System.out.print("\t");  
              
            for(int b:a){  
                System.out.print(b+"\t");  
            }  
            System.out.println();  
            count++;  
             
        }  
    }  
}  

//数据序列最长递增子序列
import java.util.ArrayList;
public class LIS {
    public static void main(String[] args) {
        int[] num = {65, 22, 34, 99, 27, 25, 100, 33, 35, 200, 44, 57};
        lis(num);
    }

    private static void lis(int[] num) {
        if (num == null) {
            return;
        }

        int max = 0;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = 0; i < num.length; i++) {
            if (max == 0) {
                arrayList.add(num[i]);
                max ++;
                continue;
            }
            if (arrayList.get(max - 1) < num[i]) {
                arrayList.add(num[i]);
                max ++;
            } else {
                // 找到比它大，但是最小的那个数据，替换掉
                int left = binarySearch(arrayList, max, num[i]);
                arrayList.set(left, num[i]);
            }
        }

        System.out.println("\n\n最大递增长度为:" + max);
    }

    private static int binarySearch(ArrayList<Integer> arrayList, int right, int n) {
        int left = 0;
        while (left < right){
            int mid = (right + left) / 2;
            if (arrayList.get(mid) >= n) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
