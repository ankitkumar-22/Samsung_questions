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

//chatGPT code
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


//my code 

import java.util.*;
class Solution{
    static int[] mines;
    static int minesCount;
    static long finalVis;
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        for(int i = 0;i<tc;i++){
            int companies = sc.nextInt();
            minesCount = sc.nextInt();
            mines = new int[minesCount];
            for(int j = 0;j<minesCount;j++)
                mines[j] = sc.nextInt();
            //this.mines = mines;
            int minDiff = Integer.MAX_VALUE;
            finalVis = (1<<minesCount)-1;
            for(int j = 0;j<minesCount;j++){
                int[] diff = {Integer.MAX_VALUE};
                solve(j,companies,diff,0,Integer.MIN_VALUE,Integer.MAX_VALUE,0L);
                minDiff = Math.min(minDiff,diff[0]);
            }
            System.out.println(minDiff);
        }
    }
    public static void solve(int index, int companies,
                            int[] diff,int currSum, int maxVal, int minVal,long vis){
        if((vis & (1l<<index))!=0){
            if(vis==finalVis && companies==1){
                maxVal = Math.max(maxVal,currSum);
                minVal = Math.min(minVal,currSum);
                diff[0] = Math.min(diff[0],(maxVal-minVal));
                //System.out.println(maxVal+" "+minVal);
            }
            return;
        }
        if(companies==0)
            return;
        /*
        At the current index we have 2 choices
        Either include it in the previous ongoing company 
        or give the oil mine at the curr index to the new company
        **/
        vis = vis | (1L<<index);
        int nextIndex = (index+1)%mines.length;
        solve(nextIndex,companies,diff,currSum+mines[index],maxVal,minVal,vis);
        maxVal = Math.max(maxVal,currSum);
        minVal = Math.min(minVal,currSum);
        solve(nextIndex,companies-1,diff,mines[index],maxVal,minVal,vis);
        vis = vis & ~(1L<<index);
    }
}

/*
Why backtracking is needed even if we have 2 branches from the same level
The Recursion Tree
Inside solve, at every single mine i, the code makes a choice:

Option 1: Add the mine to the current company.

Option 2: Place a boundary and start a new company.

This creates a massive "tree" of possibilities. Let's imagine you have 4 mines (0, 1, 2, 3) and you start your loop in main at Mine 0.

You visit Mine 0. visited[0] = true.

You visit Mine 1. visited[1] = true.

You visit Mine 2. visited[2] = true.

At Mine 2, the algorithm explores Option 1. It calls solve(3).

You visit Mine 3. visited[3] = true.

From Mine 3, you move to j = 0 and call solve(0).

Base Case Hit: visited[0] is true. We've made a full circle. The algorithm records the answer and returns back to Mine 3.

What happens if we DO NOT backtrack?
If you remove visited[i] = false, here is how the code fails immediately after Step 7:

Mine 3 finishes Option 1, does Option 2, and returns back to Mine 2.

Now, Mine 2 is ready to try Option 2 (starting a new company at Mine 2).

Mine 2 calls solve(3) again for this new alternate reality.

The Bug: solve(3) runs, checks if (visited[3]), and sees that it is true! Why? Because the previous branch (Option 1) left it as true.

The code instantly hits the base case prematurely. It thinks it has completed a full circle, even though it hasn't processed Mine 3 for this specific branch. It stops exploring, and you get the wrong answer.

What happens WITH backtracking?
When you include visited[i] = false:

Mine 3 hits the base case, returns, and hits visited[3] = false.

It returns to Mine 2.

Mine 2 tries Option 2 and calls solve(3).

Because Mine 3 cleaned up its state, visited[3] is false.

Mine 3 can now be successfully processed under this new branch, continuing the circle perfectly.

**/
