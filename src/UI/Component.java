package UI;

import Input.Mouse;
import Input.Screen;
import Utilities.Draw;
import Utilities.Time;

import java.awt.*;

public abstract class Component {

    Time time = new Time();
    double wait = 0.25;

    boolean selected = false;

    //Main
    int x = 0;
    int y = 0;
    int w = 0;
    int h = 0;

    //Border
    boolean hasBorder = false;
    int[] borderLcn = {0, 0};
    int[] borderSize = {0, 0};
    Color borderColor = null;

    public void setMain(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    public void setBorder(int w, int h, Color color) {
        borderSize[0] = w;
        borderSize[1] = h;
        borderLcn[0] = x - w;
        borderLcn[1] = y - h;
        borderColor = color;
    }
    public void setPosition(int _x, int _y) {
        //Trying out something new because doing this.x all the time gets tiring
        x = _x;
        y = _y;
        setBorder(borderSize[0], borderSize[1], borderColor);
    }

    public boolean containsPoint(int[] lcn) {
        return lcn[0] >= x && lcn[0] <= x + w && lcn[1] >= y && lcn[1] <= y + h;
    }
    public boolean inScreenView() {
        return x >= 0 && x <= Screen.getScreenWidth() && y >= 0 && y <= Screen.getScreenHeight();
    }

    boolean on() {
        return selected;
    }
    public void update() {
        selected();
    }
    void selected() {
        if (time.lap() > wait) {
            int[] clickLcn = Mouse.getClickLocation();
            if (Mouse.clicked() && containsPoint(clickLcn)) {
                selected = true;
                time.start();
            }
            else {
                selected = false;
            }
        }
    }

    void drawBorder(Graphics2D g) {
        if (hasBorder)
            Draw.drawBorder(borderLcn[0], borderLcn[1], borderSize[0], borderSize[1], w, h, true, borderColor, g);
    }
    public abstract void draw(Graphics2D g);

}
