/*
https://www.geeksforgeeks.org/samsung-interview-experience-set-39-campus-r-d-noida/
https://www.careercup.com/page?pid=samsung-interview-questions

A Doctor travels from a division to other division where divisions are connected like a graph(directed graph) and
the edge weights are the probabilities of the doctor going from that division to other connected division but the
doctor stays 10mins at each division now there will be given time and had to find the division in which he will be
staying by that time and is determined by finding division which has high probability.

Input is number of test cases followed by the number of nodes, edges, time after which we need to find the division
in which he will be there, the edges starting point, end point, probability.

Note: If he reaches a point where there are no further nodes then he leaves the lab after 10 mins and the traveling
time is not considered and during that 10min at 10th min he will be in next division, so be careful

2
6 10 40
1 2 0.3 1 3 0.7 3 3 0.2 3 4 0.8 2 4 1 4 5 0.9 4 4 0.1 5 6 1.0 6 3 0.5 6 6 0.5
6 10 10
1 2 0.3 1 3 0.7 3 3 0.2 3 4 0.8 2 4 1 4 5 0.9 4 4 0.1 5 6 1.0 6 3 0.5 6 6 0.5

6 0.774000
3 0.700000
*/

import java.util.*;

class Pair{
    int node;
    double prob;
    Pair(int node, double prob){
        this.node = node;
        this.prob = prob;
    }
}


public class Solution{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        Pair[] res = new Pair[tc];
        for(int t= 0;t<tc;t++) {
            int nodes = sc.nextInt();
            int edges = sc.nextInt();
            int time = sc.nextInt();
            int steps = time/10;
            List<List<Pair>> adj = new ArrayList<>();
            for(int i = 0;i<=nodes;i++)
                adj.add(new ArrayList<>());
            for(int i = 0;i<edges;i++){
                int from = sc.nextInt();
                int to = sc.nextInt();
                double p = sc.nextDouble();
                adj.get(from).add(new Pair(to,p));
            }
            double[] dp = new double[nodes+1];
            dp[1] = 1.0;
            for(int step = 0 ;step<steps; step++){
                double[] nextDp = new double[nodes+1];
                for(int node = 1; node<=nodes;node++){
                    for(Pair next : adj.get(node)){
                        nextDp[next.node] += dp[node]*next.prob;
                    }
                }
                dp = nextDp;
            }
            double maxProb = 0;
            int finalNode = 0;
            for(int i = 1;i<=nodes;i++){
                if(dp[i]>maxProb){
                    maxProb = dp[i];
                    finalNode = i;
                }
            }
            res[t] = new Pair(finalNode,maxProb);
        }
        for(int i = 0;i<tc;i++){
            System.out.println(res[i].node + " " + res[i].prob);
        }
        sc.close();
    }
}
