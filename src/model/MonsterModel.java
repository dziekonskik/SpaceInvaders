package model;

import utils.Image;
import utils.MonsterLevel;
import utils.Position;
import utils.Size;

import java.awt.image.BufferedImage;

public class MonsterModel {
    private final Image image;
    private final Position position;
    private Size size;
    private final MonsterLevel level;
    private int hitsLeft;

    public MonsterModel(Position position ,MonsterLevel level) {
        this.position = position;
        this.level = level;
        this.hitsLeft = level.getHitsToKill();
        this.image = new Image(level.getImagePath());
        Size baseSize = new Size(100, 100);
        this.size = new Size(baseSize.getWidth(), baseSize.getHeight());
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
    public MonsterLevel getLevel() {
        return level;
    }
    public int getHitsLeft() {
        return hitsLeft;
    }
    public boolean hit() {
        hitsLeft--;
        size = new Size(
                (int) (size.getWidth() * 0.95),
                (int) (size.getHeight() * 0.95)
        );
        return hitsLeft <= 0;
    }
}
