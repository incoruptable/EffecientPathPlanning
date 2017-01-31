
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

class Agent {

    PathPlanner pathPlanner;
    int i;
    List<GameState> correctPath;
    boolean startPrinting = false;

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
        }
    }

    void update(Model m) {
        Controller c = m.getController();
        pathPlanner = new PathPlanner(m);


        while (true) {
            MouseEvent e = c.nextMouseEvent();
            if (startPrinting && i < correctPath.size() - 1 && correctPath.get(i).x == m.getX() && correctPath.get(i).y == m.getY()) {
                i++;
                m.setDestination(correctPath.get(i).x, correctPath.get(i).y);


            }
            if (e == null)
                break;
            if (e.getButton() == MouseEvent.BUTTON1) {
                correctPath = new LinkedList<GameState>();
                GameState currentState = new GameState(null);
                currentState.x = m.getX();
                currentState.y = m.getY();
                GameState goalState = new GameState(null);
                goalState.x = e.getX() / 10;
                goalState.y = e.getY() / 10;
                goalState.x = goalState.x * 10;
                goalState.y = goalState.y * 10;
                GameState finalState = pathPlanner.uniformCostSearch(currentState, goalState);
                correctPath(finalState);
                startPrinting = true;
                i = 0;
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
