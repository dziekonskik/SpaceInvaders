package view;

import model.GameModel;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        setTitle("Space Invaders - The Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setMinimumSize(new Dimension(1000, screenSize.height));
        setSize(screenSize);
        setContentPane(new WelcomePanel(this, new GameModel()));
    }
}
