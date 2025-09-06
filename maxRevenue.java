// Question :
// Given below are the raw materials quantities and their respective selling price(if sold as raw).

// D --> No of CPUs
// E --> No of memory chips
// F --> No of boards
// d --> Selling price of CPU
// e --> Selling price of Memory chips

// We are given N Computer configurations like below : 
// Di, Ei, Fi, SPi, which are the CPU, Chips, Boards and one unit selling price for ith computer respectively.
// Our task is to maximize the final cost.
// Constraints:
// 1. Can use at Max 3 different Configurations
// 2. We can use 1 configuration multiple times
// 3. Remaining Inventories can be sold on its selling price

// Input:
// T --> Number of test cases.
// D E F d e --> Inventories
// N --> Total Configuration Count
// Di Ei Fi SPi
// ...
// Dn En Fn SPn

// 1<=T<=10
// 1<= D, E, F <= 100
// 1<= d, e <=100000
// 1<=N<=8

// Output:
// First Line print the Case #testCaseNumber
// Second Line Print Maximum Cost per test case in each line.

// Sample Input:
// 1 --> Total Test Case
// 10 10 10 2 1 --> D E F d e
// 1 --> PC Configuration Count
// 1 2 2 3 --> D1 E1 F1 SP1

// Sample Output:
// Case #1
// 30


Solution:
import java.util.Scanner;

public class maxRevenue {
    static int initialD, initialE, initialF;
    static int dPrice, ePrice;
    static int configCount;
    static Config[] configs;
    static long best;

    static class Config {
        int d, e, f, sp;
        Config(int d, int e, int f, int sp) {
            this.d = d; this.e = e; this.f = f; this.sp = sp;
        }
    }

    // Recursive search:
    // index - current configuration index we are considering
    // usedTypes - how many distinct configurations we've already chosen (0..3)
    // remD, remE, remF - remaining inventories
    // accMoney - money accumulated from built PCs so far
    static void solve(int index, int usedTypes, int remD, int remE, int remF, long accMoney) {
        // If we've considered all configs or already used 3 distinct types, finalize:
        if (index >= configCount || usedTypes == 3) {
            long total = accMoney + (long)remD * dPrice + (long)remE * ePrice;
            if (total > best) best = total;
            return;
        }

        // Option 1: skip this configuration
        solve(index + 1, usedTypes, remD, remE, remF, accMoney);

        // Option 2: use this configuration i times (i >= 1) as long as resources allow
        Config c = configs[index];
        int i = 1;
        while (true) {
            int needD = c.d * i;
            int needE = c.e * i;
            int needF = c.f * i;
            if (remD - needD >= 0 && remE - needE >= 0 && remF - needF >= 0) {
                long newMoney = accMoney + (long)c.sp * i;
                solve(index + 1, usedTypes + 1, remD - needD, remE - needE, remF - needF, newMoney);
                i++;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int tc = 1; tc <= T; tc++) {
            initialD = sc.nextInt();
            initialE = sc.nextInt();
            initialF = sc.nextInt();
            dPrice = sc.nextInt();
            ePrice = sc.nextInt();

            configCount = sc.nextInt();
            configs = new Config[configCount];
            for (int i = 0; i < configCount; i++) {
                int Di = sc.nextInt();
                int Ei = sc.nextInt();
                int Fi = sc.nextInt();
                int SPi = sc.nextInt();
                configs[i] = new Config(Di, Ei, Fi, SPi);
            }

            best = 0L;
            solve(0, 0, initialD, initialE, initialF, 0L);

            System.out.println("Case #" + tc);
            System.out.println(best);
        }
        sc.close();
    }
}
