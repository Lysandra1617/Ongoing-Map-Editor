package UI;

import Input.IO;
import Input.Key;
import Input.Mouse;
import Input.Screen;
import Map.Edit;
import Map.Map;
import Unit.Drawable;

import java.awt.*;
import java.util.ArrayList;

import static UI.Button.turnOff;
import static UI.Button.turnTo;

public class UI {

    //Colors
    static final Color BLACK = Color.BLACK;
    static final Color WHITE = Color.WHITE;
    static final Color LIGHT_GRAY = new Color(229, 229, 229);
    static final Color DARK_GRAY = new Color(62, 62, 62);

    //Border Dimensions
    static final int BORDER_HEIGHT = 1;
    static final int BORDER_WIDTH = 1;

    //Font Tid-Bits
    static final int TEXT_SIZE = 12;

    //Buttons
    final static Button ADD_TILE_BUTTON = new Button();
    final static Button DELETE_TILE_BUTTON = new Button();
    final static Button SAVE_MAP_BUTTON = new Button();
    final static Button RESET_MAP_BUTTON = new Button();
    final static Button ADD_DECOR_BUTTON = new Button();
    static ArrayList<Button> buttons = new ArrayList<>();
    static ArrayList<Component> components = new ArrayList<>();

    //Slider
    static Slider slide = new Slider(Drawable.TILE_IMAGES.size());
    static Slider decorSlide = new Slider(Drawable.DECORATION_IMAGES.size());

    public static void initialize(Graphics2D g) {
        ADD_TILE_BUTTON.setMain(10, 10, 100, 25);
        ADD_TILE_BUTTON.setMainColor(LIGHT_GRAY);
        ADD_TILE_BUTTON.setBorder(BORDER_WIDTH, BORDER_HEIGHT, BLACK);
        ADD_TILE_BUTTON.setText("Add Tile", DARK_GRAY, TEXT_SIZE, g);
        ADD_TILE_BUTTON.setClickedDesign(new Color(99, 220, 147, 221), new Color(0, 0, 0, 0), WHITE);
        ADD_TILE_BUTTON.setSticks(true);
        ADD_TILE_BUTTON.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        DELETE_TILE_BUTTON.setMain(150, 10, 100, 25);
        DELETE_TILE_BUTTON.setMainColor(LIGHT_GRAY);
        DELETE_TILE_BUTTON.setBorder(BORDER_WIDTH, BORDER_HEIGHT, BLACK);
        DELETE_TILE_BUTTON.setText("Delete Tile", DARK_GRAY, TEXT_SIZE, g);
        DELETE_TILE_BUTTON.setClickedDesign(new Color(99, 220, 147, 221), new Color(0, 0, 0, 0), WHITE);
        DELETE_TILE_BUTTON.setSticks(true);
        DELETE_TILE_BUTTON.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

        SAVE_MAP_BUTTON.setMain(290, 10, 100, 25);
        SAVE_MAP_BUTTON.setMainColor(LIGHT_GRAY);
        SAVE_MAP_BUTTON.setBorder(BORDER_WIDTH, BORDER_HEIGHT, BLACK);
        SAVE_MAP_BUTTON.setText("Save", DARK_GRAY, TEXT_SIZE, g);
        SAVE_MAP_BUTTON.setClickedDesign(new Color(99, 220, 147, 221), new Color(0, 0, 0, 0), WHITE);
        SAVE_MAP_BUTTON.setSticks(true);
        SAVE_MAP_BUTTON.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        SAVE_MAP_BUTTON.setSticks(false);

        RESET_MAP_BUTTON.setMain(430, 10, 100, 25);
        RESET_MAP_BUTTON.setMainColor(LIGHT_GRAY);
        RESET_MAP_BUTTON.setBorder(BORDER_WIDTH, BORDER_HEIGHT, BLACK);
        RESET_MAP_BUTTON.setText("Reset", DARK_GRAY, TEXT_SIZE, g);
        RESET_MAP_BUTTON.setClickedDesign(new Color(99, 220, 147, 221), new Color(0, 0, 0, 0), WHITE);
        RESET_MAP_BUTTON.setSticks(true);
        RESET_MAP_BUTTON.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        RESET_MAP_BUTTON.setSticks(false);

        ADD_DECOR_BUTTON.setMain(570, 10, 100, 25);
        ADD_DECOR_BUTTON.setMainColor(LIGHT_GRAY);
        ADD_DECOR_BUTTON.setBorder(BORDER_WIDTH, BORDER_HEIGHT, BLACK);
        ADD_DECOR_BUTTON.setText("Add Decor", DARK_GRAY, TEXT_SIZE, g);
        ADD_DECOR_BUTTON.setClickedDesign(new Color(99, 220, 147, 221), new Color(0, 0, 0, 0), WHITE);
        ADD_DECOR_BUTTON.setSticks(true);
        ADD_DECOR_BUTTON.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        ADD_DECOR_BUTTON.setSticks(false);

        buttons.add(ADD_TILE_BUTTON);
        buttons.add(DELETE_TILE_BUTTON);
        buttons.add(SAVE_MAP_BUTTON);
        buttons.add(RESET_MAP_BUTTON);
        buttons.add(ADD_DECOR_BUTTON);

        slide.setMain(0, 0, Screen.getScreenWidth(), 200);
        slide.setBgColor(BLACK);
        slide.setButtons();
        slide.loadImages(Drawable.TILE_IMAGES);

        decorSlide.setMain(0, 0, Screen.getScreenWidth(), 200);
        decorSlide.setBgColor(BLACK);
        decorSlide.setButtons();
        decorSlide.loadImages(Drawable.DECORATION_IMAGES);

        components.add(ADD_TILE_BUTTON);
        components.add(DELETE_TILE_BUTTON);
        components.add(SAVE_MAP_BUTTON);
        components.add(RESET_MAP_BUTTON);
        components.add(slide);
        components.add(decorSlide);
        components.add(ADD_DECOR_BUTTON);
    }

    public static void update() {
        ADD_TILE_BUTTON.update();
        DELETE_TILE_BUTTON.update();
        SAVE_MAP_BUTTON.update();
        RESET_MAP_BUTTON.update();
        ADD_DECOR_BUTTON.update();
        slide.update();
        decorSlide.update();


        if (ADD_TILE_BUTTON.on()) {
            turnOff(buttons, ADD_TILE_BUTTON);
        }
        if (DELETE_TILE_BUTTON.on()) {
            turnOff(buttons, DELETE_TILE_BUTTON);
        }

        if (ADD_TILE_BUTTON.on()) {
            slide.selected = true;

            Integer index = slide.getChosen();
            if (index != null)
                Edit.insertTile(index, Mouse.getClickLocation());

        }
        //@FIXME: If you ever continue with this project, you left off here
        else if (ADD_DECOR_BUTTON.on()) {
            decorSlide.selected = true;
            if (Key.DEBUG) {
                System.out.print('o');
            }
            Integer index = decorSlide.getChosen();
            if (index != null) {
                Edit.insertDecor(index, Mouse.getClickLocation());
            }

        }
        else if (DELETE_TILE_BUTTON.on()) {
            Edit.deleteTile(Mouse.getClickLocation());
        }
        else if (SAVE_MAP_BUTTON.on()) {
            IO.arrayToFile(Map.getSymbMap(), "src/Resources/Map/Map_Temp.txt");
            IO.renameFile("src/Resources/Map/Map_Temp.txt", "src/Resources/Map/Map_1.txt");
            IO.renameFile("src/Resources/Map/Map.txt", "src/Resources/Map/Map_Temp.txt");
            IO.renameFile("src/Resources/Map/Map_1.txt", "src/Resources/Map/Map.txt");
        }
        else if (RESET_MAP_BUTTON.on()) {
            Map.resetMap();
        }
    }


    public static void draw(Graphics2D g) {
        DELETE_TILE_BUTTON.draw(g);
        ADD_TILE_BUTTON.draw(g);
        SAVE_MAP_BUTTON.draw(g);
        RESET_MAP_BUTTON.draw(g);
        ADD_DECOR_BUTTON.draw(g);
        slide.draw(g);
        decorSlide.draw(g);
    }

    public static ArrayList<Button> getButtons() {
        return buttons;
    }
    public static ArrayList<Component> getComponents() {
        return components;
    }
}
