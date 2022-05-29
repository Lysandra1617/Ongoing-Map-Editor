package Utilities;
import Physics.V2D;

import java.awt.*;

public abstract class Draw {
    static final Color DEFAULT_COLOR = new Color(0, 0, 0);
    static final Font DEFAULT_FONT = new Font("MONOSPACE", Font.BOLD, 12);

    public static void drawLine(V2D start, V2D end, Color color, Graphics2D g) {
        if (color != null)
            g.setColor(color);

        g.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);

        if (color != null)
            g.setColor(DEFAULT_COLOR);
    }
    public static void drawLine(int x1, int y1, int x2, int y2, Color color, Graphics2D g) {
        if (color != null)
            g.setColor(color);

        g.drawLine(x1, y1, x2, y2);

        if (color != null)
            g.setColor(DEFAULT_COLOR);
    }
    public static void drawRectangle(int x, int y, int w, int h, boolean fill, Color color, Graphics2D g) {
        if (fill) {
            g.setColor(color);
            g.fillRect(x, y, w, h);
        }
        else {
            g.drawRect(x, y, w, h);
        }

        g.setColor(DEFAULT_COLOR);
    }
    public static void drawImage(int x, int y, int w, int h, Image image, Graphics2D g) {
        g.drawImage(image, x, y, w, h, null);

    }
    public static void drawString(String text, int x, int y, Font font, Color color, Graphics2D g) {
        if (font != null)
            g.setFont(font);

        if (color != null)
            g.setColor(color);

        g.drawString(text, x, y);

        if (font != null)
            g.setFont(DEFAULT_FONT);

        if (color != null)
            g.setColor(DEFAULT_COLOR);
    }
    public static void drawBorder(int x, int y, int wBorder, int hBorder, int wMain, int hMain, boolean fill, Color color, Graphics2D g) {
        drawRectangle(x, y, wMain + wBorder * 2, hBorder, fill, color, g);
        drawRectangle(x, y, wBorder, hMain + hBorder * 2, fill, color, g);
        drawRectangle(x, y + hBorder + hMain, wMain + wBorder * 2, hBorder, fill, color, g);
        drawRectangle(x + wBorder + wMain, y, wBorder, hMain + hBorder * 2, fill, color, g);
    }
}
