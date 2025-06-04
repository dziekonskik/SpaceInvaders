package view;
import model.BattleShipModel;
import utils.Panel;
import utils.Position;
import java.awt.*;

public class GamePanel extends Panel {
    private BattleShipModel player;

    public GamePanel() {
        super("/resources/bg.jpg");
        player = new BattleShipModel(new Position(0,0), this.setImagePath("/resources/spaceship3.png"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.getImage() != null) {
            g.drawImage(this.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        if (player != null && player.getImage() != null) {
            int initialX = getWidth()/2;
            int initialY = getHeight()-player.getHeight()-20;
            g.drawImage(player.getImage(), initialX , initialY, player.getWidth(), player.getHeight(), this);
        }
    }
}
