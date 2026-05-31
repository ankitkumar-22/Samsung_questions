/*
Exactly similar question to rotting oranges
https://leetcode.com/problems/rotting-oranges/
  */
class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<int[]> queue = new ArrayDeque<>();
        int m = grid.length;
        int n = grid[0].length;
        int fresh = 0;
        for(int i = 0;i<m;i++){
            for(int j = 0 ; j<n; j++){
                if(grid[i][j]==2){
                    queue.offer(new int[]{i,j});
                }else if(grid[i][j]==1)
                    fresh++;
            }
        }
        if(fresh==0)
            return 0;
        int[] dir = {1,0,-1,0,1};
        int time = -1;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int s = 0 ; s<size;s++){
                int[] curr = queue.poll();
                for(int d = 0; d<4;d++){
                    int nx = curr[0]+dir[d];
                    int ny = curr[1]+dir[d+1];
                    if(nx>=0 && ny>=0 && nx<m && ny<n && grid[nx][ny]==1){
                        grid[nx][ny] = 2;
                        fresh--;
                        queue.offer(new int[]{nx,ny});
                    }
                }
            }
            time++;
        }
        return (fresh!=0)?-1:time;
    }
}
