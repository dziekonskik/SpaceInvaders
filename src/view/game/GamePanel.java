package view.game;

import controller.GameController;
import controller.ScoreBoardController;
import model.BattleShipModel;
import model.BulletModel;
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
    private final GameModel gameModel;
    private final Image background;
    private final ScoreBoard scoreBoard;
    private EndGamePopup endGamePopup = null;
    private final BattleShipModel player;
    private final GameController gameController;
    private final ScoreBoardController scoreBoardController;
    private final ArrayList<MonsterModel> monsters = new ArrayList<>();
    private final ArrayList<BulletModel> bullets = new ArrayList<>();
    private final ArrayList<BulletModel> monsterBullets = new ArrayList<>();
    private final boolean[] playerMovement = new boolean[2];
    private final boolean[] playerShooting = new boolean[1];

    public GamePanel() {
        background = new Image("/resources/bg.jpg");
        player = new BattleShipModel(new Position(getWidth() / 2, 0), new Image("/resources/spaceship3.png"));
        gameModel = new GameModel();
        scoreBoard = new ScoreBoard(gameModel);
        scoreBoardController = new ScoreBoardController(gameModel, this::repaint, this::repaint);

        initMonsters();

        gameController = new GameController(gameModel, player, monsters, bullets, monsterBullets, this);
        gameController.startGameLoop(playerMovement, playerShooting);
        gameController.startTimers();

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> playerMovement[0] = true;
                    case KeyEvent.VK_RIGHT -> playerMovement[1] = true;
                    case KeyEvent.VK_SPACE -> playerShooting[0] = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> playerMovement[0] = false;
                    case KeyEvent.VK_RIGHT -> playerMovement[1] = false;
                    case KeyEvent.VK_SPACE -> playerShooting[0] = false;
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerPlayerX();
                centerMonstersX();
                centerPopupX();
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
        drawBullets(g);

        if (player != null && player.getImage() != null) {
            int x = player.getX();
            int y = getHeight() - player.getHeight() - 50;
            g.drawImage(player.getImage(), x, y, player.getWidth(), player.getHeight(), this);
        }
    }

    public void initMonsters() {
        monsters.clear();

        int rows = 2 * gameModel.getCurrentLevel();
        int cols = 5;
        MonsterModel example = new MonsterModel(new Position(0, 0), MonsterLevel.LEVEL1);
        int monsterWidth = example.getWidth();
        int monsterHeight = example.getHeight();
        int spacingX = 30;
        int totalWidth = cols * monsterWidth + (cols - 1) * spacingX;
        int offsetX = (getWidth() - totalWidth) / 2;
        int offsetY = 100;

        MonsterLevel monsterLevel = switch (gameModel.getCurrentLevel()) {
            case 1 -> MonsterLevel.LEVEL1;
            case 2 -> MonsterLevel.LEVEL2;
            case 3 -> MonsterLevel.LEVEL3;
            default -> throw new IllegalStateException("Unexpected value: " + gameModel.getCurrentLevel());
        };

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = offsetX + col * (monsterWidth + spacingX);
                int y = offsetY + row * monsterHeight;
                monsters.add(new MonsterModel(new Position(x, y), monsterLevel));
            }
        }
    }

    private void drawMonsters(Graphics g) {
        for (MonsterModel monster : monsters) {
            g.drawImage(
                    monster.getImage(),
                    monster.getX(), monster.getY(),
                    monster.getWidth(), monster.getHeight(),
                    this
            );
        }
    }

    private void drawBullets(Graphics g) {
        for (BulletModel bullet : bullets) {
            g.setColor(Color.YELLOW);
            g.fillRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
        for (BulletModel bullet : monsterBullets) {
            g.setColor(Color.RED);
            g.fillRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
    }

    private void centerPlayerX() {
        int centerX = (getWidth() - player.getWidth()) / 2;
        player.setX(centerX);
    }

    private void centerMonstersX() {
        if (monsters.isEmpty()) return;
        int cols = 5;
        MonsterModel example = monsters.get(0);
        int monsterWidth = example.getWidth();
        int spacingX = 70;
        int totalWidth = cols * monsterWidth + (cols - 1) * spacingX;
        int offsetX = (getWidth() - totalWidth) / 2;

        for (int i = 0; i < monsters.size(); i++) {
            int col = i % cols;
            MonsterModel m = monsters.get(i);
            int x = offsetX + col * (monsterWidth + spacingX);
            m.setX(x);
        }
    }

    private void centerPopupX() {
        if (endGamePopup != null) {
            endGamePopup.setBounds(0, 0, getWidth(), getHeight());
            endGamePopup.repaint();
        }
    }

    public void showPopup(String message, Runnable onClose) {
        if (endGamePopup != null) return;

        gameController.stopTimers();

        endGamePopup = new EndGamePopup(
                message,
                e -> {
                    remove(endGamePopup);
                    endGamePopup = null;
                    gameController.startTimers();
                    onClose.run();
                    repaint();
                }
        );

        endGamePopup.setBounds(0, 0, getWidth(), getHeight());
        setLayout(null);
        add(endGamePopup);
        repaint();
    }

    public EndGamePopup getEndGamePopup() {
        return endGamePopup;
    }

    public ScoreBoardController getScoreBoardController() {
        return scoreBoardController;
    }
}
