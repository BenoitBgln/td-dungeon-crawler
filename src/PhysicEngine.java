import java.util.ArrayList;

public class PhysicEngine implements Engine {
    private final ArrayList<SolidSprite> movingSpriteList = new ArrayList<>(); // Sprites that can move
    private ArrayList<Sprite> environment; // The game environment containing all sprites

    @Override
    public void update() {
        ArrayList<Sprite> addToEnv = new ArrayList<>(); // Sprites to be added to the environment
        ArrayList<Sprite> removeToEnv = new ArrayList<>(); // Sprites to be removed from the environment

        // Remove invisible sprites from the environment and from the moving sprite list
        for (Sprite s : environment) {
            if (!s.isVisible()) {
                removeToEnv.add(s);
                movingSpriteList.remove((SolidSprite) s);
            }
        }

        // Handle movement and projectiles for each sprite in the environment
        for (Sprite s : environment) {
            if (s instanceof SolidSprite && movingSpriteList.contains(s)) {
                ((SolidSprite) s).moveIfPossible(environment);
            }
            if (s instanceof Player) {
                // Add new projectiles from the Player to the environment and movingSpriteList
                for (Projectile p : ((Player) s).getProjectiles()) {
                    if (!environment.contains(p)) {
                        addToEnv.add(p);
                        movingSpriteList.add(p);
                    }
                }
            }
        }

        environment.addAll(addToEnv);
        environment.removeAll(removeToEnv);
    }

    public void addToMovingSpriteList(DynamicSprite movingSprite) {
        movingSpriteList.add(movingSprite);
    }

    public void setEnvironment(ArrayList<Sprite> environment) {
        this.environment = environment;
    }

    public void addToEnvironment(Sprite s) {
        this.environment.add(s);
    }
}
