/*


Inventory Management:You are in charge of maintaining inventory for a warehouse.
You have an initial stock of goods given by an array $A$ of size $N$.Your day starts 
off by getting an inflow of goods given by another array $B$ of size $N$ (so the stock
becomes $A[i] = A[i] + B[i]$ for all $i$).After that, you can choose any one good, and
export it, making its stock 0.Before you leave for the day, you have to report the total
stock of all items currently in the warehouse to the headquarters.Your task is to find the
minimum number of days required to make the total stock of all items $\le K$, where $K$ is
given. If it is impossible to ever make the stock $\le K$, output -1.Input Format:The first 
line contains an integer $T$, the number of test cases.For each test case:The first line 
contains two integers $N$ (number of items) and $K$ (target max stock).The next $N$ lines 
each contain two integers $A[i]$ (initial stock) and $B[i]$ (daily inflow) for the $i$-th item.
Output Format:For each test case, output "#t days" where t is the test case number (starting from 1)
and days is the minimum number of days required.

Test CasesStandard Input:Plaintext5
3 10
2 1
3 1
4 1
3 15
5 1
5 2
5 3
2 1
5 10
5 10
2 5
10 2
10 2
3 0
0 1
0 2
0 3
Expected Standard Output:Plaintext#1 0
#2 0
#3 -1
#4 2
#5 0

  */



import java.util.*;
class Solution{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        int[] res = new int[tc];
        for(int t = 0;t<tc;t++){
            int N = sc.nextInt();
            int K = sc.nextInt();
            List<int[]> stock = new ArrayList<>();
            int sumInitial = 0;
            for(int i = 0;i<N;i++){
                int[] temp = new int[2];
                temp[0] = sc.nextInt();//intial stock
                sumInitial+=temp[0];
                temp[1] = sc.nextInt();//daily incoming stock
                stock.add(temp);
            }
            //if(temp)
            //sort by decreasing order of incoming daily stock
            //we want to clear the stock with max incoming on the last day
            Collections.sort(stock,((a,b)->b[1]-a[1]));
            int left = 0;
            int right = 1000;
            int ans = -1;
            while(left<=right){
                int mid = (left+right)/2;//total number of days
                int total = 0;
                for(int i = 0;i<stock.size();i++){
                    total += stock.get(i)[0]+stock.get(i)[1]*mid;
                }
                int saving = 0;
                int idx = 0;
                int day = mid;
                while(day>0 && idx<stock.size()){
                    saving+= stock.get(idx)[0]+stock.get(idx)[1]*day;
                    day--;
                    idx++;
                }
                if(total-saving<=K){
                    ans = mid;
                    right = mid-1;
                }else
                    left = mid+1;
            }
            res[t]= ans;
        }
        for(int i =  0;i<tc;i++)
            System.out.println(res[i]);
    }
}
