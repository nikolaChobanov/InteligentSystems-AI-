import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Queens {

    int[] rows;
    Random random = new Random();
    int length;
    int[] dn;
    int[] dp;
    int[] rowQueen;


    Queens(int n) {

        rows = new int[n];
        rowQueen = new int[n];
        dn = new int[2 * n - 1];
        dp = new int[2 * n - 1];
        this.length = n;
        for (int i = 0; i < length; i++) {
            rows[i] = i;
        }


        for (int i = 0; i < length; i++) {
            int j = random.nextInt(n);
            int rowToSwap = rows[i];
            rows[i] = rows[j];
            rows[j] = rowToSwap;
        }
        computeCollisions();
        riddle();
    }


    void computeCollisions() {

        for (int i = 0; i < length; i++) {

            rowQueen[i]++;

            int sum = 0;
            sum = i + rows[i];
            dn[sum] += 1;
            int neg = 0;
            neg = i - rows[i];
            if (neg >= 0) {
                dp[neg] += 1;
            } else {
                neg = Math.abs(neg);
                dp[neg + (length - 1)] += 1;
            }


        }
    }

    int computeAtacks(int row, int col) {


        int atacked = 0;
        int sum = row + col;
        int div = col - row;
        if (div < 0) {
            div = Math.abs(div) + (length - 1);
        }

        if (dn[sum] > 1) {
            atacked += dn[sum];
            atacked -= 1;
        }
        if (dp[div] > 1) {
            atacked += dp[div];
            atacked -= 1;
        }

        if (rowQueen[row] > 1) {
            atacked += rowQueen[row];
            atacked -= 1;
        }
        return atacked;
    }


    void print() {
        int length = rows.length;
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < length; c++) {
                if (rows[c] == r) {
                    System.out.print('*');

                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    void riddle() {
        ArrayList<Integer> switchEl = new ArrayList<>();
        int maxConflicts;

        while (true) {

            maxConflicts = 0;
            switchEl.clear();


            for (int c = 0; c < length; c++) {

                int conflicts = computeAtacks(rows[c], c);

                if (conflicts == maxConflicts) {
                    switchEl.add(c);
                }
                if (conflicts > maxConflicts) {
                    maxConflicts = conflicts;
                    switchEl.clear();
                    switchEl.add(c);

                }
            }


            if (maxConflicts == 0) {
                print();
                return;
            }

            int conflictQueen = switchEl.get(new Random().nextInt((switchEl.size())));
            switchEl.clear();
            int minConflicts = length;

            for (int r = 0; r < length; r++) {

                int conflicts = computeAtacks(r, conflictQueen);



                if (conflicts == minConflicts) {
                    switchEl.add(r);
                }
                if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    switchEl.clear();
                    switchEl.add(r);

                }
            }
            if (!switchEl.isEmpty()) {
                rowQueen[rows[conflictQueen]]--;
                int sumBefore = rows[conflictQueen] + conflictQueen;
                int divBefore = conflictQueen - rows[conflictQueen];
                if (divBefore < 0) {
                    divBefore = Math.abs(divBefore) + (length - 1);
                }
                dn[sumBefore]--;
                dp[divBefore]--;

                int rowSwitch = switchEl.get(new Random().nextInt((switchEl.size())));
                rows[conflictQueen] = rowSwitch;

                rowQueen[rowSwitch]++;
                int sumAfter = rowSwitch + conflictQueen;
                int divAfter = conflictQueen - rowSwitch;
                if (divAfter < 0) {
                    divAfter = Math.abs(divAfter) + (length - 1);
                }
                dn[sumAfter]++;
                dp[divAfter]++;
            }
        }



    }


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        in.close();
        long startTime = System.nanoTime();
        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime) ;
        System.out.println(totalTime);
    }
}
