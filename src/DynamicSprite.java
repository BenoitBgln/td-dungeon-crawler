import java.awt.*;
import java.util.Random;

public class DynamicSprite extends SolidSprite {
    private final int spriteSheetNumberOfColumn;
    private final int northIndex, southIndex, eastIndex, westIndex;
    protected int timeBetweenFrame = 200;
    int seed;


    public DynamicSprite(Image image, double x, double y, double width, double height, int spriteSheetNumberOfColumn,
                         int northIndex, int southIndex, int eastIndex, int westIndex) {
        super(image, x, y, width, height);
        this.spriteSheetNumberOfColumn = spriteSheetNumberOfColumn;
        this.northIndex = northIndex;
        this.southIndex = southIndex;
        this.eastIndex = eastIndex;
        this.westIndex = westIndex;

        // Initialize a random seed for animation to add variation between different NPC
        Random rand = new Random();
        seed = rand.nextInt(timeBetweenFrame) + 1;
    }

    /**
     * Determines the line number in the sprite sheet based on the current direction.
     */
    private int getFrameLineNumber() {
        return switch (direction) {
            case NORTH -> northIndex;
            case SOUTH -> southIndex;
            case EAST -> eastIndex;
            case WEST -> westIndex;
        };
    }

    @Override
    public void draw(Graphics g) {
        int index = (int) ((seed + System.currentTimeMillis() / timeBetweenFrame) % spriteSheetNumberOfColumn);

        // Draw the appropriate frame from the sprite sheet.
        g.drawImage(image,
                (int) x, (int) y,
                (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((getFrameLineNumber() + 1) * this.height),
                null);
    }
}
