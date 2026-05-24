/*
Given below are the raw materials quantities and their respective selling price(if sold as raw).

D --> No of CPUs
E --> No of memory chips
F --> No of boards
d --> Selling price of CPU
e --> Selling price of Memory chips

We are given N Computer configurations like below : 
Di, Ei, Fi, SPi, which are the CPU, Chips, Boards and one unit selling price for ith computer respectively.
Our task is to maximize the final cost.
Constraints:
1. Can use at Max 3 different Configurations
2. We can use 1 configuration multiple times
3. Remaining Inventories can be sold on its selling price

Input:
T --> Number of test cases.
D E F d e --> Inventories
N --> Total Configuration Count
Di Ei Fi SPi
...
Dn En Fn SPn

1<=T<=10
1<= D, E, F <= 100
1<= d, e <=100000
1<=N<=8

Output:
First Line print the Case #testCaseNumber
Second Line Print Maximum Cost per test case in each line.

Sample Input:
1 --> Total Test Case
10 10 10 2 1 --> D E F d e
1 --> PC Configuration Count
1 2 2 3 --> D1 E1 F1 SP1

Sample Output:
Case #1
30

**/

public int fn(int[] initial, int[][] configs) {
    return solve(initial, configs, 0, 0);
}

public int solve(int[] initial, int[][] configs, int configNum, int configsUsed) {
    // Base: no more configs to consider → sell remaining raw
    if (configNum == configs.length) {
        return (initial[0] * initial[3] + initial[1] * initial[4]);
    }

    int maxValue = 0;

    // Option 1: Skip this config entirely
    maxValue = Math.max(maxValue, solve(initial, configs, configNum + 1, configsUsed));

    // Option 2: Use this config 1, 2, 3... times (counts as 1 distinct config)
    /*
    Make sure to use while loop in such cases 
    imagine if we were using a simple dp type recursion calling in which if we 
    have enough materials we build the pc and do not increase config number and counter , 
    or we increase configNumber moving to the nextConfig and increasing counter

    and if the constraints do not match we move forward

    but the counter calculation is an issue 
    assume we use config1 and decide to use same config : counter = 1,
    then we use config 1 again and again until our resources exhaust
    and once it does we are forced out of the loop by constraint check 
    and by then our counter is still 0 , even when we have used config1 multiple times

    Thus in such cases it is better to use a while loop as shown below
    to iterate over the count. It is simple and easy to understand.
    **/
    if (configsUsed < 3) {
        int count = 1;
        while (initial[0] >= configs[configNum][0] * count &&
               initial[1] >= configs[configNum][1] * count &&
               initial[2] >= configs[configNum][2] * count) {

            // Subtract resources for count units
            initial[0] -= configs[configNum][0] * count;
            initial[1] -= configs[configNum][1] * count;
            initial[2] -= configs[configNum][2] * count;

            // Add revenue from count PCs + recurse for remaining configs
            maxValue = Math.max(maxValue,
                count * configs[configNum][3]                         
                + solve(initial, configs, configNum + 1, configsUsed + 1));

            // Restore resources
            initial[0] += configs[configNum][0] * count;
            initial[1] += configs[configNum][1] * count;
            initial[2] += configs[configNum][2] * count;

            count++;
        }
    }

    return maxValue;
}
