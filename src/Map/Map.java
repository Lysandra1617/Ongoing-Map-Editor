package Map;

import Unit.Tile;
import Unit.Unit;

import java.util.ArrayList;
import java.util.Scanner;

import static Input.IO.scan;
import static Unit.Tile.TILE_HEIGHT;
import static Unit.Tile.TILE_WIDTH;
import Unit.Decoration;

public class Map {

    public static int ROWS = 20, COLUMNS = 30;
    static Tile[][] initTiles;
    static Tile[][] tiles;
    static char[][] charTiles;

    static ArrayList<Decoration> decorations = new ArrayList<>();

    //This function loads the map
    public static void load() {
        loadTiles();
        loadDecorations();
    }

    static void loadTiles() {
        initTiles = new Tile[ROWS][COLUMNS];
        tiles = new Tile[ROWS][COLUMNS];
        charTiles = new char[ROWS][COLUMNS];

        Scanner scan = scan("/Map/Map.txt");
        assert scan != null;

        String row;
        int index;

        //Reads the map file and fills the tiles array
        for (int r = 0; r < ROWS; r++) {

            if (scan.hasNext()) row = scan.next();
            else break;

            index = 0;

            for (int c = 0; c < COLUMNS; c++) {

                char id = row.charAt(index++);

                if (id == '-') continue; // An ID of - means nothing is there

                tiles[r][c] = new Tile(Integer.parseInt(id + ""), c * TILE_WIDTH, r * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT); // Based on the given ID, get an instance and set its position
                initTiles[r][c] = (tiles[r][c] == null) ? null : new Tile(tiles[r][c]);
            }
        }

        scan.close();
        scan = null;
    }
    static void loadDecorations() {
        Scanner scan = scan("/Map/Decorations.txt");

        int ID;
        int x, y;
        int w, h;

        if (scan == null)
            return;

        while (scan.hasNextLine()) {
            ID = scan.nextInt();
            x = scan.nextInt();
            y = scan.nextInt();
            w = scan.nextInt();
            h = scan.nextInt();

            decorations.add(new Decoration('D', ID, x, y, w, h));
        }

        scan.close();
        scan = null;
    }

    public static Tile[][] getMap() {
        return tiles;

    }

    public static char[][] getSymbMap() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                charTiles[r][c] = tiles[r][c] == null ? '-' : ((Integer.parseInt(tiles[r][c].getID() + "")) + "").charAt(0);
            }
        }
        return charTiles;
    }

    public static void resetMap() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                if (initTiles[r][c] == null && tiles[r][c] == null)
                    continue;
                else if (initTiles[r][c] == null)
                    tiles[r][c] = null;
                else if (tiles[r][c] == null)
                    tiles[r][c] = new Tile(initTiles[r][c]);
                else
                    Tile.copy(tiles[r][c], initTiles[r][c]);
            }
        }
    }

    //This function is used to find what the user actually clicked instead of where it clicked
    public static int[] convertClickLcn(int[] lcn) {
        int gameX = lcn[0] + (Camera.getSpanX() * Camera.getIntervalX());
        int gameY = lcn[1] + (Camera.getSpanY() * Camera.getIntervalY());

        return new int[]{gameY/TILE_HEIGHT, gameX/TILE_WIDTH };
    }
}
