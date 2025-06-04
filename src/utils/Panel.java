package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Panel extends JPanel {
    private final BufferedImage image;

    public Panel(String imagePath) {
        this.image = this.setImagePath(imagePath);
    }

    protected BufferedImage setImagePath(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getImage() {
        return image;
    }
}
