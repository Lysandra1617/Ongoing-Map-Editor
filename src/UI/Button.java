package UI;

import Input.Key;
import Input.Mouse;
import Utilities.Draw;
import Utilities.Format;

import java.awt.*;
import java.util.ArrayList;

import static Input.Mouse.getClickLocation;
import static Utilities.Format.stringHeight;
import static Utilities.Format.stringWidth;

public class Button extends Component {
    //Implementation
    boolean sticks = false;
    boolean dragOutClick = false;
    int dragCount = 0, dragMax = 25;

    //Main
    Color mainColor = null;

    //Text
    String text = "";
    Color fontColor = null;
    Font font = null;
    int fontX = 0, fontY = 0, fontSize = 0;

    //Clicked Design
    boolean beingClicked = false;
    Color clickedMainColor = null, clickBorderColor = null, clickFontColor;

    //Drawing
    @Override
    public void draw(Graphics2D g) {
        if (selected || (dragOutClick && !sticks)) {
            drawButton(g, clickedMainColor, clickBorderColor, clickFontColor);
            beingClicked = true;
        }
        else {
            drawButton(g, mainColor, borderColor, fontColor);
            beingClicked = false;
        }
    }
    void drawButton(Graphics2D g, Color mainColor, Color borderColor, Color fontColor) {
        Draw.drawRectangle(x, y, w, h, true, mainColor, g);
        Draw.drawBorder(borderLcn[0], borderLcn[1], borderSize[0], borderSize[1], w, h,true, borderColor, g);
        if (!text.isEmpty())
            Draw.drawString(text, fontX, fontY, font, fontColor, g);
    }

    //Setting button designs, implementation, and text
    public void setClickedDesign(Color main, Color border, Color font) {
        clickedMainColor = main;
        clickBorderColor = border;
        clickFontColor = font;
    }
    public void setText(String text, Color color, int size, Graphics2D g) {
        this.text = text;
        fontColor = color;
        fontSize = size;
        fontX = Format.getCenteredX(x, w, stringWidth(text, g));
        fontY = Format.getCenteredY(y, h, stringHeight(g));
    }
    public void setText(String text, Color color, int size) {
        this.text = text;
        fontColor = color;
        fontSize = size;
        fontX = x + w/2;
        fontY = y + h/2;
    }
    public void setFont(Font font) {
        this.font = font;
    }
    public void setMainColor(Color color){
        mainColor = color;
    }
    public void setSticks(boolean stick) {
        sticks = stick;
    }

    @Override
    void selected() {
        if (time.lap() / 1e9 > wait) {
            if (Mouse.clicked() && containsPoint(getClickLocation())) {
                selected = !selected;
                dragOutClick = true;
                time.start();
            }
        }
        else if (selected && !sticks) {
            selected = false;
        }

        if (!sticks) {
            if (dragCount > dragMax) {
                dragCount = 0;
                dragOutClick = false;
            } else {
                dragCount++;
            }
        }
    }

    //Turning buttons on and off
    void turnTo(boolean state) {
        selected = state;
    }
    //Given an array of buttons, turn them all to the boolean state value, except for the Button exception
    static void turnTo(ArrayList<Button> buttons, Button exception, boolean state) {
        for (Button button : buttons) {
            if (button != exception)
                button.turnTo(state);
        }
    }
    //Given an arrayList of buttons, turn them all to the boolean state value, except for the Button exception and the buttons that are not turned on yet
    static void turnOff(ArrayList<Button> buttons, Button exception) {
        for (Button button : buttons) {
            if (button != exception && button.beingClicked)
                button.turnTo(false);
        }
    }

}
