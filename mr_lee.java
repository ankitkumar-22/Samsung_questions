//Travelling Salesman problem
//link : https://www.coursehero.com/file/217402795/mr-leecpp/


public class MrLeeSolver {
    private int N;
    private int[][] cost;
    private boolean[] visited;
    private int best;
    private static final int INF = Integer.MAX_VALUE / 4;

    /**
     * Solve the TSP-like problem where cost[i][j] == 0 means no direct flight.
     * Returns minimum round-trip cost starting/ending at city 0, or -1 if impossible.
     */
    public int solve(int[][] costMatrix) {
        this.cost = costMatrix;
        this.N = costMatrix.length;
        this.visited = new boolean[N];
        this.best = INF;

        if (N == 0) return -1;
        visited[0] = true;
        dfs(0, 0, 0);
        visited[0] = false;

        return best == INF ? -1 : best;
    }

    // depth-first search/backtracking with simple pruning
    private void dfs(int src, int count, int cur) {
        if (cur >= best) return;           // branch-and-bound pruning

        if (count == N - 1) {              // visited all other cities
            if (cost[src][0] != 0) {       // can return to start?
                best = Math.min(best, cur + cost[src][0]); //keep track of the lowest cost
            }
            return;
        }

        for (int next = 0; next < N; next++) {
            if (!visited[next] && cost[src][next] != 0) {
                visited[next] = true;
                dfs(next, count + 1, cur + cost[src][next]);
                visited[next] = false; //backtracking
            }
        }
    }
}
