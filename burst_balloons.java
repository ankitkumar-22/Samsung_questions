/*
This is almost similar to the burst ballons question on leetcode but with a twist

There are n balloons and n bullets and each balloon is assigned with a particular number (point).
Whenever a particular balloon is shot the no of points increases by
1.the multiplication of point assigned to balloon on left and that of right side.
2.point assigned to left if no right exists
3.point assigned to right if no left exists.
4.the point assigned to itself if no other balloon exists.
 
You have to output the maximum no of points possible.
 
Input-1
1 2 3 4
 
Output-1
20

Input-2
1 0 2 3 0 4

Output-2
34

**/

import java.util.ArrayList;

class Solution {
    Integer[][] dp;
    
    public int maxCoins(int[] originalNums) {
        // Step 1: Filter out 0s (as per Samsung problem rules)
        ArrayList<Integer> filteredList = new ArrayList<>();
        for (int num : originalNums) {
            if (num > 0) {
                filteredList.add(num);
            }
        }
        
        int n = filteredList.size();
        
        // Step 2: Pad the array with 1s at the boundaries
        int[] arr = new int[n + 2];
        arr[0] = 1;
        arr[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            arr[i + 1] = filteredList.get(i);
        }
        
        // Step 3: Initialize DP and solve
        dp = new Integer[n + 2][n + 2];
        return solve(arr, 1, n);
    }
    
    public int solve(int[] arr, int start, int end) {
        // Base case: No balloons left in this interval
        if (start > end) {
            return 0;
        }
        
        // Return memoized result if available
        if (dp[start][end] != null) {
            return dp[start][end];
        }
        
        int max = 0;
        
        // Assume 'k' is the LAST balloon to be popped in the interval [start, end]
        for (int k = start; k <= end; k++) {
            int currPoints;
            
            // RULE 4: If this is the absolute last balloon remaining in the whole array
            if (start == 1 && end == arr.length - 2) {
                currPoints = arr[k]; 
            } else {
                // RULES 1, 2, 3: Multiply the adjacent unpopped balloons
                currPoints = arr[start - 1] * arr[end + 1];
            }
            
            // Recursively calculate max points for left and right intervals
            int totalPoints = currPoints + solve(arr, start, k - 1) + solve(arr, k + 1, end);
            
            max = Math.max(max, totalPoints);
        }
        
        return dp[start][end] = max;
    }
}


//Below is the solution of the actual burst ballons problem
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
