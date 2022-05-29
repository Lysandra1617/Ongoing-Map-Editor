package Map;

import Unit.*;
import Input.Screen;
import Physics.V2D;

import java.awt.*;

import static Input.Key.GRID;
import static Input.Screen.getScreenHeight;
import static Input.Screen.getScreenWidth;
import static Unit.Tile.TILE_HEIGHT;
import static Unit.Tile.TILE_WIDTH;
import static Map.Map.*;
import static Utilities.Draw.drawLine;

public class Camera {

    static Body focus;
    static V2D focusPosition;

    //Think of xSpan and ySpan as the respective measurements of the screen. xSpan would measure the width and ySpan would measure the height
    static int xSpan = 0, ySpan = 0;

    //Think of xInterval and yInterval as a category pointing to which section the focus is located.
    //If you have a 30-inch ruler and divide it into groups of 3 inches, you have 10 intervals.
    //So depending on where the focus is located, the interval will basically say what section of the map the character is at. 0 is at the top or the left of the map,
    //and it increases as the focus moves along
    static int xInterval = 0, yInterval = 0;

    //Think of xMinPos, xMaxPos, yMinPos, and yMaxPos as integers defining the boundary of an interval. Going back to the previous example, the first
    //interval would go from 0 inches to 3 inches, and the second interval would go from 3 inches to 6 inches, and so on. This is how these four variables work.
    //Instead of giving just a number to which interval the focus is in, we can also determine the boundary of a specific interval.
    static int xMinPos = 0, yMinPos = 0;
    static int xMaxPos = 0, yMaxPos = 0;



    //Generic update function
    public static void update() {
        updateInterval();
        updateCameraPosition();
    }

    //This function updates the current interval whenever the focus is out of the camera's view
    static void updateInterval() {
        updateIntervalBoundary();

        int xFocus = (int) focus.getPosition().x;
        int yFocus = (int) focus.getPosition().y;

        //If outside the current interval, the current interval must be updated
        xSpan = (xSpan == 0) ? 1 : 0;
        ySpan = (ySpan == 0) ? 1 : 0;

        if (xFocus >= xMaxPos || xFocus <= xMinPos && xSpan != 0)
            xInterval = xFocus / xSpan;
        if (yFocus >= yMaxPos || yFocus <= yMinPos && ySpan != 0)
            yInterval = yFocus / ySpan;

    }

    //This function evaluates the boundaries of the interval
    static void updateIntervalBoundary() {

        xSpan = Screen.getScreenWidth();
        xMinPos = xSpan * xInterval;
        xMaxPos = xSpan * (xInterval + 1);

        ySpan = Screen.getScreenHeight();
        yMinPos = ySpan * yInterval;
        yMaxPos = ySpan * (yInterval + 1);
    }

    //This function updates the camera position which basically just judges where the focus is on the screen and not the map
    static void updateCameraPosition() {

        if (focus.getPosition() != null)
            //I could have just used the subtract function that comes with the V2D class, but I would have to keep making a new Vector which might slow the program down
            focusPosition.setTo(focus.getPosition().x - xMinPos, focus.getPosition().y - yMinPos);
    }

    //This function...say it with me...draws!
    public static void draw(Graphics2D g) {

        //Draw the map
        for (int i = 0; i < ROWS; i++) {

            for (int j = 0; j < COLUMNS;j++) {

                Unit block = getMap()[i][j];

                if (block != null)
                    if (inCameraView(block.getPosition()))
                        draw(block, g);

                //Draws a grid
                if (GRID) {
                    drawLine(j * TILE_WIDTH, i * TILE_HEIGHT, j * TILE_WIDTH, getScreenHeight(), Color.BLACK, g);
                    drawLine(j * TILE_WIDTH + TILE_WIDTH, 0, j * TILE_WIDTH + TILE_WIDTH, getScreenHeight(), Color.BLACK, g);

                    drawLine(0, i * TILE_HEIGHT, getScreenWidth(), i * TILE_HEIGHT, Color.BLACK, g);
                    drawLine(0, i * TILE_HEIGHT + TILE_HEIGHT, getScreenWidth(), i * TILE_HEIGHT + TILE_HEIGHT, Color.BLACK, g);
                }
            }
        }

        //Draw the decorations
        for (Decoration decor : decorations) {
            if (decor != null)
                if (inCameraView(decor.getPosition()))
                draw(decor, g);
        }

        //Draw the focus
        focus.draw(g, (int) focusPosition.x, (int) focusPosition.y);
    }

    //This function returns a boolean value judging whether the given position can be seen by the camera (if it is in the interval)
    static boolean inCameraView(V2D position) {
        return position.x >= xMinPos - TILE_WIDTH && position.x <= xMaxPos && position.y >= yMinPos - TILE_HEIGHT && position.y <= yMaxPos;

    }

    //This function...say it with me one more time...draws!
    static void draw(Unit object, Graphics2D g) {

        //Maps the object to its respective position on the screen
        int x = (int) (object.getPosition().x - xMinPos);
        int y = (int) (object.getPosition().y - yMinPos);

        object.draw(g, x, y);
    }

    //Sets the focus
    public static void setFocus(Body body) {

        focus = body;
        focusPosition = new V2D(0, 0);
        if (focus.getPosition() != null) focusPosition.setTo(focus.getPosition());

        updateIntervalBoundary();
    }

    //I am not going to comment on these functions because you can read the name of the function to understand it
    public static int getIntervalX() {
        return xInterval;
    }
    public static int getIntervalY() {
        return yInterval;
    }
    public static int getSpanX() {
        return xSpan;
    }
    public static int getSpanY() {
        return ySpan;
    }

}
