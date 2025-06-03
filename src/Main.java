import view.GameView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameView view = new GameView();
            view.setVisible(true);
        });
    }
}