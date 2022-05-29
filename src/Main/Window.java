package Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Input.IO;
import Unit.Body;
import Unit.Drawable;
import Utilities.Time;
import Input.Key;
import Input.Mouse;
import Input.Screen;
import Map.*;

import UI.UI;

public class Window extends JPanel implements Runnable {

    final JFrame frame = new JFrame();
    boolean uninitializedUI = true;

    //Things for Timing
    public final static Time time = new Time();
    final Thread clock = new Thread(this);
    final int FPS = 60;

    //Objects for Input
    final Mouse mouse = new Mouse();
    final Key key = new Key();
    final Screen screen = new Screen();

    //Player(s)
    final static Body entity = new Body();

    //Constructor
    Window() {
        this.setBackground(new Color(255, 255, 255, 190));
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.addMouseMotionListener(mouse);
        this.addMouseListener(mouse);

        load();
        open();
        start();

        IO.arrayToFile(new int[][]{{0,0},{1,1}}, "src/Resources/Map/Map_Temp.txt");
    }

    //The Meat of the Program
    void load() {

        //Loading the tile images
        Drawable.load();

        try {
            //Loading the map
            Map.load();
        }
        catch (Exception e) { e.printStackTrace(); }

        //Focusing the camera
        Camera.setFocus(entity);
    }
    void open() {
        frame.getRootPane().addComponentListener(screen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(this);
    }
    void start() {
        clock.start();
        time.start();
    }
    void update() {
        time.record();
        entity.update();
        Camera.update();
        if (!uninitializedUI)
            UI.update();
    }
    public void run() {
        double interval = (double) 1000000000/FPS;
        double delta = 0;
        long now;
        long before = System.nanoTime();

        while (clock != null) {
            now = System.nanoTime();
            delta += (now - before) / interval;
            before = now;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    //The Bread of the Program
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Must initialize the UI with graphics
        if (uninitializedUI) {
            UI.initialize(g2);
            uninitializedUI = false;
        }

        Camera.draw(g2);
        UI.draw(g2);
    }

    public static Body getMainEntity() {
        return entity;
    }
}
