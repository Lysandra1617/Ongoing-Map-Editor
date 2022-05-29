package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener {

    public static boolean L, R, U, D, SPACE, DEBUG, GRID;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case 37 -> L = true;
            case 39 -> R = true;
            case 38 -> U = true;
            case 40 -> D = true;
            case 32 -> SPACE = true;
            case 17 -> DEBUG = !DEBUG;
            case 71 -> GRID = !GRID;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case 37 -> L = false;
            case 39 -> R = false;
            case 38 -> U = false;
            case 40 -> D = false;
            case 32 -> SPACE = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
