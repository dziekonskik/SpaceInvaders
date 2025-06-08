package utils;

public class Position {
    private int x,y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int newX){
        this.x = newX;
    }

    public int getY() {
        return y;
    }

    public void setY(int newY){
        this.y = newY;
    }
}
