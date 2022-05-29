package Input;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static Unit.Tile.TILE_HEIGHT;
import static Unit.Tile.TILE_WIDTH;

public class Screen extends ComponentAdapter {

    //I am not adding comments here because I think it is easy to understand what is going on

    static int screenWidth = 0;
    static int screenHeight = 0;

    static int screenColumns = 0;
    static int screenRows = 0;

    public void componentResized(ComponentEvent e) {
        screenWidth = e.getComponent().getWidth();
        screenHeight = e.getComponent().getHeight();

        screenColumns = screenWidth / TILE_WIDTH;
        screenRows = screenHeight / TILE_HEIGHT;
    }

    public static int getScreenWidth() {
        return screenWidth;

    }

    public static int getScreenHeight() {
        return screenHeight;

    }

    public static int getScreenColumns() {
        return screenColumns;

    }

    public static int getScreenRows() {
        return screenRows;

    }
}
