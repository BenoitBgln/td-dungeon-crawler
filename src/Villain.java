import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Villain extends DynamicSprite {
    int nSteps = 0;  // Counter to track the number of steps taken
    Direction lastDirection;  // The last direction the villain moved in

    public Villain(Image image, double x, double y, double width, double height, int spriteSheetNumberOfColumn,
                   int northIndex, int southIndex, int eastIndex, int westIndex) {
        super(image, x, y, width, height, spriteSheetNumberOfColumn,
                northIndex, southIndex, eastIndex, westIndex);
        lastDirection = direction;  // Initialize lastDirection with the current direction
        speed = 6; // 6 > hero walking speed, 6 < projectile speed, 6 < hero running speed
    }

    /**
     * Randomly selects a new direction for the villain to move in.
     */
    void setNewDirection() {
        int randomIndex = new Random().nextInt(Direction.values().length);
        Direction newDirection = Direction.values()[randomIndex];
        setDirection(newDirection);
    }

    @Override
    void moveIfPossible(ArrayList<Sprite> environment) {
        Random random = new Random();

        // Change direction after a random number of steps
        if (nSteps++ > random.nextInt(151) + 20) {
            nSteps = 0;  // Reset step counter
            setNewDirection();  // Set a new random direction
        }

        SolidSprite obstacle = detectObstacles(environment);

        if (obstacle == null) {
            move();  // Move the villain if there are no obstacles
        } else if (obstacle instanceof Player) {
            obstacle.delete();  // The player die if the villain collides with them
        } else {
            setNewDirection();  // Change direction if the villain encounters an obstacle
        }
    }
}
