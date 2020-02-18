import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int player = pickStarter();
        int row=3;
        int col=3;
        Game game = new Game(player,row,col);
        game.printBoard();


        while (game.hasWin() == false && !game.isDraw()) {
            if (player == 2) {

                game.moveAI();
                game.printBoard();
                if (game.hasWin() || game.isDraw()) {
                    break;
                }
                game.playerMove();
                game.printBoard();

            } else {
                game.playerMove();
                game.printBoard();
                if (game.hasWin() || game.isDraw()) {
                    break;
                }
                game.moveAI();
                game.printBoard();
            }
        }

        int value = game.endWin();
        if (value > 0) {
            System.out.print("You won!");
            System.exit(0);
        } else if (value < 0) {
            System.out.print("Computer won!");
            System.exit(0);
        }
        System.out.print("Tie.");

    }


    public static int pickStarter() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Would you like to go first or second: ");
        String symbol = sc.nextLine();

        if (symbol.equals("first")) {
            return 1;
        } else if (symbol.equals("second")) {
            return 2;
        } else {
            System.out.println("Invalid option");
            return pickStarter();
        }
    }
}