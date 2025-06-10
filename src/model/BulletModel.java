package model;

import utils.Position;
import utils.Size;

public class BulletModel {
    private final Position position;
    private final Size size = new Size(8, 24);
    private final int speed = 16;

    public BulletModel(Position position) {
        this.position = position;
    }

    public int getX() {
        return position.getX();
    }
    public int getY() {
        return position.getY();
    }
    public int getWidth() {
        return size.getWidth();
    }
    public int getHeight() {
        return size.getHeight();
    }

    public void moveUp() {
        position.setY(position.getY() - speed);
    }
    public void moveDown() {
        position.setY(position.getY() + speed);
    }

}
