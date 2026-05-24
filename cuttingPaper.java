/*

You want to cut a piece of paper by a certain fixed rule to make some pieces of white or 
blue colored square paper with various sizes.  
 
If the size of the entire paper is N×N (N = 2^K; 1 <= K <= 7; K = natural number), the cutting rules
are as below.
 
‘If the entire piece of paper is not colored the same, cut the middle part horizontally and vertically 
to divide it into the same sized four pieces of paper, 
(N/2)×(N/2), as with I, II, III, IV in < FIG. 2 >. 

For each I, II, III and IV, cut and divide again in the same way if one entire piece of paper 
is not colored the same, and make them into the same sized four pieces of paper. Continue until each and 
every piece of paper has only one color of white or blue.’
 
When you finish, < FIG. 3 > shows the first division of < FIG. 1 > and < FIG. 4 > 
shows the final version of 9 pieces of white paper and 7 pieces of blue paper of various sizes.
 
If the length of an edge of the first given piece of paper, N, and 
the color information (white or blue) inside each square are given, create a calculation program
 that assesses how many white/blue pieces of paper are.
 
Time limit: 1 second (java: 2 seconds) 
 
[Input]
 
Input may include many test cases. The number of test cases, T, is given on the first line of input and then the amount of T of test cases is given in a line. (T <= 30)
The length of an edge of the first given piece of paper, N, is given for the first line of each test case.
From the next line through to the amount of N lines, the color information is given separately as blanks. 0 indicates white and 1 indicates blue.
 
[Output]
 
For each test case, you should print "Case #T" in the first line where T means the case number. 
 
For each test case, you should output the number of white pieces of paper and blue pieces of paper separately as blanks on the first line of each test case.
 
[I/O Example]
Input 
2
8
1 1 0 0 0 0 1 1
1 1 0 0 0 0 1 1
0 0 0 0 1 1 0 0
0 0 0 0 1 1 0 0
1 0 0 0 1 1 1 1
0 1 0 0 1 1 1 1 
0 0 1 1 1 1 1 1
0 0 1 1 1 1 1 1
 
 
16
1 0 0 1 0 0 0 0 0 0 1 1 0 1 1 1
1 1 0 1 0 1 1 0 0 0 0 0 0 0 0 0
0 0 0 0 1 0 1 1 1 1 0 0 1 0 0 1
1 1 0 0 1 0 0 1 0 0 1 0 1 1 1 0
0 1 1 1 0 0 1 1 0 0 1 0 0 1 1 1
1 0 1 1 0 0 0 1 0 1 0 1 0 0 1 1
1 1 1 1 1 1 0 0 1 1 1 1 1 0 0 0
1 1 0 1 0 1 0 0 1 0 1 1 1 0 0 1
1 1 1 1 1 1 0 0 1 0 1 1 0 1 1 0
1 0 0 1 1 1 0 0 0 0 1 1 1 1 0 0
1 0 0 1 1 1 1 0 0 0 1 1 0 1 0 1
1 1 1 0 1 1 0 0 1 1 1 1 1 1 0 1
1 1 1 1 1 1 0 0 0 0 1 1 1 1 0 0
1 1 1 1 1 1 0 1 1 1 1 1 1 1 0 0
1 1 0 0 0 0 0 0 1 1 0 1 1 0 0 0
1 1 0 0 1 1 0 0 0 1 1 1 1 0 0 0
 
 
 
Output
 
Case #1
9 7
 
Case #2
88 99

**/
class Solution{
    int blue = 0; white = 0;
    public int[] fn(int[][] matrix){
        int n = matrix.length;
        /*
          prefix matrix stores the sum of all values 
          from 0,0 to this i,j forming a rectangle (or square shape)
          |
          |
          |
          |         x(i,j)
          |          
          ---------------------
          In this the prefix matrix stores the sum of all the elements 
          with rowNumber<=i and columnNumber<=j
        **/
      
        int[][] prefix = new int[n+1][n+1];
        construct(prefix,matrix,n);
        solve(prefix, 1,1,n);
        return new int[]{white,blue};
    }
    public void solve(int[][] prefix, int si, int sj, int size){
        int ei = si+size-1;
        int ej = sj+size-1;
        int sum = prefix[ei][ej];
        sum-=prefix[ei][sj-1];
        sum-=prefix[si-1][ej];
        sum+=prefix[si-1][sj-1];
        if(sum==size*size){
            blue++;
        }else if(sum==0){
            white++;
        }else{
            solve(prefix,si,sj,size/2); //1st quad
            solve(prefix,si,sj+size/2;size/2); //2nd quad
            solve(prefix,si+size/2,sj,size/2); //3rd quad
            solve(prefix,si+size/2,sj+size/2,size/2); //4th quad
        }
    }
    public void construct(int[][] prefix, int[][] matrix, int n){
        for(int i = 1;i<=n;i++){
            for(int j = 1;j<=n;j++){
                prefix[i][j] = matrix[i-1][j-1] //current value of matrix
                    +prefix[i-1][j]+prefix[i][j-1] //adding all the values from left and up
                    -prefix[i-1][j-1];// subtracting the overlapping section
            }
        }
    }
}

