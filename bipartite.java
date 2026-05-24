//link : https://leetcode.com/problems/is-graph-bipartite/

// Solution using DFS
class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        for(int i = 0;i<n;i++){
            if(color[i]==0){
                color[i] = 1;
                if(!dfs(graph,i,color))
                    return false;
            }
        }
        return true;
    }
    public boolean dfs(int[][] graph, int node, int[] color){
        int currColor = color[node];
        int nextColor = (currColor==1)?2:1;
        for(int nextNode: graph[node]){
            if(color[nextNode]==0){
                color[nextNode] = nextColor;
                if(!dfs(graph,nextNode,color))
                    return false;
            }else if(color[nextNode]==currColor){
                return false;
            }
        }
        return true;
    }
}


//Solution using BFS
class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] vis = new int[n];
        
        for(int i = 0;i<n;i++){
            if(vis[i]==0){
                vis[i] = 1;
                if(!bfs(graph,i,vis))
                    return false;
            }
        }
        return true;
    }
    public boolean bfs(int[][] graph, int node, int[] vis){
        vis[node] = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(node);
        while(!queue.isEmpty()){
            int curr = queue.poll();
            int currColor = vis[curr];
            int nextColor = (currColor==1)?2:1;
            for(int next : graph[curr]){
                if(vis[next]==0){
                    vis[next] = nextColor;
                    queue.offer(next);
                }else{
                    if(vis[next]==currColor)
                        return false;
                }
            }
        }
        return true;
    }
}
