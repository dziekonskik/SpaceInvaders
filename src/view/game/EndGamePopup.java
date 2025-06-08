package view.game;

import utils.ButtonVariant;
import view.Button;
import utils.FontManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EndGamePopup extends JPanel {
    private String message;
    private final Button okButton;

    public EndGamePopup(String message, ActionListener onClose) {
        setOpaque(false);
        this.message = message;
        setLayout(null);

        okButton = new Button("OK", 28f, ButtonVariant.DARK);
        okButton.setFont(FontManager.getVT323(28f));
        okButton.addActionListener(onClose);
        add(okButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, getWidth(), getHeight());

        int boxWidth = 500, boxHeight = 350;
        int boxX = (getWidth() - boxWidth) / 2;
        int boxY = (getHeight() - boxHeight) / 2;
        g2.setColor(new Color(255, 255, 255, 230));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 28, 28);

        g2.setFont(FontManager.getVT323(64f));
        g2.setColor(Color.BLACK);

        FontMetrics fm = g2.getFontMetrics();
        String[] lines = message.split("\n");
        int lineSpacing = 40;
        int startY = boxY + lineSpacing + fm.getAscent();
        for (String line : lines) {
            int textWidth = fm.stringWidth(line);
            g2.drawString(line, boxX + (boxWidth - textWidth) / 2, startY);
            startY += fm.getAscent() + lineSpacing;
        }

        g2.dispose();

        int btnWidth = okButton.getPreferredSize().width;
        int btnHeight = okButton.getPreferredSize().height;
        int btnX = boxX + (boxWidth - btnWidth) / 2;
        int btnY = boxY + boxHeight - btnHeight - 30;
        okButton.setBounds(btnX, btnY, btnWidth, btnHeight);
    }

    public void setMessage(String message) {
        this.message = message;
        repaint();
    }
}
