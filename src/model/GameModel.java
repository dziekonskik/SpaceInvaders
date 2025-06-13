package model;

public class GameModel {
    private int currentLevel = 1;
    private int score = 0;
    private int hiScore = 0;
    private int lives = 3;
    private int enemiesPerLine = 5;
    private int enemyFallSpeed = 200;
    private int enemyLines = 3;
    private boolean specialModeEnabled = false;
    private String playerName;

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

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getEnemiesPerLine() {
        return enemiesPerLine;
    }

    public void setEnemiesPerLine(int enemiesPerLine) {
        this.enemiesPerLine = enemiesPerLine;
    }

    public int getEnemyFallSpeed() {
        return enemyFallSpeed;
    }

    public void setEnemyFallSpeed(int enemyFallSpeed) {
        this.enemyFallSpeed = enemyFallSpeed;
    }

    public int getEnemyLines() {
        return enemyLines;
    }

    public void setEnemyLines(int enemyLines) {
        this.enemyLines = enemyLines;
    }

    public boolean isSpecialModeEnabled() {
        return specialModeEnabled;
    }

    public void setSpecialModeEnabled(boolean specialModeEnabled) {
        this.specialModeEnabled = specialModeEnabled;
    }
}
