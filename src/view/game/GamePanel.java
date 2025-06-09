package view.game;
import controller.BattleShipController;
import controller.MonsterController;
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
import java.util.stream.Collectors;

public class GamePanel extends JPanel {
    private GameModel gameModel;
    private Image background;
    private ScoreBoard scoreBoard;
    private EndGamePopup endGamePopup = null;
    private BattleShipModel player;
    private BattleShipController playerController;
    private MonsterController monsterController;
    private ArrayList<MonsterModel> monsters = new ArrayList<>();
    private ArrayList<BulletModel> bullets = new ArrayList<>();
    private ArrayList<BulletModel> monsterBullets = new ArrayList<>();
    private Timer timer;
    private Timer monsterTimer;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private boolean isShooting = false;
    private boolean levelCleared = false;



    public GamePanel() {
        background = new Image("/resources/bg.jpg");
        player = new BattleShipModel(new Position(getWidth()/2,0), new Image("/resources/spaceship3.png"));
        playerController = new BattleShipController(player);
        gameModel = new GameModel();
        scoreBoard = new ScoreBoard(gameModel);
        monsterController = new MonsterController(monsters);
        initMonsters();
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> isMovingLeft = true;
                    case KeyEvent.VK_RIGHT -> isMovingRight = true;
                    case KeyEvent.VK_SPACE -> isShooting = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> isMovingLeft = false;
                    case KeyEvent.VK_RIGHT -> isMovingRight = false;
                    case KeyEvent.VK_SPACE -> isShooting = false;
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

        timer = new Timer(50, e -> {
            if (isMovingLeft) playerController.move(-10, 0, getWidth() - player.getWidth());
            if (isMovingRight) playerController.move(10, 0, getWidth() - player.getWidth());
            if (isShooting) {
                int bulletY = getHeight() - player.getHeight() - 50;
                bullets.add(playerController.shoot(bulletY));
                isShooting = false;
            }

            for (BulletModel bullet : bullets) {
                bullet.moveUp();
            }

            handleCollision();
            bullets.removeIf(b -> b.getY() + b.getHeight() < 0);
            repaint();
        });

        timer.start();

        monsterTimer = new Timer(200 / gameModel.getCurrentLevel(), e -> {
            monsterController.moveGroup(getWidth());
            repaint();
        });
        monsterTimer.start();
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
            g.drawImage(player.getImage(), x , y, player.getWidth(), player.getHeight(), this);
        }
    }

    private void initMonsters() {
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

    private boolean collides(BulletModel bullet, MonsterModel monster) {
        Rectangle r1 = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        Rectangle r2 = new Rectangle(monster.getX(), monster.getY(), monster.getWidth(), monster.getHeight());
        return r1.intersects(r2);
    }

    private void handleCollision() {
        ArrayList<MonsterModel> hitMonsters = monsters.stream()
                .filter(monster -> bullets.stream().anyMatch(bullet -> collides(bullet, monster)))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<BulletModel> hitBullets = bullets.stream()
                .filter(bullet -> monsters.stream().anyMatch(monster -> collides(bullet, monster)))
                .collect(Collectors.toCollection(ArrayList::new));

        hitMonsters.forEach(monster -> {
            if (monster.hit()) {
                int points = monster.getLevel().getPoints();
                addPointsAsync(points);
            }
        });
        monsters.removeIf(monster -> monster.getHitsLeft() <= 0);
        bullets.removeAll(hitBullets);

        if (monsters.isEmpty() && !levelCleared) {
            levelCleared = true;
            SwingUtilities.invokeLater(() -> showVictoryPopup());
        }
    }

    private void addPointsAsync(int points) {
        new Thread(() -> {
            SwingUtilities.invokeLater(() -> gameModel.addScore(points));
        }).start();
    }

    private void showVictoryPopup() {
        String message = gameModel.getCurrentLevel() == gameModel.getMaxGameLevel() ?
                "CONGRATS!!! \n YOU WON THE GAME" :
                "You won level " + gameModel.getCurrentLevel() + "!" + "\nPREPARE!";
        endGamePopup = new EndGamePopup(
                message,
                e -> {
                    remove(endGamePopup);
                    endGamePopup = null;
                    levelCleared = false;
                    if (gameModel.getCurrentLevel() == gameModel.getMaxGameLevel()) {
                        gameModel.reset();
                    }
                    gameModel.setCurrentLevel(gameModel.getCurrentLevel() + 1);
                    initMonsters();
                    repaint();
                }
        );
        endGamePopup.setBounds(0, 0, getWidth(), getHeight());
        setLayout(null);
        add(endGamePopup);
        repaint();
    }
}
