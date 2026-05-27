/*
There is an island surrounded by oil mines. You will be given n companies and m oil mines having values.
You have to distribute the mines to "n" companies in fair manner. Remember the companies can have oil 
mines adjacent to each other and not in between of each others.After distributing them compute the 
difference of oil mines from the company getting highest and company getting lowest. This number 
should be minimum.(then only the distribution can be termed as fair).

Input 
2 
2 4 
6 13 10 2 
2 4 
6 10 13 2 

output 
5 (10+2+6,13) circular continuation is allowed
1 (10+6,13+2)
**/

import java.util.*;

public class Main {
    static int companies, mines, ANS;
    static int[] oilMines;
    static boolean[] visited;

    // minV, maxV → min and max company sums seen so far
    // sum        → current company's running sum
    // nodes      → number of company boundaries placed so far
    static void solve(int i, int minV, int maxV, int sum, int nodes) {

        // Base case: came full circle back to a visited mine
        if (visited[i]) {
            int newMin = Math.min(sum, minV);
            int newMax = Math.max(sum, maxV);

            // All companies filled (nodes boundaries = companies-1 splits)
            if (nodes == companies - 1)
                ANS = Math.min(ANS, newMax - newMin);
            return;
        }

        visited[i] = true;
        int j = (i + 1) % mines;

        // Option 1: Add mine i to CURRENT company
        solve(j, minV, maxV, sum + oilMines[i], nodes);

        // Option 2: Close current company, START NEW company at mine i
        int newMin = Math.min(sum, minV);
        int newMax = Math.max(sum, maxV);
        solve(j, newMin, newMax, oilMines[i], nodes + 1);

        // Backtrack
        visited[i] = false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            companies = sc.nextInt();
            mines     = sc.nextInt();

            oilMines = new int[mines];
            visited  = new boolean[mines];

            for (int i = 0; i < mines; i++)
                oilMines[i] = sc.nextInt();

            ANS = Integer.MAX_VALUE;

            // Try every mine as starting point → handles circularity
            for (int i = 0; i < mines; i++) {
                Arrays.fill(visited, false);
                solve(i, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0);
            }

            System.out.println(ANS);
        }
    }
}
