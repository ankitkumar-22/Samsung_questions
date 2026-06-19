/*
Basically there is a matrix of size M x M where N cars are there. They are at a starting position xi, yi. 
You have to park them at some position in the matrix (p, q) in minimum number of drives. 
If it is not possible to park them in matrix (p,q) then return -1. 

The restrictions are:
You have to move all the cars simultaneously.
You can move 1 distance exactly in 1st drive, 2 distance in 2nd drive and so on.
The paths of cars can overlap.

M range till ±10^17
N range till 100

  
similar question: https://leetcode.com/problems/reach-a-number/

*/

class Solution {
    public int reachNumber(int t) {
        long target = (long) t;
        target = (target<0)?-target:target;
        int left = 0;
        int right = (int)1e6;
        int ans = -1;
        while(left<=right){
            int mid = left + (right-left)/2;
            
            long sum = getSum(mid);
            //System.out.println(mid+" "+sum);
            if(sum>=target){
                right = mid-1;
                ans = mid;
            }
            else
                left = mid+1;
        }
        while((getSum(ans)-target)%2!=0){
            ans++;
        }
        return ans;
    }
    public long getSum(int n){
        return ((long)n*(n+1))/2l;
    }
}
