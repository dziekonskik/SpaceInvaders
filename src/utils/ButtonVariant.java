package utils;

import java.awt.Color;

public enum ButtonVariant {
    LIGHT(new Color(255, 231, 76),new Color(41, 141, 255)),
    DARK(new Color(41, 141, 255), new Color(255, 231, 76));

    private final Color bgColor;
    private final Color textColor;

    ButtonVariant(Color bg, Color text) {
        this.bgColor = bg;
        this.textColor = text;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color getTextColor() {
        return textColor;
    }
}
