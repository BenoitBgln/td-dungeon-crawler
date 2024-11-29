import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the player character in the game.
 * The player can move, toss projectiles, and change between walking and running states.
 */
public class Player extends DynamicSprite {
    private final ArrayList<Projectile> projectiles = new ArrayList<>(); // List of projectiles tossed by the player
    private boolean isWalking = true;
    private boolean win = false; // Tracks if the player has reached the exit

    public Player(Image image, double x, double y, double width, double height, int spriteSheetNumberOfColumn,
                  int northIndex, int southIndex, int eastIndex, int westIndex) {
        super(image, x, y, width, height, spriteSheetNumberOfColumn, northIndex, southIndex, eastIndex, westIndex);
    }


    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void removeProjectile(Projectile p) {
        projectiles.remove(p);
    }

    public boolean isWalking() {
        return isWalking;
    }

    /**
     * Switches the player to running mode, updating speed, sprite, and animation frame rate.
     *
     * @throws IOException If the running sprite sheet fails to load.
     */
    public void run() throws IOException {
        timeBetweenFrame = 130;
        isWalking = false;
        image = ImageIO.read(new File("./img/Slime1_Run_full2.png"));
        speed = 7;
    }

    /**
     * Switches the player to walking mode, updating speed, sprite, and animation frame rate.
     *
     * @throws IOException If the walking sprite sheet fails to load.
     */
    public void walk() throws IOException {
        timeBetweenFrame = 200;
        isWalking = true;
        image = ImageIO.read(new File("./img/Slime1_Walk_full2.png"));
        speed = 5;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    @Override
    void moveIfPossible(ArrayList<Sprite> environment) {
        SolidSprite obstacle = detectObstacles(environment);
        if (obstacle == null) {
            move();
        } else if (obstacle.isExit()) {
            setWin(true); // Mark the player as winning if the exit is reached
        }
    }

    /**
     * Tosses a projectile in the player's current direction.
     * The projectile is added to the player's projectile list.
     */
    void toss() {
        try {
            Image imageProjectile = ImageIO.read(new File("./img/carapace.png"));
            Projectile projectile = new Projectile(
                    imageProjectile,
                    x,
                    y,
                    imageProjectile.getWidth(null),
                    imageProjectile.getHeight(null),
                    direction
            );
            projectiles.add(projectile); // Add the new projectile to the list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
