package view;

import utils.FontManager;
import utils.StyledComponent;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel implements StyledComponent {
    public Label(String text, float fontSize) {
        super(text);
        applyStyle();
        setFont(FontManager.getVT323(fontSize));
    }

    @Override
    public void applyStyle() {
        setForeground(Color.CYAN);
    }
}
