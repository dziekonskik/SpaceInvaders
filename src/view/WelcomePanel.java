package view;

import utils.ButtonVariant;
import utils.FontManager;
import utils.Panel;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends Panel {
    GameView mainPanel;

    public WelcomePanel(GameView mainPanel) {
        super("/resources/toppng.com-spaceinvaderslogo-space-invaders-logo-777x336.png");
        this.mainPanel = mainPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        Button playButton = new Button("PLAY",64, ButtonVariant.LIGHT);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playButton.addActionListener(e -> {
            mainPanel.setContentPane(new GamePanel());
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        add(playButton);
        add(Box.createRigidArea(new Dimension(0, 50)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (this.getImage() != null) {
            int imgWidth = (int) (getWidth() * 0.5);
            int imgHeight = (int) (getHeight() * 0.4);
            int posX = getWidth()/2 - imgWidth/2;
            int posY = (int) (getHeight() * 0.10);

            g.drawImage(this.getImage(), posX, posY, imgWidth, imgHeight, this);

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
