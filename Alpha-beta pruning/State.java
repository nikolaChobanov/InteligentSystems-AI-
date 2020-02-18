public class State {


    private int heuristics;
    private int x;
    private int y;

    public State(int heuristics, int x, int y) {
        this.heuristics = heuristics;
        this.x = x;
        this.y = y;
    }


    public int getHeuristics() {
        return heuristics;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
