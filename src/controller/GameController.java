package controller;

import model.*;
import utils.Position;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class GameController {
    private GamePanel gamePanel;
    private GameModel gameModel;
    private BattleShipModel player;
    private BattleShipController playerController;
    private MonsterController monsterController;
    private ArrayList<MonsterModel> monsters;
    private ArrayList<BulletModel> bullets;
    private ArrayList<BulletModel> monsterBullets;

    private Timer timer;
    private Timer monsterTimer;
    private Timer monsterShootTimer;
    private boolean levelCleared = false;

    public GameController(GameModel gameModel, BattleShipModel player, ArrayList<MonsterModel> monsters,
                          ArrayList<BulletModel> bullets, ArrayList<BulletModel> monsterBullets, GamePanel gamePanel) {
        this.gameModel = gameModel;
        this.player = player;
        this.monsters = monsters;
        this.bullets = bullets;
        this.monsterBullets = monsterBullets;
        this.gamePanel = gamePanel;

        this.playerController = new BattleShipController(player);
        this.monsterController = new MonsterController(monsters);
    }

    public void startGameLoop(boolean[] playerMovement, boolean[] playerShooting) {
        timer = new Timer(50, e -> {
            if (playerMovement[0]) playerController.move(-10, 0, gamePanel.getWidth() - player.getWidth());
            if (playerMovement[1]) playerController.move(10, 0, gamePanel.getWidth() - player.getWidth());
            if (playerShooting[0]) {
                int bulletY = gamePanel.getHeight() - player.getHeight() - 50;
                bullets.add(playerController.shoot(bulletY));
                playerShooting[0] = false;
            }

            bullets.forEach(BulletModel::moveUp);
            monsterBullets.forEach(BulletModel::moveDown);

            handleCollision();

            bullets.removeIf(b -> b.getY() + b.getHeight() < 0);
            monsterBullets.removeIf(b -> b.getY() > gamePanel.getHeight());

            boolean monstersReachedPlayer = monsters.stream().anyMatch(monster -> {
                int monsterBottomY = monster.getY() + monster.getHeight();
                int playerTopY = gamePanel.getHeight() - player.getHeight() - 50;
                return monsterBottomY >= playerTopY;
            });

            if (monstersReachedPlayer && gameModel.getLives() > 0 && gamePanel.getEndGamePopup() == null) {
                gameModel.setLives(0);
                showDefeatPopup();
            }

            gamePanel.repaint();
        });

        monsterTimer = new Timer(200 / gameModel.getCurrentLevel(), e -> {
            monsterController.moveGroup(gamePanel.getWidth());
            gamePanel.repaint();
        });

        monsterShootTimer = new Timer(5000 / gameModel.getCurrentLevel(), e -> {
            if (monsters.isEmpty()) return;

            int shots = switch (gameModel.getCurrentLevel()) {
                case 2 -> 2;
                case 3 -> 3;
                default -> 1;
            };

            Collections.shuffle(monsters);
            shots = Math.min(shots, monsters.size());

            for (int i = 0; i < shots; i++) {
                MonsterModel shooter = monsters.get(i);
                int bulletX = shooter.getX() + shooter.getWidth() / 2 - 4;
                int bulletY = shooter.getY() + shooter.getHeight();
                monsterBullets.add(new BulletModel(new Position(bulletX, bulletY)));
            }
        });
    }

    public void startTimers() {
        timer.start();
        monsterTimer.start();
        monsterShootTimer.start();
    }

    public void stopTimers() {
        timer.stop();
        monsterTimer.stop();
        monsterShootTimer.stop();
    }

    private void handleCollision() {
        ArrayList<MonsterModel> hitMonsters = monsters.stream()
                .filter(monster -> bullets.stream().anyMatch(bullet -> collidesWithMonster(bullet, monster)))
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<BulletModel> hitBullets = bullets.stream()
                .filter(bullet -> monsters.stream().anyMatch(monster -> collidesWithMonster(bullet, monster)))
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<BulletModel> playerHitBullets = monsterBullets.stream()
                .filter(this::collidesWithPlayer)
                .collect(Collectors.toCollection(ArrayList::new));

        hitMonsters.forEach(monster -> {
            if (monster.hit()) {
                int points = monster.getLevel().getPoints();
                SwingUtilities.invokeLater(() -> gameModel.addScore(points));
            }
        });

        monsters.removeIf(monster -> monster.getHitsLeft() <= 0);
        bullets.removeAll(hitBullets);

        if (monsters.isEmpty() && !levelCleared) {
            levelCleared = true;
            SwingUtilities.invokeLater(this::showVictoryPopup);
        }

        if (!playerHitBullets.isEmpty()) {
            gameModel.loseLife();
            showDefeatPopup();
        }
        monsterBullets.removeAll(playerHitBullets);
    }

    private boolean collidesWithMonster(BulletModel bullet, MonsterModel monster) {
        Rectangle r1 = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        Rectangle r2 = new Rectangle(monster.getX(), monster.getY(), monster.getWidth(), monster.getHeight());
        return r1.intersects(r2);
    }

    private boolean collidesWithPlayer(BulletModel bullet) {
        Rectangle r1 = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        int playerY = gamePanel.getHeight() - player.getHeight() - 50;
        Rectangle r2 = new Rectangle(player.getX(), playerY, player.getWidth(), player.getHeight());
        return r1.intersects(r2);
    }

    private void showVictoryPopup() {
        String message = gameModel.getCurrentLevel() == gameModel.getMaxGameLevel() ?
                "VICTORY!!! \n YOU WON THE GAME" :
                "You won level " + gameModel.getCurrentLevel() + "!" + "\nPREPARE!";

        gamePanel.showPopup(message, () -> {
            levelCleared = false;
            bullets.clear();
            monsterBullets.clear();
            if (gameModel.getCurrentLevel() == gameModel.getMaxGameLevel()) {
                gameModel.reset();
            } else {
                gameModel.setCurrentLevel(gameModel.getCurrentLevel() + 1);
            }
            gamePanel.initMonsters();
        });
    }

    private void showDefeatPopup() {
        String lifeGrammar = gameModel.getLives() > 1 ? "lives!" : "life!";
        if (gameModel.getLives() <= 0) {
            gamePanel.showPopup("YOU LOST :(", () -> {
                gameModel.restart();
                gameModel.setCurrentLevel(1);
                monsters.clear();
                bullets.clear();
                monsterBullets.clear();
                gamePanel.initMonsters();
            });
        } else {
            gamePanel.showPopup("You have " + gameModel.getLives() + " " + lifeGrammar, () -> {});
        }
    }
}
