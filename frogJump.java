/*
Problem Statement: Minimum Cost Frog JumpDescription:
You are given an $N \times N$ matrix representing a grid
where 1 indicates a valid space (a lily pad or platform)
the frog can jump onto, and 0 indicates an empty space
(water) where the frog cannot go.The frog can move to
any valid adjacent cell (up, down, left, or right) with
the following cost rules:Horizontal Move: Moving left
or right to an adjacent 1 costs 0 jumps.Vertical Move:
Moving up or down to an adjacent 1 costs 1 jump.
Given the starting coordinates $(sX, sY)$ and the
destination coordinates $(tX, tY)$, find the minimum
jump cost required for the frog to reach the destination.
Input FormatAn integer $N$ representing the size of the
$N \times N$ matrix.$N$ lines, each containing $N$ integers (0 or 1),
representing the grid.Four integers $sX$, $sY$, $tX$, and $tY$
representing the starting row, starting column, target row, and
target column, respectively (0-indexed).Output FormatA single
integer representing the minimum cost (number of vertical jumps)
to reach the destination.
Sample Test CasesTest Case 1Input
:Plaintext
3
1 1 1
0 0 1
0 0 1
0 0 2 2
Output:
Plaintext 2
Explanation of Test Case 1:Start at (0, 0).Move right to (0, 1) -> Cost:
0.Move right to (0, 2) -> Cost: 0.Move down to (1, 2) -> Cost: 1.
Move down to (2, 2) -> Cost: 2.Total Minimum Cost = 2.
Test Case 2Input:
Plaintext
4
1 0 1 1
1 0 1 0
1 1 1 0
0 0 1 1
0 0 3 3
Output:Plaintext
3

 */

import java.util.*;
class Solution{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] grid = new int[n][n];
        for(int i = 0;i<n;i++){
            for(int j =0; j<n; j++){
                grid[i][j] = sc.nextInt();
            }
        }
        int sx = sc.nextInt();
        int sy = sc.nextInt();
        int ex = sc.nextInt();
        int ey = sc.nextInt();
        int[][] hor = {{0,1},{0,-1}};
        int[][] ver = {{-1,0},{1,0}};
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{sx,sy,0});
        int[][] dist = new int[n][n];
        for(int i = 0;i<n;i++)
            Arrays.fill(dist[i],Integer.MAX_VALUE);
        int ans = -1;
        while(!queue.isEmpty()){
            int[] curr = queue.pollFirst();
            if(curr[0]==ex && curr[1]==ey){
                ans = curr[2];
                break;
            }
            for(int[] dir : hor){
                int nx = curr[0]+dir[0];
                int ny = curr[1]+dir[1];
                if(nx>=0 && ny>=0 && nx<n && ny<n && grid[nx][ny]==1 && dist[nx][ny]>curr[2]){
                    dist[nx][ny] = curr[2];
                    queue.offerFirst(new int[]{nx,ny,curr[2]});
                }
            }
            for(int[] dir : ver){
                int nx = curr[0]+dir[0];
                int ny = curr[1]+dir[1];
                if(nx>=0 && ny>=0 && nx<n && ny<n && grid[nx][ny]==1 && dist[nx][ny]>curr[2]+1){
                    dist[nx][ny] = curr[2]+1;
                    queue.offerLast(new int[]{nx,ny,curr[2]+1});
                }
            }
        }
        System.out.println(ans);
    }
}
