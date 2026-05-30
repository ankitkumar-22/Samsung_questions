/*
Given n (1 to 100000)
And a string of size 2n

Where string is made of characters R and B showing the beads of a nacklace which are of two types red and blue respective.
The nacklace has a knot in the middle, between nth and n+1 th bead
You can't move a bead across that knot
You have to find the min number of beads to be removed to make count of blue and red beads same in the necklace
You can remove beads from both the end of the necklace

Ex:
2 RRRR, ans: 4
3 RRBRBR, ans: 2
3 RBBBBB, ans: 6
*/
import java.util.*;
class Solution{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        char[] s = sc.nextLine().toCharArray();
        int len = s.length;
        int n = len/2;
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(0,-1);
        int sum = 0; //r -1, b+1
        int max = 0;
        for(int i = 0 ;i<n;i++){
            if(s[i]=='R')
                sum-=1;
            else
                sum+=1;
            if(!map.containsKey(sum))
                map.put(sum,i);
            //max = Math.max(max,i-map.get(sum));
        }
        
        for(int i = n ;i<len;i++){
            if(s[i]=='R')
                sum-=1;
            else
                sum+=1;
            if(map.containsKey(sum))
                max = Math.max(max,i-map.get(sum));
        }
        System.out.println(len-max);
        
    }
}
