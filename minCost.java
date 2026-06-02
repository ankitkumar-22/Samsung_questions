/*
https://codeforces.com/contest/1092/problem/F

8
9 4 1 7 10 1 6 5
1 2
2 3
1 4
1 5
5 6
5 7
5 8


output:
2?9+1?4+0?1+3?7+3?10+4?1+4?6+4?5=18+4+0+21+30+4+24+20=121
121
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class Solution{
    static long[] dist;
    static long[] ss;
    static long[] res;
    static List<List<Integer>> list;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        ss = new long[n + 1];
        dist = new long[n + 1];
        res = new long[n + 1];
        list = new ArrayList<>();
        for (int i = 0; i <= n; i++) list.add(new ArrayList<>());

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) ss[i] = Long.parseLong(st.nextToken());

        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            list.get(u).add(v);
            list.get(v).add(u);
        }
        dfs(1,-1);
        res[1] = dist[1];
        for(int child : list.get(1))
            update(child,1);

        long max = 0;
        for(int i = 1; i<=n;i++) {
            max = Math.max(max, res[i]);
            //System.out.print(res[i]+" ");
        }
        //System.out.println();
        System.out.println(max);
    }
    public static void dfs(int node, int parent){
        for(int child : list.get(node)){
            if(child!=parent){
                dfs(child,node);
                ss[node]+=ss[child];
                dist[node]+=dist[child]+ss[child];
            }
        }
    }
    public static void update(int node, int parent){
        long[] t = {dist[parent],dist[node],ss[parent],ss[node]};
        ss[parent]-=ss[node];
        dist[parent]-=(dist[node]+ss[node]);
        ss[node]+=ss[parent];
        dist[node]+=dist[parent]+ss[parent];
        res[node] = dist[node];
        for(int child : list.get(node)){
            if(child!=parent){
                update(child,node);
            }
        }
        dist[parent] = t[0];
        dist[node] = t[1];
        ss[parent] = t[2];
        ss[node] = t[3];
    }
}
