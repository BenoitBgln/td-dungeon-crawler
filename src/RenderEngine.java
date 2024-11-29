import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList; // List of objects to render

    public RenderEngine() {
        renderList = new ArrayList<>();
    }

    public void setRenderList(ArrayList<Displayable> renderList) {
        this.renderList = renderList;
    }

    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }

    public void paint(Graphics g) {
        super.paint(g);

        ArrayList<Displayable> temp = new ArrayList<>(); // Temporary list for new objects to add

        for (Displayable renderObject : renderList) {
            // Draw the object if it is visible
            if (!(renderObject instanceof Sprite) || ((Sprite) renderObject).isVisible()) {
                renderObject.draw(g);
            }

            // Handle Player-specific logic for rendering projectiles
            if (renderObject instanceof Player) {
                ArrayList<Projectile> toRemove = new ArrayList<>();

                // Mark invisible projectiles for removal
                for (Projectile p : ((Player) renderObject).getProjectiles()) {
                    if (!p.isVisible()) {
                        toRemove.add(p);
                    }
                }

                // Remove invisible projectiles
                for (Projectile p : toRemove) {
                    ((Player) renderObject).removeProjectile(p);
                }

                // Add visible projectiles to the temporary list if not already rendered
                for (Projectile p : ((Player) renderObject).getProjectiles()) {
                    if (!renderList.contains(p) && p.isVisible()) {
                        temp.add(p);
                    }
                }
            }
        }

        renderList.addAll(temp);
    }

    @Override
    public void update() {
        this.repaint();
    }
}
