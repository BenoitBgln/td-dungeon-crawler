import java.awt.*;

public class Sprite implements Displayable {
    protected Image image;
    protected final double width, height;
    protected double x, y;
    protected boolean visible = true;  // Visibility status of the sprite

    public Sprite(Image image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void delete() {
        setVisible(false);
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            g.drawImage(this.image, (int)this.x, (int)this.y, (int)this.width, (int)this.height, null);
        }
    }
}
