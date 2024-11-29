import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class SolidSprite extends Sprite {
    protected double speed = 5;
    protected Direction direction = Direction.EAST;
    private boolean isExit = false;  // Flag to check if this sprite is an exit

    public SolidSprite(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    public SolidSprite(Image image, double x, double y, double width, double height, boolean isExit) {
        super(image, x, y, width, height);
        this.isExit = isExit;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Moves the sprite based on its current direction.
     */
    protected void move() {
        switch (direction) {
            case NORTH -> this.y -= speed;
            case EAST -> this.x += speed;
            case SOUTH -> this.y += speed;
            case WEST -> this.x -= speed;
        }
    }

    /**
     * Detects obstacles in the environment based on the current direction.
     *
     * @param environment the list of all sprites in the environment
     * @return the first detected obstacle, or null if none is found
     */
    protected SolidSprite detectObstacles(ArrayList<Sprite> environment) {
        Rectangle2D.Double hitBox = new Rectangle2D.Double();

        // Adjust the hitbox based on the current direction
        switch (direction) {
            case NORTH -> hitBox.setRect(x, y - speed, width, height);
            case EAST -> hitBox.setRect(x + speed, y, width, height);
            case SOUTH -> hitBox.setRect(x, y + speed, width, height);
            case WEST -> hitBox.setRect(x - speed, y, width, height);
        }

        // Check for collisions with other solid sprites
        for (Sprite s : environment) {
            if ((s instanceof SolidSprite) && (s != this)) {
                if (hitBox.intersects(s.x, s.y, s.width, s.height)) {
                    return (SolidSprite) s;
                }
            }
        }
        return null;
    }

    protected boolean isExit() {
        return isExit;
    }

    /**
     * Moves the sprite if no obstacles are detected.
     *
     * @param environment the list of all sprites in the environment
     */
    void moveIfPossible(ArrayList<Sprite> environment) {
        if (detectObstacles(environment) == null) {
            move();
        }
    }
}
