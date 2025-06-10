package model;

public class GameModel {
    private int currentLevel = 1;
    private int score = 0;
    private int hiScore = 0;
    private int lives = 3;

    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    public int getScore() {
        return score;
    }
    public int getHiScore() {
        return hiScore;
    }
    public int getLives() {
        return lives;
    }
    public void addScore(int points) {
        this.score += points;
        if (score > hiScore) hiScore = score;
    }

    public int getMaxGameLevel() {
        return 3;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void loseLife() {
        if (lives > 0) lives--;
    }

    public void reset() {
        score = 0;
        lives = 3;
        currentLevel = 1;
    }
    public void restart() {
        reset();
        hiScore = 0;
    }
}
