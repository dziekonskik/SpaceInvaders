package view.game;

import utils.ButtonVariant;
import view.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControlButtonsPanel extends JPanel {
    public ControlButtonsPanel(boolean[] playerMovement, boolean[] playerShooting) {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 16, 16));

        Button left = new Button("<", 40, ButtonVariant.DARK);
        Button shoot = new Button("O", 40, ButtonVariant.LIGHT);
        Button right = new Button(">", 40, ButtonVariant.DARK);

        left.setFocusable(false);
        shoot.setFocusable(false);
        right.setFocusable(false);

        left.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { playerMovement[0] = true; }
            public void mouseReleased(MouseEvent e) { playerMovement[0] = false; }
        });
        shoot.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { playerShooting[0] = true; }
            public void mouseReleased(MouseEvent e) { playerShooting[0] = false; }
        });
        right.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { playerMovement[1] = true; }
            public void mouseReleased(MouseEvent e) { playerMovement[1] = false; }
        });

        add(left);
        add(shoot);
        add(right);
    }
    public Dimension getPreferredSize() {
        return new Dimension(320, 90);
    }
}
