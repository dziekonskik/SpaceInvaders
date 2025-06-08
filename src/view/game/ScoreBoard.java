package view.game;

import utils.FontManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ScoreBoard {
    private int score;
    private int hiScore;
    private int livesCount;
    private BufferedImage heartImg;

    public ScoreBoard(){
        this.score = 0;
        this.hiScore = 0;
        this.livesCount = 3;

        try {
            heartImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/pixel-red-heart.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScore(int points) {
        this.score += points;
        if (score > hiScore) hiScore = score;
    }

    public int getScore() {
        return this.score;
    }

    public int getHiScore() {
        return this.hiScore;
    }

    public void setLivesCount(int newLivesCount) {
        if (newLivesCount < 0) return;
        this.livesCount = newLivesCount;
    }

    public void draw(Graphics2D g2, int width) {
        g2.setColor(new Color(0,0,0,200));
        g2.fillRect(0, 0, width, 100);

        g2.setFont(FontManager.getVT323(48f));
        g2.setColor(Color.WHITE);

        String scoreLabel = "SCORE";
        String scoreValue = String.valueOf(score);

        FontMetrics fm = g2.getFontMetrics();

        int marginX = 40;
        int labelY = 40;
        int valueY = 90;
        int scoreLabelWidth = fm.stringWidth(scoreLabel);

        g2.drawString(scoreLabel, marginX, labelY);
        g2.setColor(new Color(255, 231, 76));
        g2.drawString(scoreValue, marginX + scoreLabelWidth / 3, valueY);

        String hiScoreLabel = "HI-SCORE";
        String hiScoreValue = String.valueOf(hiScore);
        g2.setColor(Color.WHITE);
        int hiScoreLabelWidth = fm.stringWidth(hiScoreLabel);
        int hiScoreLabelX = (width - hiScoreLabelWidth) / 2;
        int hiScoreValueWidth = fm.stringWidth(hiScoreValue);
        int hiScoreValueX = (width - hiScoreValueWidth) / 2;

        g2.drawString(hiScoreLabel, hiScoreLabelX, labelY);
        g2.setColor(new Color(255, 231, 76));
        g2.drawString(hiScoreValue, hiScoreValueX, valueY);

        String livesLabel = "LIVES";
        g2.setFont(FontManager.getVT323(48f));
        g2.setColor(Color.WHITE);

        int livesCountWidth = fm.stringWidth(livesLabel);
        int livesLabelX = width - livesCountWidth - marginX;
        g2.drawString(livesLabel, livesLabelX, labelY);

        int iconSize = 40;
        int iconY = 60;

        if (heartImg != null) {
            for (int i = 0; i < livesCount; i++) {
                g2.drawImage(heartImg, livesLabelX + i*iconSize, iconY, iconSize, iconSize, null);
            }
        }
    }
}

