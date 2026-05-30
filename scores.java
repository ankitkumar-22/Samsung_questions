/*
Q2.
2 arrays given
a = [1, 2, 3, 4, 5]
b = [6, 7, 8, 9, 10]
Value of an array is the total score of elements,
and score of element is 1 if it is less than or equal to D and 2 if it is greater than D,
Find D such that A_score - B_Score is maximum

constraints -
0 <= D <= 1e9
1 <= a[i] <= 1e8
1 <= b[i] <= 1e8
1 <= a.size(), b.size() <= 1e5

*/

import java.sql.SQLOutput;
import java.util.*;
class Main{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int len = sc.nextInt();
        int[] a = new int[len];
        int[] b = new int[len];
       
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Set<Integer> set = new HashSet<>();
        for(int i = 0;i<len;i++){
            a[i] = sc.nextInt();
            max = Math.max(max,a[i]);
            min = Math.min(min,a[i]);
            set.add(a[i]);
        }
        for(int i = 0;i<len;i++){
            b[i] = sc.nextInt();
            max = Math.max(max,b[i]);
            min = Math.min(min,b[i]);
            set.add(b[i]);
        }
        set.add(max+1);
        set.add(min-1);
        Arrays.sort(a);
        Arrays.sort(b);
        int maxScore = -1;
        for(int ele : set) {
            maxScore = Math.max(maxScore,findScore(a,ele)-findScore(b,ele));
        }
        System.out.println(maxScore);

    }
    public static int findScore(int[] nums, int target){
        //find the index smaller or equal to target
        int left = 0;
        int right = nums.length-1;
        int ans = -1;
        while(left<=right){
            int mid = left + (right-left)/2;
            if(nums[mid]<=target){
                ans = mid;
                left = mid+1;
            }else
                right = mid-1;
        }
        int score = ans+1 + (nums.length-ans-1)*2;
        return score;
    }
}
