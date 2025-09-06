/*
You are given an array of integers which represents positions available and an integer c(cows).
Now you have to choose c positions such that minimum difference between cows is maximized.
For example,
1 3 5 8 10
c=3

Output: 4
1 5 10

link : https://www.geeksforgeeks.org/problems/aggressive-cows/1
*/

import java.util.*;

public class aggresive_cow {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] stalls = new int[n];
		 for(int i = 0;i<n;i++)
			 stalls[i]= sc.nextInt();
		 int k = sc.nextInt();
        Arrays.sort(stalls);
        int low = 1; //Keep min possible dist;
        int high = stalls[n-1]-stalls[0];//max distance
        int ans = -1;
        while(low<=high){
            int mid = (low+high)/2;
            if(canPlace(stalls,mid,k)){
                ans = mid;
                low = mid+1;
            }
            else {
                high = mid-1;
            }
        }
        System.out.println(ans);
    }
	public boolean canPlace(int stalls[], int mid, int k){
	    int count = 1;
	    int lastPos = stalls[0];
	    for(int i = 0;i<stalls.length;i++){
	        if(stalls[i]-lastPos>=mid){
	            lastPos = stalls[i];
	            count++;
	            if(count==k)
	                return true;
	        }
	    }
	    return false;
	}
}




