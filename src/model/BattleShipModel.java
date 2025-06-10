package model;

import utils.Image;
import utils.Position;
import utils.Size;

import java.awt.image.BufferedImage;

public class BattleShipModel {
    private final Position position;
    private final Size size = new Size(100,100);
    private final Image image;


    public BattleShipModel(Position position, Image image) {
        this.position = position;
        this.image = image;
    }

    public BufferedImage getImage() {
        return image.getImage();
    }

    public int getX() {
        return this.position.getX();
    }

    public void setX(int newX) {
        this.position.setX(newX);
    }

    public int getY() {
        return this.position.getY();
    }

    public int getWidth() {
        return this.size.getWidth();
    }

    public int getHeight() {
        return this.size.getHeight();
    }
}
