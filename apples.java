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
public class Samsung {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder res;
    static int[][] grid;
    static int[][][] dp;
    static int n;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        res = new StringBuilder();
        while (t-- > 0) {
            fn();
        }
        System.out.println(res);
    }

    public static void fn() throws IOException {
       n = Integer.parseInt(br.readLine());
       grid = new int[n][n];
       int numApples = -1;
       for(int i = 0;i<n;i++){
           st = new StringTokenizer(br.readLine());
           for(int j = 0; j<n;j++){
               grid[i][j] = Integer.parseInt(st.nextToken());
               if(grid[i][j]>0)
                   numApples = Math.max(grid[i][j],numApples);
           }
       }
       int[][] applesLoc = new int[numApples+1][2];
       for(int i = 0;i<n;i++){
           for(int j = 0; j<n; j++){
               if(grid[i][j]>0){
                   applesLoc[grid[i][j]][0] = i;
                   applesLoc[grid[i][j]][1] = j;
               }
           }
       }

       int[][][][] dist = new int[n][n][numApples+1][4];
       for(int[][][] row: dist){
           for(int[][] sr : row){
               for(int[] ssr: sr){
                   Arrays.fill(ssr,Integer.MAX_VALUE);
               }
           }
       }
       dist[0][0][1][0] = 0;

       //PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(a[3],b[3]));
        Deque<int[]> pq = new ArrayDeque<>();
       pq.offerFirst(new int[]{0,0,0,0,1});
       int[] dx = {0,1,0,-1};
       int[] dy = {1,0,-1,0};
       while(!pq.isEmpty()){
           int[] curr = pq.pollFirst();
           int currx = curr[0];
           int curry = curr[1];
           int currDir = curr[2];
           int currTurns = curr[3];
           int currIdx = curr[4];
           int d = dist[currx][curry][currIdx][currDir];
//           if(dist[currx][curry][currIdx][currDir]<currTurns)
//               continue;
           if(currx == applesLoc[currIdx][0] && curry == applesLoc[currIdx][1]){
               if(currIdx==numApples){
                   res.append(d).append('\n');
                   return;
               }
               if(dist[currx][curry][currIdx+1][currDir]>d) {
                   dist[currx][curry][currIdx+1][currDir]=d;
                   pq.offerFirst(new int[]{currx, curry, currDir, currTurns, currIdx + 1});
               }
               continue;
           }

           int nx = currx + dx[currDir];
           int ny = curry + dy[currDir];
           if(nx>=0 && ny>=0 && nx<n && ny<n && grid[nx][ny]!=-1){
               if(dist[nx][ny][currIdx][currDir]>d){
                   dist[nx][ny][currIdx][currDir]=d;
                   pq.offerFirst(new int[]{nx,ny,currDir,currTurns,currIdx});
               }
           }

           int nd = (currDir+1)%4;
           if(dist[currx][curry][currIdx][nd]>d+1){
               dist[currx][curry][currIdx][nd]=d+1;
               pq.offerLast(new int[]{currx,curry,nd,currTurns+1,currIdx});
           }
       }
       res.append(-1).append('\n');
    }
}
