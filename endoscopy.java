//link https://www.hackerearth.com/problem/algorithm/question-7-4/


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import java.io.*;
import java.util.*;

public class TestClass {
    
    static final int[] dr = {-1, 0, 1, 0}; // up, right, down, left
    static final int[] dc = {0, 1, 0, -1};

    // pipeOpen[type][dir] == true if pipe "type" has opening in direction dir
    static final boolean[][] pipeOpen = new boolean[8][4];
    static {
        // fill according to description (index 0 unused)
        // type 1: up,right,down,left
        pipeOpen[1][0] = pipeOpen[1][1] = pipeOpen[1][2] = pipeOpen[1][3] = true;
        // type 2: up, down
        pipeOpen[2][0] = pipeOpen[2][2] = true;
        // type 3: left, right
        pipeOpen[3][1] = pipeOpen[3][3] = true;
        // type 4: up, right
        pipeOpen[4][0] = pipeOpen[4][1] = true;
        // type 5: right, down
        pipeOpen[5][1] = pipeOpen[5][2] = true;
        // type 6: left, down
        pipeOpen[6][2] = pipeOpen[6][3] = true;
        // type 7: up, left
        pipeOpen[7][0] = pipeOpen[7][3] = true;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();
        while (T-- > 0) {
            int N = fs.nextInt();
            int M = fs.nextInt();
            int R = fs.nextInt(); // vertical location (row)
            int C = fs.nextInt(); // horizontal location (col)
            int L = fs.nextInt(); // length / time limit

            int[][] grid = new int[N][M];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    grid[i][j] = fs.nextInt();
                }
            }

            // If start cell has no pipe or L <= 0 -> 0 reachable
            if (L <= 0 || grid[R][C] == 0) {
                out.append(0).append('\n');
                continue;
            }

            boolean[][] visited = new boolean[N][M];
            ArrayDeque<int[]> q = new ArrayDeque<>(); // elements: {r, c, time}
            visited[R][C] = true;
            q.add(new int[]{R, C, 1});
            int count = 1; // start cell counted at time 1

            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int r = cur[0], c = cur[1], t = cur[2];

                if (t >= L) continue; // cannot move further from here

                int type = grid[r][c];
                // try all 4 directions, but only those allowed by current pipe
                for (int d = 0; d < 4; d++) {
                    if (!pipeOpen[type][d]) continue;
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                    if (visited[nr][nc]) continue;
                    int ntype = grid[nr][nc];
                    if (ntype == 0) continue;
                    int opposite = (d + 2) % 4;
                    if (!pipeOpen[ntype][opposite]) continue;

                    visited[nr][nc] = true;
                    count++;
                    q.add(new int[]{nr, nc, t + 1});
                }
            }

            out.append(count).append('\n');
        }

        System.out.print(out.toString());
    }

    // Fast scanner (Buffered)
    static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        FastScanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}


/*
Upper solution is more optimized
another readable solution down
*/
import java.util.*;
class Solution{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        HashMap<Integer,List<int[]>> move = new HashMap<>();
        int[] up = {-1,0};
        int[] right = {0,1};
        int[] down = {1,0};
        int[] left = {0,-1};
        
        move.put(1,new ArrayList<>());
        List<int[]> curr = move.get(1);      
        curr.add(up);
        curr.add(right);
        curr.add(down);
        curr.add(left);
        
        move.put(2,new ArrayList<>());
        curr = move.get(2);
        curr.add(up);
        curr.add(down);
        
        move.put(3,new ArrayList<>());
        curr = move.get(3);
        curr.add(left);
        curr.add(right);
        
        move.put(4,new ArrayList<>());
        curr = move.get(4);
        curr.add(up);
        curr.add(right);
                 
        move.put(5,new ArrayList<>());
        curr = move.get(5);
        curr.add(right);
        curr.add(down);
                 
        move.put(6,new ArrayList<>());
        curr = move.get(6);
         curr.add(left);
         curr.add(down);

         move.put(7, new ArrayList<>());
         curr = move.get(7);
         curr.add(left);
                 curr.add(up);
                 
                 
                 
                 
                 
        int[] res = new int[tc];
        int resIdx = 0;
        while(tc-->0){
            int rows = sc.nextInt();
            int cols = sc.nextInt();
            int[][] grid = new int[rows][cols];
            int startx = sc.nextInt();
            int starty = sc.nextInt();
            int maxDepth = sc.nextInt();
            for(int i = 0; i<rows;i++){
                for(int j = 0;j<cols; j++){
                    grid[i][j] = sc.nextInt();
                }
            }
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{startx,starty});
            int layer = 1;
            int count = 0;
            boolean[][] visited = new boolean[rows][cols];
            visited[startx][starty] = true;
            while(!queue.isEmpty() && layer<=maxDepth){
                int size = queue.size();
                count+=size;
                //System.out.println(size);
                for(int i = 0; i<size;i++){
                    int[] current = queue.poll();
                    for(int[] dir : move.get(grid[current[0]][current[1]])){
                        int nx = current[0]+dir[0];
                        int ny = current[1]+dir[1];
                        if(isValid(nx,ny,rows,cols) && grid[nx][ny]!=0 && !visited[nx][ny]){
                            int[] revDir = {-dir[0],-dir[1]};
                            boolean reciprocal = false;
                            for(int[] rev : move.get(grid[nx][ny])){
                                if(rev[0]==revDir[0] && rev[1]==revDir[1])
                                    reciprocal = true;
                            }
                            if(reciprocal){
                                visited[nx][ny] = true;
                                queue.offer(new int[]{nx,ny});
                            }
                        }
                    }
                }
                layer++;
            }
            res[resIdx++] = count;
        }
                 for(int i = 0;i<resIdx;i++)
                    System.out.println(res[i]);
    }
    public static boolean isValid(int i, int j,int rows,int cols){
        return (i>=0 && j>=0 && i<rows && j<cols);
    }
}
