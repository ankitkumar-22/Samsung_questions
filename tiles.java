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




//same logic cleaner code
import java.util.*;
import java.io.*;
public class Samsung {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder res;

    static int[][] tiles;
    static int n;
    static int k;
    public static void main(String[] args) throws IOException{
        br= new BufferedReader(new InputStreamReader(System.in));
        int t = 1;
        res = new StringBuilder();
        while(t-->0){
            fn();
        }
        System.out.println(res);
    }
    public static void fn() throws IOException{
        n = Integer.parseInt(br.readLine());
        k = Integer.parseInt(br.readLine());
        tiles = new int[n][2];
        for(int i = 0; i<n;i++){
            st = new StringTokenizer(br.readLine());
            tiles[i][0] = Integer.parseInt(st.nextToken());
            tiles[i][1] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(tiles,(a,b)->Integer.compare(a[0],b[0]));


        int left = 0;
        int right = (int)1e9;
        int ans = -1;
        while(left<=right){
            int mid = left+(right-left)/2;
            if(helper(mid)){
                ans = mid;
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        res.append(ans);
    }
    public static boolean helper(int minDiff) {
        int left = 0;
        for(int right = 0;right<n;right++){
            while(tiles[right][0]-tiles[left][0]>minDiff)
                left++;
            if(right-left+1>=k){
                List<int[]> temp = new ArrayList<>();
                for(int i = left; i<=right; i++)
                    temp.add(tiles[i]);
                if(subHelper(temp,minDiff))
                    return true;
            }
        }
        return false;
    }

    public static boolean subHelper(List<int[]> temp, int minDiff){
        temp.sort((a,b)->Integer.compare(a[1],b[1]));
        int left = 0;
        for(int right = 0; right<temp.size();right++){
            while(temp.get(right)[1]-temp.get(left)[1]>minDiff)
                left++;
            if(right-left+1>=k)
                return true;
        }
        return false;
    }
}

