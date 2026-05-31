/*
A person is running a marathon of distance D with limited energy H.
He can change his pace every 1 km. Every pace consumes some amount of energy.
Given 5 paces in increasing order of time, i.e. time taken to run 1 km and energy consumed in 1km.
You need to find the least amount of time required to complete the marathon within the given amount of energy.
Paces are given such that the combination always completes the marathon.

Input format:
The first line contains the number of test cases T.
Each test case is given as follows:
- The first line of each test case contains the 2 integers D and H (D is the distance and H is the energy)
- The next 5 lines are as follows:
    - There are 3 integers (M, S and E) where: M is the minutes and S is the seconds required to run 1 km
      and E is the energy consumed in 1 km.

Output Format:
Print the following:
# followed by test case number followed by minimum time in mutes followed by seconds
Eg: #1 153 20

Eg: D = 30, H = 130
5 Paces: (M min S sec E energy)
4 50 7
5 0 5
5 10 4
5 20 3
5 30 2

Output: #1 153 20
*/

import java.util.*;
class Pair{
    int min;
    int energy;
    Pair(int hr, int min, int energy){
        this.min = 60*hr + min;
        this.energy = energy;
    }
}
class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int d = sc.nextInt();
        int h = sc.nextInt();
        Pair[] pace = new Pair[5];
        for(int i = 0;i<5;i++){
            pace[i] = new Pair(sc.nextInt(),sc.nextInt(),sc.nextInt());
        }
        int[][] dp = new int[d+1][h+1];
        //dp[i][j] = min time taken to run dist i with energy j
        for(int i = 0 ; i<=d;i++)
            Arrays.fill(dp[i],Integer.MAX_VALUE);
        dp[0][0] = 0;
        for(int i = 1; i<=d; i++){
            for(int j = 1; j<=h;j++){

                for(Pair p : pace){
                    int remEnergy = j-p.energy;
                    if(remEnergy>= 0 && dp[i-1][remEnergy]!=Integer.MAX_VALUE)
                        dp[i][j] = Math.min(dp[i][j],dp[i-1][remEnergy]+p.min);
                }
            }
        }
        
        int minTime = Integer.MAX_VALUE;
        for(int j = 0 ; j<=h;j++){
            minTime = Math.min(minTime,dp[d][j]);
        }
        System.out.println((minTime/60)+ " "+ (minTime%60));
    }
}
