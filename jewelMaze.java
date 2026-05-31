/*
Problem Statement: Jewel MazeDescription:There is a maze that has one entrance and one exit.
Jewels are placed in passages of the maze. You want to pick up the jewels after getting into the maze
through the entrance and before getting out of it through the exit. You want to get as many jewels as
possible, but you don?t want to take the same passage you used once.  When locations of a maze and jewels
are given, find out the greatest number of jewels you can get without taking the same passage twice
, and the path taken in this case.  Input:There can be more than one test case in the input file.
The first line has T, the number of test cases. Then the totally T test cases are provided in
the following lines (T ? 10).  In each test case, in the first line, the size of the maze
N (1 ? N ? 10) is given. The maze is N � N square-shaped. From the second line through N lines,
information of the maze is given.
0 means a passage
1 means a wall
2 means a location of a jewel
The entrance is located on the upper-most left passage (0, 0) and the exit is located on the
lower-most right passage (N-1, N-1). There is no case where the path from the entrance to the
exit doesn?t exist.Output:From the first line through N lines, mark the path with 3 and output it.
In the N+1 line, output the greatest number of jewels that can be picked up. Each test case must
be output separately with an empty line.Sample Test CasesSample
Input:Plaintext
2
3
0 2 0
1 0 2
0 2 0
4
0 1 0 0
0 2 0 2
0 1 0 1
0 2 0 0
Sample Output:Plaintext
3 3 3
1 3 3
0 3 3
3

3 1 0 0
3 3 3 2
0 1 3 1
0 2 3 3
1

 */

import java.util.*;
class Solution {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        List<int[][]> res = new ArrayList<>();
        int[] numJewels = new int[tc];
        for(int t = 0;t<tc;t++){
            int n = sc.nextInt();
            int[][] grid = new int[n][n];
            for(int i = 0;i<n;i++){
                for(int j = 0;j<n;j++){
                    grid[i][j] = sc.nextInt();
                }
            }
            int[][] currRes = new int[n][n];
            int[] maxJewels = {0};
            int currJewel = (grid[0][0]==2)?1:0;
            grid[0][0] = 3;
            solve(grid,0,0,currRes, currJewel,maxJewels);
            res.add(currRes);
            numJewels[t] = maxJewels[0];
        }
        for(int t = 0; t <tc; t++){
            int[][] curr = res.get(t);
            for(int i = 0;i<curr.length;i++){
                for(int j = 0; j<curr.length;j++){
                    System.out.print(curr[i][j]+" ");
                }
                System.out.println();
            }
            System.out.println(numJewels[t]);
        }

    }
    public static void solve(int[][] grid, int x, int y, int[][] currRes,
                             int currJewels, int[] maxJewels){
        if(x==grid.length-1 && y==grid.length-1){
            if(currJewels>=maxJewels[0]){
                maxJewels[0] = currJewels;
                for(int i = 0;i<grid.length;i++){
                    currRes[i] = Arrays.copyOf(grid[i],grid.length);
                }
            }
            return ;
        }
//        if(dp[x][y][currJewels]!=-1)
//            return dp[x][y][currJewels];
        //System.out.println(x+" "+y+" "+currJewels);
        //int ans = 0;
        int[] dir = {1,0,-1,0,1};
        for(int d= 0; d<4;d++){
            int nx = x+dir[d];
            int ny = y+dir[d+1];
            if(nx>=0 && ny>=0 && nx<grid.length && ny<grid.length && grid[nx][ny]!=1 && grid[nx][ny]!=3){
                int t = grid[nx][ny];
                int updatedJewels = currJewels + ((grid[nx][ny]==2)?1:0);
                grid[nx][ny] = 3;
                solve(grid,nx,ny,currRes,updatedJewels,maxJewels);
                grid[nx][ny] = t;
            }
        }
        //dp[x][y][currJewels] = ans;
        return;
    }
}
