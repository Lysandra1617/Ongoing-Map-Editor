package Input;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
    static boolean clicked = false;

    final static int[] CLICK_LOCATION = new int[2];
    final static int[] HOVER_LOCATION = new int[2];

    //Returns [X, Y] of last click
    public static int[] getClickLocation() {
        return CLICK_LOCATION;

    }

    //Returns [X, Y] of location of cursor
    public static int[] getHoverLocation() {
        return HOVER_LOCATION;

    }

    //If the user clicked, this should return true and false if otherwise
    public static boolean clicked() {
        return clicked;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        CLICK_LOCATION[0] = e.getX();
        CLICK_LOCATION[1] = e.getY();
        clicked = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        HOVER_LOCATION[0] = e.getX();
        HOVER_LOCATION[1] = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        CLICK_LOCATION[0] = e.getX();
        CLICK_LOCATION[1] = e.getY();
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = true;
    }

    //Not implemented
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
}