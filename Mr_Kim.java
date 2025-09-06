/*
Mr. Kim has to deliver refrigerators to N customers. From the office, he is going to visit all the customers and then return to his home. 
Each location of the office, his home, and the customers is given in the form of integer coordinates [x, y],

The distance between two arbitrary locations (x1, y1) and (x2, y2) is computed by |x1-x2| + |y1-y2|, where |x|
denotes the absolute value of x; for instance, |3|=|-3|=3. The locations of the office, his home, and the customers are all distinct.
You should plan an optimal way to visit all the N customers and return to his among all the possibilities.

You are given the locations of the office, Mr. Kim’s home, and the customers; the number of the customers 
is in the range of 5 to 10. Write a program that, starting at the office, finds a (the) shortest path visiting all the customers 
and returning to his home. Your program only has to report the distance of a (the) shortest path.

Input Format

You are given 10 test cases. Each test case consists of two lines; the first line has N, the number
of the customers, and the following line enumerates the locations of the office, Mr. Kim’s home, and the 
customers in sequence. Each location consists of the coordinates (x, y), which is represented by ‘x y’.

Input (20 lines in total. In the first test case, the locations of the office and the home are (0, 0) and (100, 100) respectively,
and the locations of the customers are (70, 40), (30, 10), (10, 5), (90, 70), (50, 20).) */




import java.util.*;

public class KimDeliveryTSP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Problem says: You are given 10 test cases.
        final int T = 10;

        for (int tc = 1; tc <= T; tc++) {
            // Read N (number of customers)
            int N = sc.nextInt();

            // We'll store points in indices:
            // 0 -> office (start)
            // 1..N -> customers
            // N+1 -> home (end)
            int totalPoints = N + 2;
            int[] xs = new int[totalPoints];
            int[] ys = new int[totalPoints];

            // Input order: office x y, home x y, then N customers (x y pairs)
            xs[0] = sc.nextInt(); ys[0] = sc.nextInt();         // office
            xs[N + 1] = sc.nextInt(); ys[N + 1] = sc.nextInt(); // home
            for (int i = 1; i <= N; i++) {
                xs[i] = sc.nextInt();
                ys[i] = sc.nextInt();
            }

            // Precompute Manhattan distances
            int[][] dist = new int[totalPoints][totalPoints];
            for (int i = 0; i < totalPoints; i++) {
                for (int j = 0; j < totalPoints; j++) {
                    dist[i][j] = Math.abs(xs[i] - xs[j]) + Math.abs(ys[i] - ys[j]);
                }
            }

            // DP bitmask: dp[mask][pos]
            int fullMask = (1 << N) - 1;
            int states = 1 << N;
            final int INF = Integer.MAX_VALUE / 4;
            int[][] dp = new int[states][N + 1]; // pos from 0..N (not including home index N+1)

            // Init with INF
            for (int m = 0; m < states; m++) Arrays.fill(dp[m], INF);

            // Base: if mask == fullMask, cost from pos to home
            for (int pos = 0; pos <= N; pos++) {
                dp[fullMask][pos] = dist[pos][N + 1];
            }

            // Fill DP for all masks from fullMask-1 down to 0
            for (int mask = fullMask - 1; mask >= 0; mask--) {
                for (int pos = 0; pos <= N; pos++) {
                    int best = INF;
                    for (int k = 1; k <= N; k++) {
                        int bit = 1 << (k - 1);
                        if ((mask & bit) == 0) { // customer k not visited
                            int nextMask = mask | bit;
                            int cand = dist[pos][k] + dp[nextMask][k];
                            if (cand < best) best = cand;
                        }
                    }
                    dp[mask][pos] = best;
                }
            }

            int answer = dp[0][0];
            System.out.println("# " + tc + " " + answer);
        }

        sc.close();
    }
}
