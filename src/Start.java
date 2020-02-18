import java.util.Scanner;

public class Start {


    public static void main(String[] args) {


        Scanner input;
        input = new Scanner(System.in);

        int num = input.nextInt();
        double numb = (num + 1);
        double size = Math.log(numb) / Math.log(2);
        int boardSize = (int) size;
        int zeroPos = input.nextInt();
        Solver solver = new Solver(boardSize, boardSize, zeroPos);
        int[] blocks = new int[num + 1];
        for (int i = 0; i <= num; i++) {
            blocks[i] = input.nextInt();
        }
        solver.solve(blocks);
        solver.printSolution();
    }
}