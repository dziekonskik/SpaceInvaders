package view;

import utils.FontManager;
import javax.swing.*;
import java.awt.*;

public class PlayerNamePanel extends JPanel {
    private final JTextField nameField;

    public PlayerNamePanel() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel label = new JLabel("Player Name:");
        label.setFont(FontManager.getVT323(32f));
        label.setForeground(Color.WHITE);

        nameField = new JTextField(16);
        nameField.setFont(FontManager.getVT323(32f));
        nameField.setMaximumSize(new Dimension(300, 48));
        nameField.setForeground(Color.WHITE);
        nameField.setBackground(Color.BLACK);
        nameField.setCaretColor(Color.YELLOW);

        add(Box.createHorizontalGlue());
        add(label);
        add(Box.createRigidArea(new Dimension(20, 0)));
        add(nameField);
        add(Box.createHorizontalGlue());
    }

    public String getPlayerName() {
        return nameField.getText();
    }
}
