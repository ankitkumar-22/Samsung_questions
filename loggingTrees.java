/*
The Korea Expressway Corporation is planning to expand the road where the volume of traffic is high.
In order to expand the road, all of the roadside trees planted nearby the road have to be chopped down.
To do the course of action, they should rent a logging robot which is developed by Samsung Electronics.
However, the rental fee of the logging robot is obviously high due to its high production cost.
Thus, the development of an operation software which allows logging trees within the shortest time is needed.

The logging robot has following features:

1.The logging robot can move forward and backward. And to get moved by 1, it takes 1 minute.

2.The roadside trees to the left or right can be cut and loaded which requires 1 minute.

3.Trees can be loaded in the order of their cut. Also only a tree which length is shorter or equal in length to the previous one can be loaded. (refer to Fig.1)

4.If there are trees on both sides such as to the left and right, without moving each can be cut and loaded by following the third requirement (above).

5.The logging robot can load infinite number of trees.

The information of the road to expand is as follows:

The road is given as a straight line with a length of N. The first point of the road is the starting point (point 0) and the last point (where N is located) is the ending point.

1.On each side of the road there will be one tree at most after every 1 unit length with varying lengths (it is possible that there is no tree)

2.On both sides to the starting and ending point there are no trees planted.

3.The logging robot starts moving from the starting point, chops down all trees along the way, transport them and it reaches the ending point.

Suppose a road with N=5 as demonstrated in [Fig.2] is given.

The beginning part of the road 0 is the starting point of the robot and 5 the ending point.
The logging robot moves forward, backward, chops down all trees on the left, right side of the road, transports them and must reach point 5.

The logging robot can move as follows:

=>
Point0 -> Point3 (chops down tree with a length of 3)
-> Point4 (chops down tree with a length of 2)
-> Point2 (chops down tree with a length of 2)
-> Point4 (chops down tree with a length of 1)
-> Point5 (moves to the ending point)
The total time is 13 minutes. (time needed to chop down: 4 minutes, time needed to move: 9 minutes)
However, if the robot follows the moves as given below, the total time becomes 11 minutes and the rental fee can be reduced.

=>
Point0 -> Point3 (chops tree with a length of 3)
-> Point2 (chops down tree with a length of 2)
-> (Standing still) (chops down tree with a length of 1)
-> Point4 (chops down tree with a length of 2)
-> Point5 (moves to the ending point)
(time needed to chop down: 4 minutes, time needed to move: 7 minutes)

Given the information of the road, write a program that finds the minimum time (in minute unit) that is needed in order to chop down all trees along the way from the starting to the end point.

The correct answer in [Fig.2] is 11.

Constraints:

The length of the road N is given as an integer that is greater than or equal to 5 and less than or equal to 1,000. (5 ? N ? 1,000)

On each side of the road there is one tree at most after every 1 unit length (it is possible that there is no tree)

The length of a tree L is given as an integer that is greater than or equal to 1 and less than or equal to 1,000. (1 ? L ? 1,000)

The starting point of the logging robot is given as point 0 on the map and the ending point as N.

There are no trees on both sides of the starting and ending point.

There is no situation where there are no trees at all on the road.
*/

import java.util.*;
import java.io.*;
public class Solution{
    static int[] left;
    static int[] right;
    static List<Integer> treeLength;
    static HashMap<Integer,List<Integer>> map ;//tree length -> indexes
    static int[] cost;
    static int[][] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        int n = Integer.parseInt(br.readLine().trim());
        left = new int[n+1];
        right = new int[n+1];
        treeLength = new ArrayList<>();
        map = new HashMap<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 1;i<=n;i++){
            left[i] = Integer.parseInt(st.nextToken());
            if(left[i]==0)
                continue;
            if(!map.containsKey(left[i])) {
                map.put(left[i], new ArrayList<>());
                treeLength.add(left[i]);
            }
            map.get(left[i]).add(i);
        }

        st = new StringTokenizer(br.readLine());
        for(int i = 1;i<=n;i++){
            right[i] = Integer.parseInt(st.nextToken());
            if(right[i]==0)
                continue;
            if(!map.containsKey(right[i])){
                map.put(right[i],new ArrayList<>());
                treeLength.add(right[i]);
            }
            map.get(right[i]).add(i);
        }
        Collections.sort(treeLength,(a,b)->(b-a));
        cost = new int[treeLength.size()];
        for(int i = 0 ;i<treeLength.size();i++) {
            int len = treeLength.get(i);
            Collections.sort(map.get(len));
            cost[i] = map.get(len).getLast() - map.get(len).getFirst();//traversing all indexes
            cost[i] += map.get(len).size();//time to cut all trees( 1min per tree)
        }
        dp = new int[n+1][treeLength.size()];
        //dp[i][j] = min time needed to cut all the trees smaller than treeLength[i] and currently standing at index j
        for(int[] row : dp)
            Arrays.fill(row,-1);
        int ans = dfs(0,0);
        System.out.println(ans);
    }
    public static int dfs(int currIdx, int treeIdx){
        if(treeIdx==treeLength.size())
            return n-currIdx;
        if(dp[currIdx][treeIdx]!=-1)
            return dp[currIdx][treeIdx];
        int currTreeLen = treeLength.get(treeIdx);
        List<Integer> currTreeLoc = map.get(currTreeLen);
        int ans = Integer.MAX_VALUE;
        // choice 1 cut the tree from left to right
        ans = Math.min(ans,
                Math.abs(currTreeLoc.getFirst()-currIdx) + cost[treeIdx] +
                        dfs(currTreeLoc.getLast(),treeIdx+1));
        //choice 2: cut the tree from right to left
        ans = Math.min(ans ,
                Math.abs(currTreeLoc.getLast()-currIdx)+cost[treeIdx]+
                        dfs(currTreeLoc.getFirst(),treeIdx+1));
        return dp[currIdx][treeIdx] = ans;
    }
}
