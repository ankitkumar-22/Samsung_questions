/*Test - 5
Q1.
You are given an array of strings. You can merge two strings, arr[i] and arr[j], only if,
i < j  and the last letter of arr[i] == first letter of arr[j]
Eg, you have two strings -> ?123? and ?389? -> after merging it becomes ?123389?
You can keep merging strings like this, but the ?final? string you form must be such that the first letter of the string should be the same as the last letter.
Eg, after merging several strings, the ?final? string becomes -> ?123389????1?
Print the max length of the ?final? string that can be formed in this way.
Test case - 1:
Array of strings = [?14?, ?123?, ?323?, ?321?,  ?421?, ?535?]
Possible ?final? strings -> ?323?, ?535?, ?14421?, ?123323321?, ?123321?
Ans = 9 (which is the length of ?123323321?)
Test case ? 2:
Array of strings = [?14?, ?15?, ?89?, ?22?]
Possible ?final? strings -> ?22?
Ans = 2 (which is the length+ of ?22?)
1 <= ai <=1e9.  1 <=N<= 1e5
*/

import java.util.*;
class Main{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int len = sc.nextInt();
        String[] s = new String[len];
        for(int i = 0;i<len;i++)
            s[i] = sc.next();
        int maxLen = 0;
        for(int i = 0;i<len;i++){
            boolean[] visited = new boolean[len];
            StringBuilder sb = new StringBuilder();
            maxLen = Math.max(maxLen,solve(s,i,sb,visited));
        }
        System.out.println(maxLen);
        sc.close();
    }
    public static int solve(String[] s, int index, StringBuilder sb, boolean[] visited){
        if(visited[index]){
            if(sb.length()>0 && sb.charAt(0)==sb.charAt(sb.length()-1))
                return sb.length();
            return 0;
        }
        visited[index] = true;
        int nextIndex = (index+1)%(s.length);
        int currLen = sb.length();
        int max = 0;
        if(currLen==0 || sb.charAt(currLen-1)==s[index].charAt(0)) {
            sb.append(s[index]);
            max = Math.max(max,solve(s,nextIndex,sb,visited));
            sb.delete(sb.length()-s[index].length(),sb.length());
        }
        max = Math.max(max,solve(s,nextIndex,sb,visited));
        visited[index] = false;
        return max;
    }
}
