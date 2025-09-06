/*
In this the approach is simple, we are given 2 arrays one for the roads containing 0, 1 (0 : no_road, 1: road)
And another array for location of rare elements 
We create and array for the distance of the rare element to each cell that has a road.

1 1 1 
1 0 1 
1 1 1 

now we have 2 rare elements at - {(0,0),(2,2)}
Now for the rare element at 0,0 we can calc the distance of each road cell from that rare element
0,0 - 0
0,1 - 1
0,2 - 2
1,0 - 1
1,1 - INF
1,2 - 3
2,0 - 2
2,1 - 3
2,2 - 4

Now similarly we do that for the rare metal at 2,2 
0,0 - 4
0,1 - 3
0,2 - 2
1,0 - 3
1,1 - INF
1,2 - 1
2,0 - 2
2,1 - 1
2,2 - 0


now for each location we calculate max dist 

0,0 - 0 , 4 = 4
0,1 - 1 , 3 = 3
0,2 - 2 , 2 = 2 <- least
1,0 - 1 , 3 = 3
1,1 - INF , INF = INF 
1,2 - 3 , 1 = 3
2,0 - 2 , 2 = 2 <- least
2,1 - 3 , 1 = 3
2,2 - 4 , 0 = 4

Now from the max dist figure out the least ones 
Thus we have our answer

Thus to achieve this we create a 3 dimensional matrix 
dist[k][r][c] = distace of the cell (r,c) from the rare element k 
and for each combination of r and c we calculate the max distance from 
each rare metal and from that overall max dist list we figure out the min one and we 
can print the min dist from that.
*/


public class ResearchCenterSolver {
    private static final int INF = 1_000_000;

    /** 
     * Returns the minimum possible value of the maximum distance from the chosen road cell
     * to all rare-element locations. Returns -1 if no road cell can reach all rares.
     *
     * @param grid N x N grid (0/1)
     * @param rares array of positions {r, c} (0-based)
     */
    public int findBestCenter(int[][] grid, int[][] rares) {
        int N = grid.length;
        int C = rares.length;
        // dist[k][r][c] = distance from rare k to cell (r,c)
        int[][][] dist = new int[C][N][N];

        for (int k = 0; k < C; k++) {
            // initialize distances
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    dist[k][i][j] = INF;
                }
            }
            bfsFromRare(dist[k], grid, rares[k][0], rares[k][1]);
        }

        int best = INF;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (grid[r][c] != 1) continue; // research center must be built on a road
                int maxd = 0;
                boolean reachable = true;
                for (int k = 0; k < C; k++) {
                    int d = dist[k][r][c];
                    if (d == INF) { reachable = false; break; }
                    if (d > maxd) maxd = d;
                }
                if (reachable && maxd < best) best = maxd;
            }
        }

        return best == INF ? -1 : best;
    }

    // BFS from a single rare located at (sr, sc). Fills out[] with distances (in steps) or leaves INF if unreachable.
    private void bfsFromRare(int[][] out, int[][] grid, int sr, int sc) {
        int N = grid.length;
        if (!inBounds(sr, sc, N) || grid[sr][sc] == 0) {
            // if the rare is not on a road, we cannot start BFS (remain INF)
            return;
        }

        ArrayDeque<int[]> q = new ArrayDeque<>();
        out[sr][sc] = 0;
        q.addLast(new int[] { sr, sc });

        final int[] dr = {-1, +1, 0, 0};
        final int[] dc = {0, 0, -1, +1};

        while (!q.isEmpty()) {
            int[] p = q.removeFirst();
            int r = p[0], c = p[1];
            int d = out[r][c];
            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k], nc = c + dc[k];
                if (!inBounds(nr, nc, N)) continue;
                if (grid[nr][nc] != 1) continue; // can only traverse roads
                if (out[nr][nc] > d + 1) {
                    out[nr][nc] = d + 1;
                    q.addLast(new int[] { nr, nc });
                }
            }
        }
    }

    private boolean inBounds(int r, int c, int N) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
