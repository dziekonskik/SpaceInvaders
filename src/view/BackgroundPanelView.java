package view;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class BackgroundPanelView extends JPanel {
    private BufferedImage background;

    public BackgroundPanelView () {
        try {
            // Jeśli masz obrazek w resources:
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/bg.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
