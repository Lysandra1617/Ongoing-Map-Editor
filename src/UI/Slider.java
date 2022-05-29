package UI;

import Input.Screen;
import Unit.Drawable;
import Unit.Tile;
import Utilities.Draw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Slider extends Component {

    Color bgColor = null;

    //Slot-ty stuff
    Slot[] slots;
    int wSlot = Tile.TILE_WIDTH;
    int hSlot = Tile.TILE_HEIGHT;
    int xSlotSpacing = 5; //Distance in between the slots
    int ySlotSliderSpacing = 50; //Distance from the top of the slider to the top of the slots
    Color borderColorSlot = Color.YELLOW;
    int[] borderSizeSlot = {2, 2};

    //Go through slider
    final Button LEFT = new Button();
    final Button RIGHT = new Button();
    final Button CLOSE = new Button();
    int currentIndex = 0;

    //What's chosen
    Integer chosen = null;

    public Slider() {
        slots = new Slot[0];
    }

    public Slider(int numberSlots) {
        slots = new Slot[numberSlots];
    }

    public void setButtons() {
        LEFT.setMain(x + 20, y + h - h/4, 20, 20);
        LEFT.setBorder(1, 1, Color.WHITE);
        LEFT.setMainColor(Color.WHITE);
        LEFT.setClickedDesign(Color.BLUE, Color.BLUE, Color.WHITE);

        RIGHT.setMain(x + w - 40, y + h - h/4, 20, 20);
        RIGHT.setBorder(1, 1, Color.WHITE);
        RIGHT.setMainColor(Color.WHITE);
        RIGHT.setClickedDesign(Color.BLUE, Color.BLUE, Color.WHITE);

        CLOSE.setMain(x + 20, y + 15, 20, 20);
        CLOSE.setBorder(0, 0, null);
        CLOSE.setMainColor(Color.RED);
        CLOSE.setClickedDesign(Color.BLUE, Color.BLUE, Color.WHITE);
    }

    //Must be a hash map with consecutive integers. Probably a better way to go about this, not going to lie
    public void loadImages(HashMap<Integer, BufferedImage> images) {
        slots = new Slot[images.size()];
        for (int i = 0; i < slots.length; i++) {
            setSlot(i, images.get(i));
        }
    }

    public void setSlot(int i, BufferedImage image) {
        if (validIndex(i, slots.length)) {
            slots[i] = new Slot(image);
            slots[i].setMain((i - currentIndex) * wSlot + xSlotSpacing * i, y + ySlotSliderSpacing, wSlot, hSlot);
            slots[i].setBorder(borderSizeSlot[0], borderSizeSlot[1], borderColorSlot);
        }
    }

    void repositionSlots() {
        for (int i = 0; i < slots.length; i++) {
            slots[i].setPosition((i - currentIndex) * wSlot + xSlotSpacing * i, y + ySlotSliderSpacing);
        }
    }

    public void setBgColor(Color color) {
        bgColor = color;
    }

    public void update() {
        for (int i = 0; i < slots.length; i++) {
            slots[i].update();
            if (slots[i].on()) {
                chosen = i;
            }
        }

        LEFT.update();
        RIGHT.update();
        CLOSE.update();

        if (LEFT.on()) {
            if (validIndex(currentIndex - 1, slots.length)) {
                currentIndex--;
                repositionSlots();
            }
        }
        else if (RIGHT.on()) {
            if (validIndex(currentIndex + 1, slots.length)) {
                currentIndex++;
                repositionSlots();
            }
        }
        else if (CLOSE.on()) {
            selected = false;
            currentIndex = 0;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            Draw.drawRectangle(x, y, Screen.getScreenWidth(), h, true, bgColor, g);

            for (Slot slot : slots) {
                slot.draw(g);
            }

            LEFT.draw(g);
            RIGHT.draw(g);
            CLOSE.draw(g);
        }
    }

    @Override
    public void selected() {

    }


    static boolean validIndex(int index, int size) {
        return index >= 0 && index < size;
    }

    public Integer getChosen() {
        return chosen;
    }
}
