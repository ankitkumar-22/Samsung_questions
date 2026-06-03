/*
? Apple Game - Right Turn Only

Mr. Sam-Sung Na purchased a newly released game.

This game is played on an N x N map. The goal of the game is to eat the apples in order.

Rules:
- Apples appear on the map according to their number, starting from 1.
- Apple 2 appears only after Apple 1 is eaten, Apple 3 after Apple 2, and so on.
- You cannot eat an apple out of order.
- You cannot move into cells marked as traps (-1 on the map).

Movement:
- The player starts at the top-left corner of the map (0, 0).
- The player initially moves to the right (?).
- The player continues to move in the current direction unless:
    - They hit a wall
    - They hit a trap
    - They make a right turn
- Due to a keyboard malfunction, the player can only make right turns.
- Left turns and backward movement are not possible.

Goal:
- Eat all apples in order while making the minimum number of right turns.
- If it's impossible to eat all the apples in order, return -1.

Input:
- First line: T, number of test cases.
- For each test case:
  - First line: N (size of the map)
  - Next N lines: N integers each representing the map
    - 0: empty cell
    - -1: trap
    - Positive integers: apple numbers (1 to M)

Output:
- For each test case, print "#t ans"
  - t = test case number starting from 1
  - ans = minimum number of right turns needed to eat all apples
  - If impossible, print -1

Constraints:
- 5 ? N < 60
- 2 ? M ? 150

*/

import java.util.*;
import java.io.*;
class Solution{
    static int n;
    static int[][] applesLoc;
    static int m;
    static int[][] grid;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        grid = new int[n][n];
        for(int i = 0;i<n;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0 ;j<n;j++){
                grid[i][j] = Integer.parseInt(st.nextToken());
                if(grid[i][j]>0)
                    m++;
            }
        }
        applesLoc = new int[m+1][2];
        for(int i = 0;i<n;i++){
            for(int j = 0;j<n;j++){
                if(grid[i][j]>0){
                    applesLoc[grid[i][j]][0] = i;
                    applesLoc[grid[i][j]][1] = j;
                }
            }
        }
        int startx = 0;
        int starty = 0;
        int dir = 0;
        int totalTurns = 0;
        for(int i = 1;i<=m;i++){
            int targetx = applesLoc[i][0];
            int targety = applesLoc[i][1];
            int[] res = bfs(startx,starty,targetx,targety,dir);
            startx = targetx;
            starty = targety;
            dir = res[1];
            totalTurns+=res[0];
        }
        System.out.println(totalTurns);
    }
    public static int[] bfs(int startx, int starty, int targetx, int targety, int dir){
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{startx,starty,dir,0});
        int[][][] dist = new int[n][n][4];
        for(int[][] row: dist) {
            for(int[] subrow: row)
                Arrays.fill(subrow, Integer.MAX_VALUE);
        }
        dist[startx][starty][dir]=0;
        int[] dr = {0,1,0,-1};
        int[] dc = {1,0,-1,0};
        while(!queue.isEmpty()){
            int r = queue.peek()[0];
            int c = queue.peek()[1];
            int d = queue.peek()[2];
            int h = queue.poll()[3];
            if(r==targetx && c==targety){
                return new int[]{dist[r][c][d],d};
            }
            if(dist[r][c][d]<h)
                continue;
            int nx = r+dr[d];
            int ny = c+dc[d];
            if(nx>=0 && ny>=0 && nx<n && ny<n && grid[nx][ny]!=-1 && dist[nx][ny][d]>dist[r][c][d]){
                dist[nx][ny][d] = dist[r][c][d];
                queue.offerFirst(new int[]{nx,ny,d,dist[nx][ny][d]});
            }
            int nd = (d+1)%4;
            if(dist[r][c][nd]>dist[r][c][d]+1){
                dist[r][c][nd]=dist[r][c][d]+1;
                queue.offerLast(new int[]{r,c,nd,dist[r][c][nd]});
            }
        }
        return new int[]{-1,-1};
    }
}
