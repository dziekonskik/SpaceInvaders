package controller;

import model.MonsterModel;
import java.util.List;

public class MonsterController {
    private final List<MonsterModel> monsters;
    private boolean movingRight = true;
    private final int step = 8;
    private final int downStep = 32;

    public MonsterController(List<MonsterModel> monsters) {
        this.monsters = monsters;
    }

    public void moveGroup(int panelWidth) {
        if (monsters.isEmpty()) return;
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
