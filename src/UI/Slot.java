package UI;

import Input.Mouse;
import Utilities.Draw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import static Input.Mouse.getClickLocation;

public class Slot extends Component {

    BufferedImage image = null;
    boolean dragOutClick = false;
    int dragCount = 0, dragMax = 25;

    public Slot() {}

    public Slot(BufferedImage image) {
        this.image = image;
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

        if (dragCount > dragMax) {
            dragCount = 0;
            dragOutClick = false;
            selected = false;
        } else {
            dragCount++;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        Draw.drawImage(x, y, w, h, image, g);

        if (dragOutClick)
            Draw.drawBorder(borderLcn[0], borderLcn[1], borderSize[0], borderSize[1], w, h, true, borderColor, g);
    }
}
