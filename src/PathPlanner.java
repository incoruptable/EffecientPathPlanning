import java.util.PriorityQueue;
import java.util.TreeSet;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;


public class PathPlanner {

    Model model;
    TreeSet<GameState> visitedGameStates;
    PriorityQueue<GameState> frontier;

    public PathPlanner(Model m) {
        this.model = m;
    }

    public GameState uniformCostSearch(GameState startState, GameState goalState) {
        StateComparator comparator = new StateComparator();
        StateComparatorPriority priority = new StateComparatorPriority();
        frontier = new PriorityQueue(priority);
        visitedGameStates = new TreeSet(comparator);
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
            checkChild(nextGameState, 10, -10);
            nextGameState = new GameState(current);
            checkChild(nextGameState, 10, 0);
            nextGameState = new GameState(current);
            checkChild(nextGameState, 10, 10);
            nextGameState = new GameState(current);
            checkChild(nextGameState, 0, 10);
            nextGameState = new GameState(current);
            checkChild(nextGameState, 0, -10);
            nextGameState = new GameState(current);
            checkChild(nextGameState, -10, -10);
            nextGameState = new GameState(current);
            checkChild(nextGameState, -10, 0);
            nextGameState = new GameState(current);
            checkChild(nextGameState, -10, 10);

        }
        throw new RuntimeException("There is no path to the goal");
    }

    public void checkChild(GameState nextState, int xDelta, int yDelta) {
        nextState.x = nextState.parent.x + xDelta;
        nextState.y = nextState.parent.y + yDelta;
        if (nextState.x < Model.XMAX && nextState.x >= 0 && nextState.y < Model.YMAX && nextState.y >= 0)
            if (abs(xDelta) + abs(yDelta) == 20)
                nextState.cost = (10 * sqrt(2) / model.getTravelSpeed(nextState.x, nextState.y)) + nextState.parent.cost;
            else
                nextState.cost = (10 / model.getTravelSpeed(nextState.x, nextState.y)) + nextState.parent.cost;
        else
            return;

        if (visitedGameStates.contains(nextState)) {
            GameState oldChild = visitedGameStates.floor(nextState);
            if (nextState.cost < oldChild.cost) {
                oldChild.cost = nextState.cost;
                oldChild.parent = nextState.parent;
            }
        } else {
            frontier.add(nextState);
            visitedGameStates.add(nextState);
        }
    }
}
