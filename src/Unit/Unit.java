package Unit;

import Physics.V2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Unit implements Spatial, Drawable {

    BufferedImage image;
    V2D position;
    V2D size;
    int ID;

    public Unit(int id, int x, int y, int w, int h) {
        ID = id;
        image = Drawable.TILE_IMAGES.get(ID);
        position = new V2D(x, y);
        size = new V2D(w, h);
    }

    public Unit(char type, int id, int x, int y, int w, int h) {
        ID = id;
        if (type == 'D')
            image = Drawable.DECORATION_IMAGES.get(ID);
        else
            image = Drawable.TILE_IMAGES.get(ID);
        position = new V2D(x, y);
        size = new V2D(w, h);
    }
    public void draw(Graphics2D g, int x, int y) {
        Drawable.draw(image, x, y, size.x, size.y, g);
    }
    public V2D getSize() {
        return size;
    }
    public V2D getPosition() {
        return position;
    }
    public int getID() {
        return ID;
    }
}
