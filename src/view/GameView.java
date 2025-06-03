package view;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {

    public GameView() {
        setTitle("Space Invaders - The Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setContentPane(new BackgroundPanelView());
    }
}
