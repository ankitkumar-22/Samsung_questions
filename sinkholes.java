/*
	Q.There is a large plot with various sinkholes present.
	Since one sinkhole can combine with another sinkhole, it is required to get
	at most one sinkhole while occupying the plot. You have to find the maximum
	square-area formed with at most one sinkhole present.
	If there are two plots with the same area then print the one with
	lesser sinkhole present otherwise if the sinkholes are also same then print
	anyone. For each case, you have to print the bottom leftmost coordinate and
	top rightmost point. Please keep in mind that the plot starts with (1, 1).

	Time limit= 1sec and Memory limit? 256Mb

	Input: First line will give the number of test cases. For each test case, we
	will be given the size of the plot matrix N x M (where 1<=N, M<=1000). Next
	line will give the number of sinkholes present in the matrix K (1<=K<=N+M).
	Next, K-lines will give the coordinates of the sinkholes.

	Output: For each test case, you have to print the number of the test case
	and the coordinates of the resultant square.
	i.e. #i xb yb xt yt (ith test case, xb=bottomost left x-coordinate,
	yb=bottomost  left y-coordinate, xt= topmost right x-coordinate,
	yt= topmost right y-coordinate)

  *	time complexity of my approach = O(n*m*log(min(m,n))) *

  input :
3
4 4
1
2 2
5 5
3
2 2
2 4
4 4
3 3
4
1 1
1 3
3 1
3 3

output:
#1 4 1 1 4
#2 5 1 3 3
#3 2 1 1 2
*/


import java.util.*;
class Pair{
    int min;
    int energy;
    Pair(int hr, int min, int energy){
        this.min = 60*hr + min;
        this.energy = energy;
    }
}
class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        int[][] res = new int[tc][4];
        for(int t = 0;t<tc;t++){
            int m = sc.nextInt();
            int n = sc.nextInt();
            int[][] pre = new int[m+1][n+1];
            int[][] grid = new int[m][n];
            int numSink = sc.nextInt();
            for(int i = 0;i<numSink;i++){
                int x = sc.nextInt();
                int y = sc.nextInt();
                grid[x-1][y-1] = 1;
            }
            construct(pre,grid,m,n);
            int left = 1;
            int right = Math.min(m,n);
            int[] ans = new int[4];
            while(left<=right){
                int mid = (left+right)/2;
                int[] currRes = func(pre,mid,m,n);
                if(currRes[0]!=-1){
                    //try a bigger grid
                    left = mid+1;
                    ans = Arrays.copyOf(currRes,currRes.length);
                }else{
                    right = mid-1;
                }
            }
            res[t] = Arrays.copyOf(ans,4);
        }
        for(int i = 1; i<=tc;i++){
            System.out.print("#"+i+" "+res[i-1][0]+" "+res[i-1][1]+" "+res[i-1][2]+" "+res[i-1][3]);
            System.out.println();
        }
    }
    public static void construct(int[][] pre, int[][] grid, int m , int n){
        for(int i = 1;i<=m;i++){
            for(int j = 1; j<=n;j++){
                pre[i][j] = grid[i-1][j-1] + pre[i-1][j] + pre[i][j-1] - pre[i-1][j-1];
            }
        }
    }
    public static int[] func(int[][] pre,int size, int m, int n){
       int x = 1;
       int minSinks = 2;
       int[] ret =  {-1,-1,-1,-1};
       while(x+size-1<=m){
           int y = 1;
           while(y+size-1<=n){
               int sx = x;
               int sy = y;
               int ex = x+size-1;
               int ey = y+size-1;
               int sinks = pre[ex][ey]-pre[sx-1][ey]-pre[ex][sy-1]+pre[sx-1][sy-1];

               if(sinks<minSinks){
                   minSinks = sinks;
                   ret = new int[]{ex, sy, sx, ey};
               }
               y++;
           }
           x++;
       }
       return ret;
    }
}
