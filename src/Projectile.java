import java.awt.*;
import java.util.ArrayList;

public class Projectile extends SolidSprite {

    public Projectile(Image image, double x, double y, double width, double height, Direction direction) {
        super(image, x, y, width, height);
        setDirection(direction);
        speed = 10; // 10 > villain speed
    }

    @Override
    public void moveIfPossible(ArrayList<Sprite> environment) {
        SolidSprite obstacle = detectObstacles(environment);

        // If no obstacle is detected, move the projectile or if obstacle is the player
        if (obstacle == null || obstacle instanceof Player) {
            move();
        }
        // If the obstacle is a villain, it dies along with the projectile
        else if (obstacle instanceof Villain) {
            obstacle.delete();
            delete();
        }
        // Otherwise, the projectile dies
        else {
            delete();
        }
    }
}
