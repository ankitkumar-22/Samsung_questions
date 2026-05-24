/*
Based on the text and the movement logic in the code, here is the exact question being asked in simple terms:

You are given a chessboard of size **N × M**. On this board, there is a moving piece and a stationary target piece.

The moving piece moves exactly like a **Knight in standard chess** (in an "L" shape: two squares in one direction, and one square perpendicular to it).

Given the starting coordinates of the Knight and the coordinates of the target piece, what is the **minimum number of moves** required for the Knight to land on the target?

If it is impossible for the Knight to ever reach the target, return **-1**.

[Input]
Several test cases can be included in the inputs. T, the number of cases is given in the first row of the
inputs. After that, the test cases as many as T (T ≤ 20) are given in a row. 
N, the numbers of the rows and M, the number of columns of the chessboard are given in the first row of
each test case. 
R & C is the location information of the attacking piece and S & K is the location of the defending pieces 
and are given in the row at the second line. However, the location of the uppermost end of the left end 
is (1, 1)

[Output]
For each test case, you should print "Case #T" in the first line where T means the case number.

For each test case, you should output the minimum number of movements to catch a defending piece at the 
first line of each test case. If not moveable, output equals ‘-1’. 

[I/O Example]

Input 
2
9 9
3 5 2 8
20 20
2 3 7 9

Output
Case #1
2
Case #2
5 


**/



import java.util.*;

class Solution {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        
        // Note: The problem might have multiple test cases (T). 
        // You may need to wrap this in a loop: while(T-- > 0)
        int m = sc.nextInt();
        int n = sc.nextInt();
        
        int si = sc.nextInt() - 1;
        int sj = sc.nextInt() - 1;
        int ei = sc.nextInt() - 1;
        int ej = sc.nextInt() - 1;
        
        // If the start and end are the same, 0 moves needed.
        if (si == ei && sj == ej) {
            System.out.println(0);
            return;
        }

        // boolean array is faster and uses less memory than an int array
        boolean[][] vis = new boolean[m][n];
        vis[si][sj] = true;
        
        int[] dx = {-2, -2, -1, 1, 2, 2, 1, -1};
        int[] dy = {-1, 1, 2, 2, 1, -1, -2, -2};
        
        // Standard Queue for standard BFS
      
        /*
          It might feel that using priorityQueue for A* like search would be a better option 
          like : PriorityQueue<int[]> queue = new PriorityQueue<>
            ((a,b)->((Math.abs(a[0]-ei)+Math.abs(a[1]-ej))-(Math.abs(b[0]-ei)+Math.abs(b[1]-ej))));

            But it is wrong and a simple queue is best because we want to minimize the number of hops 
            and a simple bfs is best at doing that and also another logical flaw that might cause an 
            issue is that we are trying to move a knight peice, a position that is one square away
            might take a lot of hops as compared to a location that is 3 squares away.
        **/
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{si, sj, 0});
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0];
            int y = curr[1];
            int hops = curr[2];
            
            for (int i = 0; i < 8; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                // If the next move is valid and hasn't been visited yet
                if (nx >= 0 && ny >= 0 && nx < m && ny < n && !vis[nx][ny]) {
                    
                    // EARLY EXIT: Stop immediately when we hit the target
                    if (nx == ei && ny == ej) {
                        System.out.println(hops + 1);
                        return; 
                    }
                    
                    vis[nx][ny] = true; // Mark visited immediately upon adding to queue
                    queue.offer(new int[]{nx, ny, hops + 1});
                }
            }                        
        }
        
        // If the queue empties and we never reached the target
        System.out.println(-1);
    }
}
