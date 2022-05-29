package Unit;

public class Tile extends Unit {

    public final static int TILE_WIDTH = 48;
    public final static int TILE_HEIGHT = 48;

    public Tile(int id, int x, int y, int w, int h) {
        super(id, x, y, w, h);
    }

    public Tile(Tile copy) {
        super(copy.getID(), (int) copy.getPosition().x, (int) copy.getPosition().y, (int) copy.getSize().x, (int) copy.getSize().y);
    }

    public static void copy(Tile copy, Tile orig) {
        copy.ID = orig.ID;
        copy.getPosition().setTo(orig.getPosition().x, orig.getPosition().y);
        copy.getSize().setTo(orig.getSize().x, orig.getSize().y);
    }
}
