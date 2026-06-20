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
//actual solution 

    static void solve() throws IOException {
        // your solution here
        //int m = in.nextInt();
        int n = in.nextInt();
        long[][] grid = new long[n][2];
        long maxDist = 0;
        long prevDist = 0;
        boolean flag = true;
        long p = in.nextLong();
        long q = in.nextLong();
        for(int i = 0;i<n;i++){
            grid[i][0] = in.nextLong();
            grid[i][1] = in.nextLong();
            long currDist = Math.abs(grid[i][0]-p)+Math.abs(grid[i][1]-q);
            if(i>0 && prevDist%2!=currDist%2)
                flag = false;
            prevDist = currDist;
            maxDist = Math.max(maxDist,currDist);
        }
        if(!flag) {
            out.println(-1);
            return;
        }
        long left = 0;
        long right = (int)1e17;
        long ans = -1;
        while(left<=right){
            long mid = left + (right-left)/2;
            long sum = getSum(mid);
            if(sum>=maxDist){
                right = mid-1;
                ans = mid;
            }
            else{
                left = mid+1;
            }
        }

        while((getSum(ans)-maxDist)%2!=0)
            ans++;

        out.println(ans);
    }

    public static long getSum(long n){
        return (((long) n *(n+1))/2);
    }



//leetcode solution

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
