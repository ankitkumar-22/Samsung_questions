//Travelling Salesman problem
//link : https://www.coursehero.com/file/217402795/mr-leecpp/
/*
Mr. Lee has to travel various offices abroad to assist branches of each place. But he has a problem. 
The airfare would be real high as all offices he has to visit are in foreign countries. He wants to visit every 
location only one time and return home with the lowest expense. Help this company-caring man calculate the lowest expense.


Input format
Several test cases can be included in the inputs. T, the number of cases is given in the first row of the inputs. 
After that, the test cases as many as T (T ≤ 30) are given in a row. N, the number of offices to visit is given on 
the first row per each test case. At this moment, No. 1 office is regarded as his company (Departure point). 
(1 ≤ N ≤ 12) Airfares are given to move cities in which branches are located from the second row to N number rows.
i.e. jth number of ith row is the airfare to move from ith city to jth city. If it is impossible to move between 
two cities, it is given as zero.

Output format
Output the minimum airfare used to depart from his company, visit all offices, and then return his company on the 
first row per each test case.

Example of Input

3
5
0 14 4 10 20
14 0 7 8 7
4 5 0 7 16
11 7 9 0 2
18 7 17 4 0
5
9 9 2 9 5
6 3 5 1 5
1 8 3 3 3
6 0 9 6 8
6 6 9 4 8
3
0 2 24
3 0 2
0 4 0

Example of Output

30
18
CUSTOM - 31 <- 4
**/

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
