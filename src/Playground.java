import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Playground {
    private final ArrayList<Sprite> environment = new ArrayList<>(); // List of all sprites in the environment
    private final ArrayList<int[]> availablePositions = new ArrayList<>(); // List of available positions for movement

    /**
     * @param pathName The file path of the map to load.
     */
    public Playground(String pathName) {
        try {
            // Load images for various sprites
            final Image imageTree = ImageIO.read(new File("./img/tree32.png"));
            final Image imageGrass = ImageIO.read(new File("./img/grass32.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock32.png"));
            final Image imageExit = ImageIO.read(new File("./img/rainbow.png"));

            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);
            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);
            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);
            final int imageExitWidth = imageExit.getWidth(null);
            final int imageExitHeight = imageExit.getHeight(null);

            // Read the map file line by line
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;

            while (line != null) {
                int columnNumber = 0; // Track column for sprite placement

                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    // Map symbols to sprites
                    switch (element) {
                        case 'T': // Tree
                            environment.add(new SolidSprite(
                                    imageTree,
                                    columnNumber * imageTreeWidth,
                                    lineNumber * imageTreeHeight,
                                    imageTreeWidth,
                                    imageTreeHeight
                            ));
                            break;
                        case 'E': // Exit (solid to avoid villains exit the map)
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new SolidSprite(
                                    imageExit,
                                    columnNumber * imageExitWidth,
                                    lineNumber * imageExitHeight,
                                    imageExitWidth,
                                    imageExitHeight,
                                    true
                            ));
                            break;
                        case ' ': // Empty space (walkable)
                            availablePositions.add(new int[]{
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight
                            });
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            break;
                        case 'R': // Rock
                            environment.add(new SolidSprite(
                                    imageRock,
                                    columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight,
                                    imageRockWidth,
                                    imageRockHeight
                            ));
                            break;
                    }
                    columnNumber++;
                }
                lineNumber++;
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return A list of solid sprites in the environment.
     */
    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) {
                solidSpriteArrayList.add(sprite);
            }
        }
        return solidSpriteArrayList;
    }

    /**
     * @return A list of available positions where entities can move as (x, y) coordinate arrays.
     */
    public ArrayList<int[]> getAvailablePositions() {
        return availablePositions;
    }

    /**
     * @return A list of displayable objects in the environment.
     */
    public ArrayList<Displayable> getSpriteList() {
        return new ArrayList<>(environment);
    }
}
