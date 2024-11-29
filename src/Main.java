import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
    static JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    Playground playground;
    PhysicEngine physicEngine = new PhysicEngine();

    static Timer renderTimer, physicTimer, gameTimer;

    public Main() throws Exception {
        displayZoneFrame = new JFrame("Java Labs");
        playground = new Playground("./data/level2.txt");
        displayZoneFrame.setSize(623, 640);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Player hero = new Player(
                ImageIO.read(new File("./img/Slime1_Walk_full2.png")),
                32, 32, // Initial position
                24, 24, // Sprite size
                8, 1, 0, 3, 2
        );

        // Create enemies and position them randomly on the map
        ArrayList<Villain> villains = new ArrayList<>();
        ArrayList<int[]> availablePositions = playground.getAvailablePositions();
        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            // Pick a random position from the available slots
            int randomIndex = rand.nextInt(availablePositions.size());
            int[] position = availablePositions.get(randomIndex);

            // Avoid placing villains too close to the start of the map
            if (position[0] < 32 * 4 && position[1] < 32 * 4) {
                continue; // Skip invalid positions
            }

            availablePositions.remove(randomIndex); // Prevent duplicate placements
            villains.add(new Villain(
                    ImageIO.read(new File("./img/Slime3_Walk_full2.png")),
                    position[0], position[1],
                    24, 24, 8, 1, 0, 3, 2
            ));
        }

        renderEngine = new RenderEngine();
        gameEngine = new GameEngine(hero, displayZoneFrame);

        renderTimer = new Timer(50, (_) -> renderEngine.update());
        physicTimer = new Timer(50, (_) -> physicEngine.update());
        gameTimer = new Timer(50, (_) -> gameEngine.update());

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);
        displayZoneFrame.addKeyListener(gameEngine);

        for (Displayable s : playground.getSpriteList()) {
            renderEngine.addToRenderList(s);
        }
        renderEngine.addToRenderList(hero);
        for (Villain v : villains) {
            renderEngine.addToRenderList(v);
        }

        physicEngine.setEnvironment(playground.getSolidSpriteList());
        physicEngine.addToEnvironment(hero);
        for (Villain v : villains) {
            physicEngine.addToEnvironment(v);
        }

        physicEngine.addToMovingSpriteList(hero);
        for (Villain v : villains) {
            physicEngine.addToMovingSpriteList(v);
        }

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();
    }

    public static void main(String[] args) throws Exception {
        // Stop and clean up previous game state before starting a new game
        if (renderTimer != null) renderTimer.stop();
        if (physicTimer != null) physicTimer.stop();
        if (gameTimer != null) gameTimer.stop();
        if (displayZoneFrame != null) displayZoneFrame.dispose();

        // Launch a new game instance
        new Main();
    }
}
