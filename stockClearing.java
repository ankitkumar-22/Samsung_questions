/*
https://leetcode.com/problems/minimum-time-to-make-array-sum-at-most-x/

Inventory Management:You are in charge of maintaining inventory for a warehouse.
You have an initial stock of goods given by an array $A$ of size $N$.Your day starts 
off by getting an inflow of goods given by another array $B$ of size $N$ (so the stock
becomes $A[i] = A[i] + B[i]$ for all $i$).After that, you can choose any one good, and
export it, making its stock 0.Before you leave for the day, you have to report the total
stock of all items currently in the warehouse to the headquarters.Your task is to find the
minimum number of days required to make the total stock of all items $\le K$, where $K$ is
given. If it is impossible to ever make the stock $\le K$, output -1.Input Format:The first 
line contains an integer $T$, the number of test cases.For each test case:The first line 
contains two integers $N$ (number of items) and $K$ (target max stock).The next $N$ lines 
each contain two integers $A[i]$ (initial stock) and $B[i]$ (daily inflow) for the $i$-th item.
Output Format:For each test case, output "#t days" where t is the test case number (starting from 1)
and days is the minimum number of days required.

Test CasesStandard Input:Plaintext5
3 10
2 1
3 1
4 1
3 15
5 1
5 2
5 3
2 1
5 10
5 10
2 5
10 2
10 2
3 0
0 1
0 2
0 3
Expected Standard Output:Plaintext#1 0
#2 0
#3 -1
#4 2
#5 0

  */

//in this we follow a pick and not pick approach 
//we decide whether we want to choose to clear this stock at current day number
//evem though it is a pick and not pick approach we sort it because 
//we want to clear the item that is piling up quickly (>b value) at the last day possible
//Essentially by sorting we fix the sequence otherwise it would have been a complex set of choices
//multiple items and each item at diff day , so a lot of combinations. by sorting we eliminate one dimension of sorting
class Solution {
    public int minimumTime(List<Integer> nums1, List<Integer> nums2, int x) {
        int n = nums1.size();
        int suma =0;
        int sumb = 0;
        List<int[]> nums = new ArrayList<>();
        for(int i = 0;i<n;i++){
            nums.add(new int[]{nums1.get(i),nums2.get(i)});
            suma+=nums1.get(i);
            sumb+=nums2.get(i);
        }
        if(suma<=x)
            return 0;
        Collections.sort(nums,((a,b)->(b[1]-a[1])));
        int[][] dp = new int[n+1][n+1];
        for(int i = 0;i<=n;i++)
            Arrays.fill(dp[i],-1);
        for(int i = 1;i<=n;i++){
            int rem = suma+sumb*i - solve(nums,0,i,dp);
            if(rem<=x)
                return i;
        }
        return -1;

    }
    public int solve(List<int[]> nums, int idx, int days,int[][] dp){
        if(idx==nums.size())
            return 0;
        if(days == 0)
            return 0;
        if(dp[idx][days]!=-1)
            return dp[idx][days];
        int max = 0;
        //choice 1 skip this 
        max = Math.max(max,solve(nums,idx+1,days,dp));
        //choice 2 we eliminate this at current day
        max = Math.max(max,solve(nums,idx+1,days-1,dp)+nums.get(idx)[0]+nums.get(idx)[1]*days);
        return dp[idx][days] = max;
    }
}


/*

import java.util.*;
class Solution{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        int[] res = new int[tc];
        for(int t = 0;t<tc;t++){
            int N = sc.nextInt();
            int K = sc.nextInt();
            List<int[]> stock = new ArrayList<>();
            int sumInitial = 0;
            for(int i = 0;i<N;i++){
                int[] temp = new int[2];
                temp[0] = sc.nextInt();//intial stock
                sumInitial+=temp[0];
                temp[1] = sc.nextInt();//daily incoming stock
                stock.add(temp);
            }
            //if(temp)
            //sort by decreasing order of incoming daily stock
            //we want to clear the stock with max incoming on the last day
            Collections.sort(stock,((a,b)->b[1]-a[1]));
            int left = 0;
            int right = 1000;
            int ans = -1;
            while(left<=right){
                int mid = (left+right)/2;//total number of days
                int total = 0;
                for(int i = 0;i<stock.size();i++){
                    total += stock.get(i)[0]+stock.get(i)[1]*mid;
                }
                int saving = 0;
                int idx = 0;
                int day = mid;
                while(day>0 && idx<stock.size()){
                    saving+= stock.get(idx)[0]+stock.get(idx)[1]*day;
                    day--;
                    idx++;
                }
                if(total-saving<=K){
                    ans = mid;
                    right = mid-1;
                }else
                    left = mid+1;
            }
            res[t]= ans;
        }
        for(int i =  0;i<tc;i++)
            System.out.println(res[i]);
    }
}

*/
