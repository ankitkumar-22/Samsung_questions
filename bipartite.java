//link : https://leetcode.com/problems/is-graph-bipartite/


class Solution {
    public boolean isBipartite(int[][] graph) {
        int[] color = new int[graph.length];
        for(int i =0 ;i<graph.length;i++)
        {
            if(color[i]== 0) 
                if(!dfs(graph,i,color,1)) return false; 
        }
        return true;                   
    }
    public boolean dfs(int[][] graph,int node , int[] color, int current_color){
        color[node] = current_color;
        int next_color = current_color==1?2:1;
        for(int neighbour : graph[node]){
            if(color[neighbour] == 0)
                if(!dfs(graph,neighbour,color,next_color)) return false;
            if(color[neighbour]== current_color) return false;
        }
        return true;
    }
}
