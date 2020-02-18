import java.util.Arrays;
import java.util.Stack;

public class State {

    public enum MoveDirection {
        ROOT, UP, DOWN, LEFT, RIGHT
    }

    private int[] puzzle;
    private int heuristic;
    private int row;
    private int col;
    private int deep;
    private State parent;
    private MoveDirection moveDirection = MoveDirection.ROOT;


    public State(int[] puzzle, int row, int col, int[] goalState) {
        this.puzzle = new int[row * col];
        this.col = col;
        this.row = row;
        this.puzzle = puzzle;
        this.heuristic = calcManhattanDist(this);

    }


    private State(State parentState) {
        this.puzzle = parentState.puzzle.clone();
        this.col = parentState.col;
        this.row = parentState.row;
        this.deep = parentState.deep + 1;
    }


    public boolean checkSolution(int[] goalState) {
        return Arrays.equals(puzzle, goalState);

    }

    public int getHeuristic() {
        return heuristic;
    }

    public MoveDirection getMoveDirection() {
        return this.moveDirection;
    }

    public State getParent() {
        return this.parent;
    }

    public Stack<State> getPositions(Stack<State> queue) {

        int x = getX(getBlankPos());
        int y = getY(getBlankPos());


        if ((x == 0) && (y == 0)) {
            queue.add(moveRight(x, y));
            queue.add(moveDown(x, y));


        } else if ((x == row - 1) && (y == 0)) {
            queue.add(moveLeft(x, y));
            queue.add(moveDown(x, y));


        } else if (y == 0) {
            queue.add(moveLeft(x, y));
            queue.add(moveRight(x, y));
            queue.add(moveDown(x, y));


        } else if ((x == 0) && (y == col - 1)) {
            queue.add(moveRight(x, y));
            queue.add(moveUp(x, y));


        } else if ((x == row - 1) && (y == col - 1)) {
            queue.add(moveLeft(x, y));
            queue.add(moveUp(x, y));


        } else if (y == col - 1) {
            queue.add(moveLeft(x, y));
            queue.add(moveRight(x, y));
            queue.add(moveUp(x, y));


        } else if (x == 0) {
            queue.add(moveDown(x, y));
            queue.add(moveUp(x, y));
            queue.add(moveRight(x, y));


        } else if (x == row - 1) {
            queue.add(moveDown(x, y));
            queue.add(moveUp(x, y));
            queue.add(moveLeft(x, y));


        } else {
            queue.add(moveDown(x, y));
            queue.add(moveUp(x, y));
            queue.add(moveRight(x, y));
            queue.add(moveLeft(x, y));
        }
        return queue;
    }


    public boolean hasCurrentStateOccured() {
        boolean expand = false;
        if (this.deep > 1) {
            if (!Arrays.equals(this.parent.parent.puzzle, this.puzzle)) {
                expand = true;
            }
        } else {
            expand = true;
        }
        return expand;
    }


    private State moveRight(int x, int y) {
        return swapCreateState(x, y, x + 1, y, MoveDirection.RIGHT);
    }

    private State moveLeft(int x, int y) {
        return swapCreateState(x, y, x - 1, y, MoveDirection.LEFT);
    }

    private State moveDown(int x, int y) {
        return swapCreateState(x, y, x, y + 1, MoveDirection.DOWN);
    }

    private State moveUp(int x, int y) {
        return swapCreateState(x, y, x, y - 1, MoveDirection.UP);
    }

    private int getX(int bufferPos) {
        return bufferPos % row;
    }

    private int getY(int bufferPos) {
        return bufferPos / col;
    }

    private int getPos(int x, int y) {
        return y * row + x;
    }

    private int getBlankPos() {
        int pos = 0;
        if (puzzle == null) {
            return pos;
        }
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 0) {
                pos = i;
                break;
            }
        }
        return pos;
    }


    private State swapCreateState(int fromX, int fromY, int toX, int toY, MoveDirection move) {

        State child = new State(this);
        child.parent = this;

        int toN = child.puzzle[getPos(toX, toY)];
        int fromN = child.puzzle[getPos(fromX, fromY)];

        child.puzzle[getPos(toX, toY)] = fromN;
        child.puzzle[getPos(fromX, fromY)] = toN;
        child.moveDirection = move;
        child.heuristic = calcManhattanDist(child);

        return child;
    }


    private int calcManhattanDist(State state) {

        int childHeuristic = 0;

        for (int i = 0; i < state.puzzle.length; i++) {
            int heuristic = 0;
            if (state.puzzle[i] != 0) {

                int pos = -1;
                for (int j = 0; j < state.puzzle.length; j++) {
                    if (state.puzzle[i] == state.puzzle[j]) {
                        pos = j;
                        break;
                    }
                }
                int bufferPos = pos;
                int xIst = getX(bufferPos);
                int yIst = getY(bufferPos);

                int xSoll = getX(state.puzzle[i] - 1);
                int ySoll = getY(state.puzzle[i] - 1);

                heuristic = Math.abs(xIst - xSoll) + Math.abs(yIst - ySoll);
            }
            childHeuristic = childHeuristic + heuristic;
        }
        return childHeuristic + state.deep;
    }
}