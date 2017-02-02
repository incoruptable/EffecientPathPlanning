import java.util.PriorityQueue;
import java.util.TreeSet;

import static java.lang.Math.*;


public class PathPlanner {

    public static float maxSpeed = 0;
    Model model;
    TreeSet<GameState> visitedGameStates;
    PriorityQueue<GameState> frontier;

    public PathPlanner(Model m) {
        this.model = m;
        if (maxSpeed == 0) {
            findMaxSpeed(m);
        }
    }

    public void findMaxSpeed(Model m) {
        for (int i = 0; i < Model.XMAX; i++) {
            for (int j = 0; j < Model.YMAX; i++) {
                maxSpeed = max(maxSpeed, m.getTravelSpeed(i, j));
            }
        }
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

    public GameState aStarSearch(GameState startState, GameState goalState) {
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
            checkChildAStar(nextGameState, 10, -10, goalState);
            nextGameState = new GameState(current);
            checkChildAStar(nextGameState, 10, 0, goalState);
            nextGameState = new GameState(current);
            checkChildAStar(nextGameState, 10, 10, goalState);
            nextGameState = new GameState(current);
            checkChildAStar(nextGameState, 0, 10, goalState);
            nextGameState = new GameState(current);
            checkChildAStar(nextGameState, 0, -10, goalState);
            nextGameState = new GameState(current);
            checkChildAStar(nextGameState, -10, -10, goalState);
            nextGameState = new GameState(current);
            checkChildAStar(nextGameState, -10, 0, goalState);
            nextGameState = new GameState(current);
            checkChildAStar(nextGameState, -10, 10, goalState);

        }
        throw new RuntimeException("There is no path to the goal");
    }

    public void checkChildAStar(GameState nextState, int xDelta, int yDelta, GameState goalState) {
        nextState.x = nextState.parent.x + xDelta;
        nextState.y = nextState.parent.y + yDelta;
        nextState.heuristic = (float) model.getDistanceToDestination(nextState, goalState) / maxSpeed;
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
