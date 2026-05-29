/*

Fisherman Problem (Samsung OA)
There are N fishing spots numbered 1 to N, and 3 gates, each with a given position and a number of fishermen waiting.
Rules:

Distance between consecutive fishing spots = 1m
Distance between a gate and its nearest fishing spot = 1m
Only 1 gate can be opened at a time
All fishermen from that gate must occupy spots before the next gate opens
Fishermen occupy the nearest unoccupied spot from the gate
Distance = gate ? nearest spot + nearest spot ? next vacant spot (chaining)
If the last fisherman of a gate has 2 equidistant spots, assign the one that gives minimum total walking distance; for all others, either spot is fine

Goal: Find the minimum total walking distance for all fishermen across all gates.
Input:

Number of fishing spots (N)
Position of each of the 3 gates
Number of fishermen at each gate

Sample Input:

N = 10
Gate 1 at position 4, 5 fishermen
Gate 2 at position 6, 2 fishermen
Gate 3 at position 10, 2 fishermen

Answer for G1?G2?G3 order: 18
 */
import java.util.Arrays;

public class Fisherman {

    public static int solve(int N, int[][] gates) {
        int[] perm = {0, 1, 2}; // Represents the 3 gates
        boolean[] avail = new boolean[N + 1];
        Arrays.fill(avail, true);
        avail[0] = false; // 1-indexed fishing spots, 0 is invalid

        // Start recursive permutation generator
        return permute(perm, 0, N, gates, avail);
    }

    // Generates all 6 orders of opening the gates
    private static int permute(int[] perm, int i, int N, int[][] gates, boolean[] avail) {
        if (i == perm.length) {
            return fill(N, gates, perm, avail, 0, 0);
        }

        int minAns = Integer.MAX_VALUE;
        for (int j = i; j < perm.length; j++) {
            swap(perm, i, j);
            minAns = Math.min(minAns, permute(perm, i + 1, N, gates, avail));
            swap(perm, i, j); // Backtrack
        }
        return minAns;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Assigns fishermen to spots based on the current gate order
    private static int fill(int N, int[][] gates, int[] perm, boolean[] avail, int idx, int total) {
        if (idx == 3) {
            return total; // All 3 gates processed
        }

        int gateIdx = perm[idx];
        int G = gates[gateIdx][0]; // Gate position
        int K = gates[gateIdx][1]; // Number of fishermen

        int l = G;
        int r = G + 1;
        int dist = 0;

        // Clone the boolean array (equivalent to av[:] in Python)
        boolean[] av = avail.clone();

        for (int i = 0; i < K; i++) {
            // Find nearest unoccupied spots on left and right
            while (l >= 1 && !av[l]) l--;
            while (r <= N && !av[r]) r++;

            // Calculate distances (+1 for the step from gate to spot)
            int dl = (l >= 1) ? Math.abs(G - l) + 1 : Integer.MAX_VALUE;
            int dr = (r <= N) ? Math.abs(G - r) + 1 : Integer.MAX_VALUE;

            if (dl < dr) {
                av[l] = false;
                dist += dl;
                l--;
            } else if (dr < dl) {
                av[r] = false;
                dist += dr;
                r++;
            } else if (i == K - 1) {
                // TIE ON THE LAST FISHERMAN: Branch into both possibilities
                boolean[] av1 = av.clone();
                av1[l] = false;

                boolean[] av2 = av.clone();
                av2[r] = false;

                // Recurse for both branches and return the minimum
                int ans1 = fill(N, gates, perm, av1, idx + 1, total + dist + dl);
                int ans2 = fill(N, gates, perm, av2, idx + 1, total + dist + dr);

                return Math.min(ans1, ans2);

            } else {
                // TIE ON NON-LAST: Pick either (deterministically picking left here)
                av[l] = false;
                dist += dl;
                l--;
            }
        }

        // Move to the next gate
        return fill(N, gates, perm, av, idx + 1, total + dist);
    }

    public static void main(String[] args) {
        int N = 10;
        int[][] gates = {
                {4, 5},
                {6, 2},
                {10, 2}
        };

        // Output should now correctly be 18
        System.out.println("Minimum walking distance: " + solve(N, gates));
    }
}
