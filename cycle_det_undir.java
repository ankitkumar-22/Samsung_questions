class Solution {
    public boolean isCycle(int V, int[][] edges) {
        
        //creation of adjacency matrix
        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0;i<V;i++)
            adj.add(new ArrayList<>());
        for(int[] e : edges){
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }
        
        boolean[] vis = new boolean[V];
        for(int i = 0;i<V;i++){
            if(vis[i]==false && dfs(adj,i,vis,-1))
                return true;
            
        }
        return false;
    }
    public boolean dfs(List<List<Integer>> adj , int node, boolean[] vis,int parent){
        vis[node] = true;
        for(int neigh : adj.get(node)){
            if(vis[neigh]==false){
                if(dfs(adj,neigh,vis,node))
                    return true;
            }
            else{//making sure that the vis neigh is not the parent
                if(neigh!=parent)
                    return true;//if the vis node is not the parent then we can say that the graph is undir.
            }
        }
        return false;
    }
}
