package view;

import model.GameModel;
import model.HighScoresModel;
import utils.ButtonVariant;
import utils.FontManager;
import utils.Image;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WelcomePanel extends JPanel {
    private final MainView mainPanel;
    private final GameModel gameModel;
    private final PlayerNamePanel namePanel;
    private final HighScoresModel highScoresModel;
    private final Image logo;

    public WelcomePanel(MainView mainPanel, GameModel gameModel) {
        logo = new Image("/resources/toppng.com-spaceinvaderslogo-space-invaders-logo-777x336.png");
        this.mainPanel = mainPanel;
        this.gameModel = gameModel;
        namePanel =  new PlayerNamePanel();
        highScoresModel = new HighScoresModel();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        add(namePanel);
        add(Box.createRigidArea(new Dimension(0, 32)));

        Button highScoresButton = new Button("HIGH SCORES",48, ButtonVariant.DARK);
        highScoresButton.addActionListener(e -> {
            ArrayList<HighScoresModel.ScoreEntry> topScores = (ArrayList<HighScoresModel.ScoreEntry>) highScoresModel.getScores();
            StringBuilder sb = new StringBuilder();
            for (HighScoresModel.ScoreEntry entry : topScores) {
                sb.append(entry.name).append(" - ").append(entry.score).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Top 10", JOptionPane.INFORMATION_MESSAGE);
        });

        Button playButton = new Button("PLAY",64, ButtonVariant.LIGHT);
        playButton.addActionListener(e -> {
            if (namePanel.getPlayerName().isBlank()) return;
            gameModel.setPlayerName(namePanel.getPlayerName());
            mainPanel.setContentPane(new GamePanel(gameModel, highScoresModel));
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        Button settingsButton = new Button("SETTINGS", 48, ButtonVariant.DARK);
        settingsButton.addActionListener(e -> {
            mainPanel.setContentPane(new SettingsPanel(mainPanel, gameModel));
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(playButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(50, 50)));
        buttonsPanel.add(highScoresButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(50, 50)));
        buttonsPanel.add(settingsButton);
        add(buttonsPanel);
        add(Box.createRigidArea(new Dimension(0, 50)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (this.logo.getImage() != null) {
            int imgWidth = (int) (getWidth() * 0.5);
            int imgHeight = (int) (getHeight() * 0.4);
            int posX = getWidth()/2 - imgWidth/2;
            int posY = (int) (getHeight() * 0.10);

            g.drawImage(this.logo.getImage(), posX, posY, imgWidth, imgHeight, this);

            String h1 = "Edition PJATK";
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(h1);
            int textX = getWidth() / 2 - textWidth*2;
            int textY = posY + imgHeight + 100;

            g.setFont(FontManager.getVT323(70f));
            g.setColor(Color.white);
            g.drawString(h1, textX, textY);
        }

    }
}
