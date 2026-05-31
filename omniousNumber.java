/*
https://www.geeksforgeeks.org/samsung-interview-experience-set-40-campus-white-box-testing/
https://www.geeksforgeeks.org/samsung-rd-sri-noida-on-campus-interview-experience/
https://code.hackerearth.com/01ac52j?key=b462f0a802c8c1faf1d87f2b1353b9ce

Company  A  is discarding product numbers that contain few specific digits a specific number of time or
more than that. You are given a range and you need to find product numbers that are possible.

Example-

Range: 24 to 12943

Numbers that should not come: 1, 3, 5
Number of times these number should not occur: 3 or more

In above case all two digit numbers are valid.

In three digit: 111, 113, 115, 311, 331, 333, 511, 533, 555 are not valid.

In four digit: All the numbers containing above 3 digit numbers are not valid.

Eg: 11223 is not valid, 11222 is valid.
*/

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String start = String.valueOf(sc.nextInt()-1);
        String end = String.valueOf(sc.nextInt());
        int[][][] dp1 = new int[20][2][3];
        int[][][] dp2 = new int[20][2][3];
        for(int i = 0;i<20;i++){
            for(int j = 0 ;j<2;j++){
                Arrays.fill(dp1[i][j],-1);
                Arrays.fill(dp2[i][j],-1);
            }
        }
        System.out.println((solve(end,0,true,0,dp1)-solve(start,0,true,0,dp2)));
    }
    public static int solve(String num, int idx, boolean tight,int count,int[][][] dp){
        if(idx==num.length())
            return 1;
        int tightIdx = (tight)?1:0;
        if(dp[idx][tightIdx][count]!=-1)
            return dp[idx][tightIdx][count];
        int limit = (tight)?num.charAt(idx)-'0':9;
        int ans = 0;
        for(int i = 0; i<=limit; i++){
            int updatedCount = count;
            if(i==1 || i==3 || i==5){
                if(count==2)
                    continue;
                updatedCount++;
            }

            ans += solve(num,idx+1,(tight &&(i==num.charAt(idx)-'0')),updatedCount,dp);
        }
        return dp[idx][tightIdx][count] = ans;
    }
}
