//link : https://www.geeksforgeeks.org/problems/detect-cycle-in-a-directed-graph/1

class Solution {
    public boolean isCyclic(int V, int[][] edges) {
        // code here
        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        
        for(int[] e: edges){
            adj.get(e[0]).add(e[1]);
        }
        boolean[] globalVis = new boolean[V];
        
        for(int i = 0;i<V;i++){
            boolean[] pathVis = new boolean[V];
            if(globalVis[i]==false && dfs(adj,i,globalVis,pathVis))
                return true;
        }
        return false;
    }
    public boolean dfs(List<List<Integer>> adj, int node, boolean[] globalVis, boolean[] pathVis){
        globalVis[node]=true;
        pathVis[node]=true;
        
        for(int neigh : adj.get(node)){
            if(!globalVis[neigh]){
                if(dfs(adj,neigh,globalVis,pathVis))
                    return true;
            }
            else if(pathVis[neigh])//the path is vis globally and locally
                return true;//if the path was visited globally but not locally we know that it is not a part of the cycle
        }
        pathVis[node] = false;
        return false;
    }
}
