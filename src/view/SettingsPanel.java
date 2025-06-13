package view;

import model.GameModel;
import utils.ButtonVariant;
import utils.FontManager;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private final MainView mainPanel;
    private final GameModel gameModel;

    public SettingsPanel(MainView mainPanel, GameModel gameModel) {
        this.mainPanel = mainPanel;
        this.gameModel = gameModel;

        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Pomoc");
        JMenuItem aboutItem = new JMenuItem("Ustawienia");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tutaj możesz dostosować ustawienia gry", "Space Invaders PJATK", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        mainPanel.setJMenuBar(menuBar);


        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        Label titleLabel = new Label("Settings", 32);
        titleLabel.setFont(FontManager.getVT323(48f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;

        contentPanel.add(new Label("Enemies per line:", 32), gbc);
        gbc.gridx = 1;
        JSpinner enemiesPerLineSpinner = new JSpinner(new SpinnerNumberModel(gameModel.getEnemiesPerLine(), 1, 20, 1));
        contentPanel.add(enemiesPerLineSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new Label("Enemy fall speed (ms):",32), gbc);
        gbc.gridx = 1;
        JSlider enemyFallSpeedSlider = new JSlider(1, 500, gameModel.getEnemyFallSpeed());
        enemyFallSpeedSlider.setMajorTickSpacing(500);
        enemyFallSpeedSlider.setMinorTickSpacing(1);
        enemyFallSpeedSlider.setPaintTicks(true);
        enemyFallSpeedSlider.setPaintLabels(true);
        contentPanel.add(enemyFallSpeedSlider, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new Label("Special Mode:", 32), gbc);
        gbc.gridx = 1;
        JCheckBox specialModeCheckBox = new JCheckBox("Reversed controls");
        specialModeCheckBox.setSelected(gameModel.isSpecialModeEnabled());
        contentPanel.add(specialModeCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new Label("Enemy lines:", 32), gbc);
        gbc.gridx = 1;
        JSpinner enemyLinesSpinner = new JSpinner(new SpinnerNumberModel(gameModel.getEnemyLines(), 1, 10, 1));
        contentPanel.add(enemyLinesSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        Button backButton = new Button("BACK", 48, ButtonVariant.DARK);
        backButton.addActionListener(e -> {
            gameModel.setEnemiesPerLine((int) enemiesPerLineSpinner.getValue());
            gameModel.setEnemyLines((int) enemyLinesSpinner.getValue());
            gameModel.setEnemyFallSpeed(enemyFallSpeedSlider.getValue());
            gameModel.setSpecialModeEnabled(specialModeCheckBox.isSelected());

            mainPanel.setJMenuBar(null);
            mainPanel.setContentPane(new WelcomePanel(mainPanel, gameModel));
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        contentPanel.add(backButton, gbc);

        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
