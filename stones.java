/*

https://codeforces.com/problemset/problem/358/D
*/


static void solve() throws IOException {
        // your solution here
        int n = in.nextInt();
        int[] a = new int[n];//both hungry
        int[] b = new int[n];//one hungry
        int[] c = new int[n];//none hungry
        int[][] dp = new int[n][2];
        for(int i = 0;i<n;i++)
            a[i] = in.nextInt();
        for(int i = 0;i<n;i++)
            b[i] = in.nextInt();
        for(int i = 0;i<n;i++)
            c[i] = in.nextInt();
        //dp[i][0] = if we feed hare i+1 before i
        //dp[i][1] = if we feed hare i+1 after i
        dp[0][0] = b[0]; //we have fed hare at idx 1, one hungry
        dp[0][1] = a[0]; //we have not fed hare at idx 1, all hungry

        for(int i = 1;i<n;i++){
            //dp[i][0] means that right not hungry
            //dp[i-1][0] means that left is hungry
            //dp[i-1][1] means that left is not hungry
            dp[i][0] = Math.max(dp[i-1][0]+b[i], dp[i-1][1]+c[i]);
            //dp[i][1] means that right is hungry
            dp[i][1] = Math.max(dp[i-1][0]+a[i],dp[i-1][1]+b[i]);
        }
        //because we cannot use dp[i][0] this means that we will have to feed a non-existant hare
        out.println(dp[n-1][1]);
    }
