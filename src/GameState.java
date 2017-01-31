import java.util.Comparator;

/**
 * Created by zach on 1/27/2017.
 */
public class GameState {
    public double cost;
    public GameState parent;
    public float x;
    public float y;

    public GameState(GameState parent, double cost) {
        this.parent = parent;
        this.cost = cost;
    }

    public GameState(GameState parent) {
        this.parent = parent;
    }
}

class StateComparator implements Comparator<GameState> {
    public int compare(GameState a, GameState b) {
        if (a.cost < b.cost)
            return -1;
        else if (a.cost > b.cost)
            return 1;
        return 0;
    }
}
