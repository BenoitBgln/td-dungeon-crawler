import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;


public class GameEngine implements Engine, KeyListener {
    private final Player hero; // The main player controlled by the user
    private final JFrame displayZoneFrame; // The main game window

    public GameEngine(Player hero, JFrame displayZoneFrame) {
        this.hero = hero;
        this.displayZoneFrame = displayZoneFrame;
    }

    @Override
    public void update() {
        if (!hero.isVisible()) {
            stopTimers();
            switchToGameEndedPanel("game over");
        } else if (hero.isWin()) {
            stopTimers();
            switchToGameEndedPanel("game win");
        }
    }

    /**
     * Stop timers to avoid that game continue when game is over
     */
    private void stopTimers() {
        Main.gameTimer.stop();
        Main.renderTimer.stop();
        Main.physicTimer.stop();
    }

    /**
     * Replaces the current game view with the game end screen.
     *
     * @param state The end game state, either "game over" or "game win".
     */
    private void switchToGameEndedPanel(String state) {
        JPanel gameEndedPanel = createGameEndedPanel(state);
        displayZoneFrame.setContentPane(gameEndedPanel); // Replace the game window content
        displayZoneFrame.revalidate(); // Refresh the window
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> hero.setDirection(Direction.NORTH);
            case KeyEvent.VK_DOWN -> hero.setDirection(Direction.SOUTH);
            case KeyEvent.VK_LEFT -> hero.setDirection(Direction.WEST);
            case KeyEvent.VK_RIGHT -> hero.setDirection(Direction.EAST);
            case KeyEvent.VK_SPACE -> hero.toss();
            case KeyEvent.VK_R -> toggleWalkOrRun();
        }
    }

    /**
     * Toggles between walking and running for the player.
     */
    private void toggleWalkOrRun() {
        try {
            if (hero.isWalking()) {
                hero.run();
            } else {
                hero.walk();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Creates a panel displaying the end game message and options.
     *
     * @param state The end game state, either "game over" or "game win".
     * @return A JPanel representing the end game screen.
     */
    private JPanel createGameEndedPanel(String state) {
        JPanel panel = new JPanel(new BorderLayout());

//        // Display end game message -> displayed only if image load failed
//        JLabel label = new JLabel(state.equals("game over") ? "Game Over!" : "Game Win!", JLabel.CENTER);
//        label.setFont(new Font("Arial", Font.BOLD, 30));
//        panel.add(label, BorderLayout.NORTH);

        try {
            Image image = ImageIO.read(new File(state.equals("game over") ? "./img/gameover.jpg" : "./img/gamewin.jpg"));
            panel.setBackground(state.equals("game over") ? Color.BLACK : Color.WHITE);
            image = image.getScaledInstance(623, 640, Image.SCALE_SMOOTH);
            panel.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton restartButton = new JButton("Recommencer");
        restartButton.addActionListener(_ -> {
            try {
                restartGame();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        panel.add(restartButton, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Restarts the game by reinitializing the main game class.
     */
    private void restartGame() throws Exception {
        Main.main(null);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
