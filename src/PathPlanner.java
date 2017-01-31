import java.util.PriorityQueue;
import java.util.TreeSet;


public class PathPlanner {

    Model model;

    public PathPlanner(Model m) {
        this.model = m;
    }

    public GameState uniformCostSearch(GameState startState, GameState goalState) {
        PriorityQueue<GameState> frontier = new PriorityQueue();
        TreeSet<GameState> visitedGameStates = new TreeSet();
        startState.cost = 0.0;
        startState.parent = null;
        frontier.add(startState);
        visitedGameStates.add(startState);

        while (frontier.size() > 0) {
            GameState current = frontier.poll();
            GameState nextGameState = new GameState(current);

            if (current.x == goalState.x && current.y == goalState.y) {
                return current;
            }


        }
        throw new RuntimeException("There is no path to the goal");
    }

    public void checkChild(GameState nextState, int xDelta, int yDelta) {
        nextState.cost = model.getTravelSpeed(nextState.parent.x + xDelta, nextState.parent.y + yDelta);
    }
}
