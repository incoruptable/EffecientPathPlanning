import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

class Agent {

    PathPlanner pathPlanner;
    List<GameState> correctPath;
    PriorityQueue<GameState> frontier;
    GameState finalState;
    boolean uniformCostSearch = false;
    boolean aStarSearch = false;

    public static void main(String[] args) throws Exception {
        Controller.playGame();
    }

    void drawPlan(Graphics g, Model m) {
        g.setColor(Color.red);
        //g.drawLine((int) m.getX(), (int) m.getY(), (int) m.getDestinationX(), (int) m.getDestinationY());
        if (correctPath != null && correctPath.size() > 0) {
            for (int i = 0; i < correctPath.size() - 1; i++) {
                g.drawLine((int) correctPath.get(i).x, (int) correctPath.get(i).y, (int) correctPath.get(i + 1).x, (int) correctPath.get(i + 1).y);
            }
            if (frontier != null && frontier.size() > 0) {
                Iterator<GameState> frontierIT = frontier.iterator();
                while (frontierIT.hasNext()) {
                    GameState current = frontierIT.next();
                    if (current.x < Model.XMAX && current.y < Model.YMAX && current.x >= 0 && current.y >= 0)
                        g.drawOval((int) current.x, (int) current.y, 5, 5);
                }


            }
        }

    }

    void update(Model m) {
        Controller c = m.getController();
        pathPlanner = new PathPlanner(m);

        while (true) {
            MouseEvent e = c.nextMouseEvent();
            if (uniformCostSearch && !aStarSearch && correctPath != null && correctPath.get(1).x == m.getX() && correctPath.get(1).y == m.getY()) {
                correctPath = new LinkedList<GameState>();
                GameState currentState = new GameState(null);
                currentState.x = m.getX();
                currentState.y = m.getY();
                finalState = pathPlanner.uniformCostSearch(currentState, finalState);
                correctPath(finalState);
                frontier = pathPlanner.frontier;
                if (correctPath.size() == 1) {
                    uniformCostSearch = false;
                } else {
                    m.setDestination(correctPath.get(1).x, correctPath.get(1).y);
                }
            }
            if (aStarSearch && !uniformCostSearch && correctPath != null && correctPath.get(1).x == m.getX() && correctPath.get(1).y == m.getY()) {
                correctPath = new LinkedList<GameState>();
                GameState currentState = new GameState(null);
                currentState.x = m.getX();
                currentState.y = m.getY();
                finalState = pathPlanner.aStarSearch(currentState, finalState);
                correctPath(finalState);
                frontier = pathPlanner.frontier;
                if (correctPath.size() == 1) {
                    aStarSearch = false;
                } else {
                    m.setDestination(correctPath.get(1).x, correctPath.get(1).y);
                }
            }
            if (e == null)
                break;
            if (e.getButton() == MouseEvent.BUTTON1) {
                correctPath = new LinkedList<GameState>();
                GameState currentState = new GameState(null);
                currentState.x = (int) m.getX() / 10;
                currentState.y = (int) m.getY() / 10;
                currentState.x = currentState.x * 10;
                currentState.y = currentState.y * 10;
                GameState goalState = new GameState(null);
                goalState.x = e.getX() / 10;
                goalState.y = e.getY() / 10;
                goalState.x = goalState.x * 10;
                goalState.y = goalState.y * 10;
                finalState = pathPlanner.uniformCostSearch(currentState, goalState);
                correctPath(finalState);
                frontier = pathPlanner.frontier;
                m.setDestination(correctPath.get(1).x, correctPath.get(1).y);
                uniformCostSearch = true;
                aStarSearch = false;
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                correctPath = new LinkedList<GameState>();
                GameState currentState = new GameState(null);
                currentState.x = (int) m.getX() / 10;
                currentState.y = (int) m.getY() / 10;
                currentState.x = currentState.x * 10;
                currentState.y = currentState.y * 10;
                GameState goalState = new GameState(null);
                goalState.x = e.getX() / 10;
                goalState.y = e.getY() / 10;
                goalState.x = goalState.x * 10;
                goalState.y = goalState.y * 10;
                finalState = pathPlanner.aStarSearch(currentState, goalState);
                correctPath(finalState);
                frontier = pathPlanner.frontier;
                m.setDestination(correctPath.get(1).x, correctPath.get(1).y);
                aStarSearch = true;
                uniformCostSearch = false;
            }
        }
    }

    public void correctPath(GameState state) {
        if (state.parent != null) {
            correctPath(state.parent);
        }
        correctPath.add(state);
    }
}
