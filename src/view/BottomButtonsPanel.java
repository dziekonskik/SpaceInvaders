package view;

import model.GameModel;
import utils.ButtonVariant;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.*;

public class BottomButtonsPanel extends JPanel {
    public BottomButtonsPanel(MainView mainPanel, GameModel gameModel, GamePanel gamePanel) {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        setOpaque(false);

        Button restartButton = new Button("RESTART", 24, ButtonVariant.LIGHT);
        restartButton.addActionListener(e -> {
            gamePanel.restartGame();
        });

        Button menuButton = new Button("MENU", 24, ButtonVariant.DARK);
        menuButton.addActionListener(e -> {
            mainPanel.setJMenuBar(null);
            mainPanel.setContentPane(new WelcomePanel(mainPanel, gameModel));
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        add(restartButton);
        add(menuButton);
    }
}
