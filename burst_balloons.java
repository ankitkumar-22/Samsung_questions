//link : https://leetcode.com/problems/burst-balloons/

class Solution {
    Integer[][] dp;
    public int maxCoins(int[] nums) {
        int n = nums.length;
        //create an extended array
        int[] arr = new int[n+2];
        arr[0]= 1;arr[n+1] = 1;
        for(int i = 0;i<n;i++){
            arr[i+1] = nums[i];
        }
        dp = new Integer[n+2][n+2];
        return solve(arr,0,n+1);
    }
    public int solve(int[] arr, int start, int end){
        if(end-start<=1) 
            return 0;
        if(dp[start][end]!=null)
            return dp[start][end];
        
        int best = 0;
        for(int i = start+1;i<end;i++){//this is a counter to keep track of the balloon which is popped last
            int coins = arr[start]*arr[i]*arr[end];
            coins += solve(arr,start,i);
            coins+= solve(arr,i,end);
            best = Math.max(best,coins);
        }
        return dp[start][end] = best;

    }
}
