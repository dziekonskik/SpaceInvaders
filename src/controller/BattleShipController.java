package controller;

import model.BattleShipModel;
import model.BulletModel;
import utils.Position;

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

    public BulletModel shoot(int bulletY) {
        int bulletX = model.getX() + model.getWidth() / 2 - 4;
        return new BulletModel(new Position(bulletX, bulletY));
    }

}

