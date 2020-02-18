import java.util.Scanner;

public class Game {
    private String[][] board;
    private String player;
    public int row;
    public int col;


    public Game(int player, int row, int col) {
        board = new String[row][col];

        if (player == 1) {
            this.player = "X";
        } else {
            this.player = "O";
        }
        this.row = row;
        this.col = col;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = "-";
            }
        }

    }


    public void printBoard() {
        String[] label = {"1 ", "2 ", "3 "};
        for (int i = 0; i < row; i++) {
            if (i == 0) {
                System.out.println("  1 2 3  ");
            }
            for (int j = 0; j < col; j++) {
                if (j == 0) {
                    System.out.print(label[i] + board[i][j] + "|");
                } else {
                    System.out.print(board[i][j] + "|");
                }
            }
            System.out.println();
        }
    }


    public String opposite(String val) {
        if (val == "X") {
            return "O";
        } else {
            return "X";
        }
    }


    public void playerMove() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter row: ");
        int row = sc.nextInt();
        System.out.print("Enter column: ");
        int column = sc.nextInt();
        if (row > this.row || col > this.col) {
            System.out.println("Spot is invalid.");
            playerMove();
        }
        if (board[row - 1][column - 1] != "-") {
            System.out.println("Spot is taken or invalid.");
            playerMove();
        } else {
            board[row - 1][column - 1] = player;
        }
    }


    public boolean hasWin() {

        for (int i = 0; i < row; i++) {
            if (this.board[i][0] == this.board[i][1] &&
                    this.board[i][0] == this.board[i][2] &&
                    this.board[i][0] != "-") {
                return true;
            }
        }
        for (int j = 0; j < col; j++) {
            if (this.board[0][j] == this.board[1][j] &&
                    this.board[0][j] == this.board[2][j] &&
                    this.board[0][j] != "-") {
                return true;
            }
        }
        if (this.board[0][0] == this.board[1][1] &&
                this.board[0][0] == this.board[2][2] &&
                this.board[0][0] != "-") {
            return true;
        }
        if (this.board[0][2] == this.board[1][1] &&
                this.board[0][2] == this.board[2][0] &&
                this.board[0][2] != "-") {
            return true;
        }
        return false;
    }


    public int endWin() {
        for (int i = 0; i < row; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == player) {
                    return 10;
                } else if (board[i][0] == opposite(player)) {
                    return -10;
                }
            }
        }
        for (int i = 0; i < col; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == player) {
                    return 10;
                } else if (board[0][i] == opposite(player)) {
                    return -10;
                }
            }
            if ((board[1][1] == board[0][0] && board[1][1] == board[2][2]) || (board[1][1] == board[0][2] && board[1][1] == board[2][0])) {
                if (board[1][1] == player) {
                    return 10;
                } else if (board[1][1] == opposite(player)) {
                    return -10;
                }
            }
        }
        return 0;
    }
    public boolean isDraw() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (this.board[i][j] == "-") {
                    return false;
                }
            }
        }
        return true;
    }

    public void moveAI() {
        if (board[0][0] == "-") {
            board[0][0] = opposite(player);
            return;
        }

        State bestState = new State(Integer.MIN_VALUE, 1, 1);
        int score = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == "-") {
                    board[i][j] = opposite(player);
                    score = min(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (score > bestState.getHeuristics()) {
                        bestState = new State(score, i, j);
                    }
                    board[i][j] = "-";
                }
            }
        }
        board[bestState.getX()][bestState.getY()] = opposite(player);
    }

    public int min(int depth, int alpha, int beta) {

        int value = endWin();
        if(value!=0){
            return value*-1;
        }
        if (isDraw()) {
            return 0;
        }
        int bestValue = Integer.MAX_VALUE;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == "-") {
                    board[i][j] = player;
                    bestValue = Math.min(max(depth + 1, alpha, beta) + depth, bestValue);
                    beta = Math.min(beta, bestValue);
                    board[i][j] = "-";
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
        return bestValue;
    }


    public int max(int depth, int alpha, int beta) {


        int value = endWin();
        if(value!=0){
            return value*-1;
        }
        if (isDraw()) {
            return 0;
        }
        int bestValue = Integer.MIN_VALUE;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == "-") {
                    board[i][j] = opposite(player);
                    bestValue = Math.max(min(depth + 1, alpha, beta) - depth, bestValue);
                    alpha = Math.max(alpha, bestValue);
                    board[i][j] = "-";
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
        return bestValue;
    }


}