package view.game;
import controller.BattleShipController;
import model.BattleShipModel;
import model.GameModel;
import model.MonsterModel;
import utils.Image;
import utils.MonsterLevel;
import utils.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private Image background;
    private ScoreBoard scoreBoard;
    private BattleShipModel player;
    private BattleShipController playerController;
    private ArrayList<MonsterModel> monsters = new ArrayList<>();
    private GameModel gameModel;

    public GamePanel() {
        background = new Image("/resources/bg.jpg");
        player = new BattleShipModel(new Position(getWidth()/2,0), new Image("/resources/spaceship3.png"));
        playerController = new BattleShipController(player);
        scoreBoard = new ScoreBoard();
        gameModel = new GameModel();
        initMonsters();
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> playerController.move(-20, 0, getWidth());
                    case KeyEvent.VK_RIGHT -> playerController.move(20, 0, getWidth());
//                    case KeyEvent.VK_SPACE -> shoot();
                }
                repaint();
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerPlayerX();
                repaint();
            }
        });
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.background != null) {
            g.drawImage(this.background.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        scoreBoard.draw((Graphics2D) g, getWidth());
        drawMonsters(g);

        if (player != null && player.getImage() != null) {
            int x = player.getX();
            int y = getHeight() - player.getHeight() - 50;
            g.drawImage(player.getImage(), x , y, player.getWidth(), player.getHeight(), this);
        }
    }

    private int[] initMonsters() {
        int rows = 2 + gameModel.getCurrentLevel();
        int cols = 5;
        int monsterWidth = monsters.stream().mapToInt(MonsterModel::getWidth).max().orElse(0);
        int monsterHeight =  monsters.stream().mapToInt(MonsterModel::getHeight).max().orElse(0);
        int spacingX = 70;
        int spacingY = 50;

        monsters.clear();

        if (monsters.isEmpty()) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int x = col * (monsterWidth + spacingX);
                    int y = row * (monsterHeight + spacingY);
                    monsters.add(new MonsterModel(new Position(x, y), MonsterLevel.LEVEL1));
                }
            }
        }

        return new int[]{
                monsterHeight, monsterWidth, rows, cols, spacingX, spacingY
        };
    }

    private void drawMonsters(Graphics g) {
        int[] config = initMonsters();
        int monsterHeight = config[0];
        int monsterWidth  = config[1];
        int rows          = config[2];
        int cols          = config[3];
        int spacingX      = config[4];
        int spacingY      = config[5];

        int totalWidth = cols * monsterWidth + (cols - 1) * spacingX;
        int offsetX = (getWidth() - totalWidth) / 2;
        int offsetY = 140;

        int monsterIdx = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (monsterIdx >= monsters.size()) break;
                MonsterModel monster = monsters.get(monsterIdx);
                int x = offsetX + col * (monsterWidth + spacingX);
                int y = offsetY + row * (monsterHeight + spacingY);
                g.drawImage(
                        monster.getImage(),
                        x, y,
                        monsterWidth, monsterHeight,
                        this
                );
                monsterIdx++;
            }
        }
    }

    private void centerPlayerX() {
        int centerX = (getWidth() - player.getWidth()) / 2;
        player.setX(centerX);
    }
}
