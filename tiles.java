/*
Q2. Given N tiles of given width and height,
we have to select K out of it, we need to minimise
the maximum of the difference between any two tiles selected,
 the difference between any two tiles is defined as
 the maximum of the height difference and width difference.
N = 4, K = 2
Tiles = [[1, 1], [2, 10], [10, 2], [3, 3]]

4 2
1 1
2 10
10 2
3 3

answer = 2
 */


import java.util.*;
class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        List<int[]> tiles = new ArrayList<>();
        for(int i = 0;i<n;i++){
            tiles.add(new int[]{sc.nextInt(),sc.nextInt()});
        }
        tiles.sort((a, b) -> (a[0] - b[0]));
        int left = 0;
        int right = 100007;
        int ans = -1;
        while(left<=right){
            int mid = left + (right-left)/2;
            if(check(tiles,mid,k)){
                ans = mid;
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        System.out.println(ans);
    }
    public static boolean check(List<int[]> tiles, int mid, int k){
        //check for each starting point
        for(int i = 0;i<tiles.size()-k+1;i++){
            int windowLength = -1;
            for(int j = i ; j<tiles.size();j++){
                if(tiles.get(j)[0]-tiles.get(i)[0]<=mid){
                    windowLength = j-i+1;
                }else{
                    break;
                }
            }

            if(windowLength>=k){
                List<int[]> tempList = new ArrayList<>();
                for(int j = i; j<i+windowLength;j++)
                    tempList.add(tiles.get(j));
                tempList.sort((a,b)->(a[1]-b[1]));
                for(int j = 0 ; j<tempList.size()-k+1;j++){
                    int idx = j;
                    while(idx<tempList.size() && tempList.get(idx)[1]-tempList.get(j)[1]<=mid){
                        idx++;
                    }
                    if(idx-j>=k)
                        return true;
                }
            }
        }
        return false;
    }
}
