package controller;

import model.MonsterModel;
import java.util.List;

public class MonsterController {
    private final List<MonsterModel> monsters;
    private boolean movingRight = true;

    public MonsterController(List<MonsterModel> monsters) {
        this.monsters = monsters;
    }

    public void moveGroup(int panelWidth) {
        if (monsters.isEmpty()) return;
        int step = 8;
        int dx = movingRight ? step : -step;

        boolean atEdge = false;
        for (MonsterModel m : monsters) {
            int nextX = m.getX() + dx;
            if (movingRight && nextX + m.getWidth() >= panelWidth) {
                atEdge = true;
                break;
            }
            if (!movingRight && nextX <= 0) {
                atEdge = true;
                break;
            }
        }

        if (atEdge) {
            for (MonsterModel m : monsters) {
                int downStep = 32;
                m.setY(m.getY() + downStep);
            }
            movingRight = !movingRight;
        } else {
            for (MonsterModel m : monsters) {
                m.setX(m.getX() + dx);
            }
        }
    }
}
