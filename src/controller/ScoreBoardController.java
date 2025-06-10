package controller;

import model.GameModel;

import javax.swing.*;

public class ScoreBoardController {
    private final GameModel gameModel;
    private final Runnable onScoreUpdated;
    private final Runnable onLivesUpdated;

    public ScoreBoardController(GameModel gameModel, Runnable onScoreUpdated, Runnable onLivesUpdated) {
        this.gameModel = gameModel;
        this.onScoreUpdated = onScoreUpdated;
        this.onLivesUpdated = onLivesUpdated;
    }

    public void addScore(int points) {
        new Thread(() -> {
            int targetScore = gameModel.getScore() + points;
            while (gameModel.getScore() < targetScore) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                SwingUtilities.invokeLater(() -> {
                    gameModel.addScore(points);
                    onScoreUpdated.run();
                });
            }
        }).start();
    }

    public void loseLife(Runnable onLifeLost) {
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(() -> {
                gameModel.loseLife();
                onLivesUpdated.run();
                onLifeLost.run();
            });
        }).start();
    }
}
