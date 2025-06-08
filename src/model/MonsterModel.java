package model;

import utils.Image;
import utils.MonsterLevel;
import utils.Position;
import utils.Size;

import java.awt.image.BufferedImage;

public class MonsterModel {
    private Image image;
    private Position position;
    private final Size size = new Size(100, 100);

    public MonsterModel(Position position ,MonsterLevel level) {
        this.position = position;
        this.image = new Image(level.getImagePath());
    }

    public int getX() {
        return position.getX();
    }
    public void setX(int newX) {
        this.position.setX(newX);
    }
    public int getY() {
        return position.getY();
    }
    public void setY(int newY) {
        this.position.setY(newY);
    }
    public int getWidth() {
        return size.getWidth();
    }
    public int getHeight() {
        return size.getHeight();
    }
    public BufferedImage getImage() {
        return image.getImage();
    }
}
