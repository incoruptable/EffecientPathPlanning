import java.util.Comparator;

/**
 * Created by zach on 1/27/2017.
 */
public class GameState {
    public double cost;
    public GameState parent;
    public float x;
    public float y;
    public float heuristic;

    public GameState(GameState parent, double cost) {
        this.parent = parent;
        this.cost = cost;
        heuristic = 0;
    }

    public GameState(GameState parent) {
        this.parent = parent;
        heuristic = 0;
    }
}

class StateComparatorPriority implements Comparator<GameState> {
    public int compare(GameState a, GameState b) {
        if (a.cost + a.heuristic < b.cost + b.heuristic)
            return -1;
        else if (a.cost + a.heuristic > b.cost + b.heuristic)
            return 1;
        return 0;
    }
}

class StateComparator implements Comparator<GameState> {
    public int compare(GameState a, GameState b) {
        Float x1 = a.x;
        Float x2 = b.x;
        int floatCompare1 = x1.compareTo(x2);

        if (floatCompare1 != 0) {
            return floatCompare1;
        } else {
            Float y1 = a.y;
            Float y2 = b.y;
            return y1.compareTo(y2);
        }
    }
}
