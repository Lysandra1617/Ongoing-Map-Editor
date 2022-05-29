package Map;

import Input.Screen;
import Main.Window;
import UI.Button;
import UI.Component;
import Unit.Decoration;
import Unit.Tile;

import java.util.ArrayList;

import static Map.Map.decorations;
import static Map.Map.getMap;
import static UI.UI.getButtons;
import static UI.UI.getComponents;
import static Unit.Tile.TILE_HEIGHT;
import static Unit.Tile.TILE_WIDTH;


public class Edit {

    public static void insertTile(int ID, int[] clickLcn) {
        //DON'T INSERT A TILE IF:
        //The RC does not exist.
        //The player is there.
        //There's a tile already there that is of the same type.
        //A button is where the user clicked.
        boolean rcExists = rcExists(clickLcn);
        if (rcExists) {
            int[] RC = Map.convertClickLcn(clickLcn);

            boolean notClickingComponents = notClickingComponent(clickLcn);
            boolean notClickingPlayer = notClickingPlayer(clickLcn);
            boolean notIdenticalTile = notIdenticalTile(ID, clickLcn);

            if (notClickingComponents && notClickingPlayer && notIdenticalTile) {
                getMap()[RC[0]][RC[1]] = new Tile(ID, RC[1] * TILE_WIDTH, RC[0] * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
            }
        }
    }

    public static void insertDecor(int ID, int[] clickLcn) {
        boolean rcExists = rcExists(clickLcn);
        if (rcExists) {
            int[] RC = Map.convertClickLcn(clickLcn);

            boolean notClickingComponents = notClickingComponent(clickLcn);
            boolean notClickingPlayer = notClickingPlayer(clickLcn);
            boolean notIdenticalTile = notIdenticalTile(ID, clickLcn);

            if (notClickingComponents && notClickingPlayer && notIdenticalTile) {
                decorations.add(new Decoration('D', ID, RC[1] * TILE_WIDTH, RC[0] * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT));
            }
        }
    }

    public static void deleteTile(int[] clickLcn) {
        boolean rcExists = rcExists(clickLcn);

        if (rcExists) {
            int[] RC = Map.convertClickLcn(clickLcn);

            boolean notClickingComponents = notClickingComponent(clickLcn);
            boolean notClickingPlayer = notClickingPlayer(clickLcn);
            boolean notNull = notNull(clickLcn);

            if (notClickingComponents && notClickingPlayer && notNull) {
                getMap()[RC[0]][RC[1]] = null;
            }
        }
    }

    static boolean notClickingButtons(int[] clickLcn) {
        ArrayList<Button> buttons = getButtons();
        for (Button btn : buttons) {
            if (btn.containsPoint(clickLcn))
                return false;
        }
        return true;
    }

    static boolean notClickingComponent(int[] clickLcn) {
        ArrayList<Component> components = getComponents();
        for (Component cmp : components) {
            if (cmp.containsPoint(clickLcn))
                return false;
        }
        return true;
    }

    static boolean notClickingPlayer(int[] clickLcn) {
        int entityC = (int) Window.getMainEntity().getPosition().x / Tile.TILE_WIDTH;
        int entityR = (int) Window.getMainEntity().getPosition().y / Tile.TILE_HEIGHT;
        int[] clickLcnRC = Map.convertClickLcn(clickLcn);
        return entityR != clickLcnRC[0] || entityC != clickLcnRC[1];
    }

    static boolean notIdenticalTile(int ID, int[] clickLcn) {
        int[] clickLcnRC = Map.convertClickLcn(clickLcn);
        if (Map.getMap()[clickLcnRC[0]][clickLcnRC[1]] == null) return true;
        return Map.getMap()[clickLcnRC[0]][clickLcnRC[1]].getID() != ID;
    }

    static boolean notNull(int[] clickLcn) {
        int[] clickLcnRC = Map.convertClickLcn(clickLcn);
        return Map.getMap()[clickLcnRC[0]][clickLcnRC[1]] != null;
    }

    static boolean rcExists(int[] clickLcn) {
        int[] clickLcnRC = Map.convertClickLcn(clickLcn);
        return (clickLcnRC[0] >= 0 && clickLcnRC[0] < Screen.getScreenRows() && clickLcnRC[1] >= 0 && clickLcnRC[1] < Screen.getScreenColumns());
    }
}
