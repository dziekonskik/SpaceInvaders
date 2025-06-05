package view;

import utils.ButtonVariant;
import utils.FontManager;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    private Color bgColor;
    private Color textColor;

    public Button(String text, float fontSize, ButtonVariant variant) {
        super(text);
        this.bgColor = variant.getBgColor();
        this.textColor = variant.getTextColor();
        setFont(FontManager.getVT323(fontSize));
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(this.textColor);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getHeight();
        int paddingX = 50;
        int paddingY = 25;
        return new Dimension(textWidth + paddingX, textHeight + paddingY);
    }
}
