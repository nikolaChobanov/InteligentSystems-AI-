import java.util.Stack;

public class Solver {

    private Stack<State> stack = new Stack<State>();

    private boolean finished = false;
    private State solutionState;
    private int row;
    private int col;
    private int[] finalState;


    public Solver(int row, int col, int zeroPosition) {
        this.row = row;
        this.col = col;
        this.finalState = new int[row * col];
        int num = 1;
        int colRow = col * row;
        if (zeroPosition == -1) {
            zeroPosition = colRow - 1;
        }
        for (int i = 0; i < colRow; i++) {
            if (zeroPosition == i) {
                finalState[i] = 0;
            } else {
                finalState[i] = num;
                num++;
            }
        }
    }

    public void solve(int[] puzzle) {

        int depth = 0;

        if (this.row * this.col != puzzle.length) {
            System.out.println("Incorrect input data");
            System.exit(0);
        }

        State root = new State(puzzle, this.row, this.col, this.finalState);
        depth = root.getHeuristic();

        while (!this.finished) {
            stack.push(root);
            idaStar(depth);
            depth += 1;
        }
    }

    public void idaStar(int depthlimit) {

        while (!stack.isEmpty()) {

            State state = stack.pop();

            if (state.checkSolution(finalState)) {
                this.finished = true;
                this.solutionState = state;
                stack.clear();
            }

            if (depthlimit >= state.getHeuristic()) {

                if (state.hasCurrentStateOccured()) {
                    stack = state.getPositions(stack);

                }
            }
        }
    }

    public void printSolution() {

        Stack<State> solutionStack = new Stack<State>();
        State state = this.solutionState;


        while (state != null) {
            solutionStack.push(state);
            state = state.getParent();
        }
        System.out.println(solutionStack.size()-1);
        while (!solutionStack.isEmpty()) {

            State sState = solutionStack.pop();

            if (sState.getMoveDirection() == State.MoveDirection.RIGHT) {
                System.out.println("left");
            } else if (sState.getMoveDirection() == State.MoveDirection.LEFT) {
                System.out.println("right");
            } else if (sState.getMoveDirection() == State.MoveDirection.UP) {
                System.out.println("up");
            } else if (sState.getMoveDirection() == State.MoveDirection.DOWN) {
                System.out.println("down");
            }
        }
    }
}