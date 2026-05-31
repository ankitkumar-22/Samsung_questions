/*A Research team want to establish a research center in a region where they found some rare-elements.
They want to make it closest to all the rare-elements as close as possible so that they can reduce
overall cost of research over there. It is given that all the rare-element?s location is connected
by roads. It is also given that Research Center can only be build on road. Team decided to assign
this task to a coder. If you feel you have that much potential.

Here is the Task :- Find the shortest of the longest distance of research center from given locations
of rare-elements.

Locations are given in the matrix cell form where 1 represents roads and 0 no road.
Number of rare-element and their location was also given(number<=5) and order of square matrix
was less than equal to (20).

For this you have to implement bfs for every position where road exist to find the distance of
every research center or do Vice-versa. for each position store maximum distance of all distances
to research center and the compare each maximum distance to find minimum of them

Input -
6
5 2
4 3
3 4
1 1 0 0 0
1 1 0 0 0
1 1 1 1 1
1 1 1 0 1
1 1 1 1 1
8 2
5 6
6 4
1 1 1 1 1 1 0 0
1 1 1 1 1 1 1 0
1 1 0 1 0 1 1 0
1 1 1 1 0 1 1 0
1 1 1 1 1 1 1 0
1 1 1 1 1 1 1 0
0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
10 3
8 2
5 3
7 1
0 0 0 1 1 1 1 1 1 0
1 1 1 1 1 1 1 1 1 0
1 0 0 1 0 0 0 0 1 0
1 1 1 1 1 1 1 1 1 1
1 1 1 1 0 1 0 0 1 1
1 1 1 1 0 1 0 0 1 1
1 1 1 1 0 1 0 0 1 1
1 1 1 1 1 1 1 1 1 1
1 1 1 0 0 1 0 0 1 1
1 1 1 1 1 1 1 1 1 1
16 4
11 15
15 9
1 2
14 3
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 0 1 1 1 1 1 1 1 1 1 1 1 0 1
1 0 1 0 0 0 1 0 0 0 0 1 1 0 1
1 0 1 0 0 0 1 0 0 0 0 1 1 0 1
1 0 1 1 1 1 1 1 1 1 1 1 1 1 1
1 0 1 0 0 0 1 0 0 0 0 1 1 0 1
1 0 1 0 0 0 1 1 1 1 1 1 1 1 1
1 0 1 0 0 0 1 0 0 0 0 1 1 0 1
1 0 1 0 0 0 1 0 0 0 0 1 1 0 1
1 0 1 0 0 0 1 0 0 0 0 1 1 0 1
1 0 1 0 0 0 1 0 0 0 0 1 1 0 1
1 0 1 0 0 0 1 0 0 0 0 1 1 0 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
0 0 1 0 0 0 1 1 1 1 1 1 1 0 1
0 0 1 1 1 1 1 1 1 1 1 1 1 1 1
20 4
13 6
20 4
1 2
17 16
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0
1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0
1 0 1 0 0 0 0 0 0 0 1 0 0 1 1 0 0 0 0 0
1 0 1 0 0 0 0 0 0 0 1 0 0 1 1 0 0 0 0 0
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 0 0
1 0 1 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 0 0
1 0 1 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 0 0
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 0 1 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 1
1 0 1 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 1
1 0 1 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 1
1 0 1 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 1
1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1
1 0 1 0 0 0 0 0 0 0 1 0 0 0 1 1 0 0 1 1
1 0 1 0 0 0 0 0 0 0 1 0 0 0 1 1 0 0 1 1
1 0 1 0 0 0 0 0 0 0 1 0 0 0 1 1 0 0 1 1
1 0 1 0 0 0 0 0 0 0 1 0 0 0 1 1 0 0 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0
5 2
2 1
3 5
1 0 1 1 1
1 1 1 0 1
0 1 1 0 1
0 1 0 1 1
1 1 1 0 1

Output -
1
2
2
12
15
4

last few testcases are wrong
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
        int tc = sc.nextInt();
        int[] res = new int[tc];
        for(int t = 0 ;t<tc;t++){
            //System.out.println("Done");
            int n = sc.nextInt();
            int nr = sc.nextInt();
            int[][] rare = new int[nr][2];
            for(int i = 0;i<nr;i++){
                rare[i][0] = sc.nextInt();
                rare[i][1] = sc.nextInt();
            }
            int[][] grid = new int[n][n];
            for(int i =0 ;i<n;i++){
                for(int j = 0 ;j<n ; j++){
                    grid[i][j] = sc.nextInt();
                }
            }
            int[][] dist = new int[n][n];
            int[][] count = new int[n][n];
            for(int i = 0;i<n;i++)
                Arrays.fill(dist[i],Integer.MIN_VALUE);
            for(int i = 0;i<nr;i++){
                boolean[][] vis = new boolean[n][n];
                dist[rare[i][0]][rare[i][1]] = Math.max(dist[rare[i][0]][rare[i][1]],0);
                bfs(grid,rare[i][0],rare[i][1],dist,vis,count);
            }
            int min = Integer.MAX_VALUE;
            for(int i = 0;i<n;i++){
                for(int j = 0;j<n;j++){
                    if(grid[i][j]==1 && dist[i][j]!=Integer.MIN_VALUE && count[i][j]==nr){
                        min = Math.min(min,dist[i][j]);
                    }
                }
            }
            res[t]=min;
        }
        for(int i = 0;i<tc;i++)
            System.out.println(res[i]);
    }
    public static void bfs(int[][] grid, int x , int y, int[][] dist, boolean[][] vis, int[][] count){
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{x,y});
        int currDist = 1;
        int[] dir = {1,0,-1,0,1};
        vis[x][y] = true;
        count[x][y]++;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int s = 0; s<size; s++){
                int[] curr = queue.poll();
                for(int d = 0 ; d<4;d++){
                    int nx = curr[0] + dir[d];
                    int ny = curr[1] + dir[d+1];
                    if(nx>=0 && ny>=0 && nx<grid.length && ny<grid.length &&
                    grid[nx][ny]==1 && !vis[nx][ny]){
                        dist[nx][ny] = Math.max(dist[nx][ny],currDist);
                        vis[nx][ny] = true;
                        count[nx][ny]++;
                        queue.offer(new int[]{nx,ny});
                    }
                }
            }
            currDist++;
        }
        return;
    }
}
