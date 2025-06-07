package controller;

import model.BattleShipModel;

public class BattleShipController {
    private final BattleShipModel model;

    public BattleShipController(BattleShipModel model) {
        this.model = model;
    }

    public void move(int dx, int minX, int maxX) {
        int newX = model.getX() + dx;
        if (newX < minX) newX = minX;
        if (newX > maxX) newX = maxX;
        model.setX(newX);
    }
}

