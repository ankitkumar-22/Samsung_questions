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
