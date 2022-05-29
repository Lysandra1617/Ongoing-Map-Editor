package Unit;

import java.awt.Graphics2D;
import java.awt.Color;

import static Utilities.Draw.drawRectangle;
import static Input.Key.*;
import static Physics.V2D.*;

import Physics.Acceleration;
import Physics.Collision;
import Physics.V2D;
import Main.Window;

public class Body implements Spatial, Drawable {

    Acceleration acceleration = new Acceleration(this);
    Collision collision = new Collision(this);

    V2D size = new V2D(30, 40);
    V2D position = new V2D(0, 0);
    V2D velocity = new V2D(0, 0);

    public void update() {

        //Handling user input
        if (L) velocity.x -= 10;
        else if (R) velocity.x += 10;
        if (U) velocity.y -= 10;
        else if (D) velocity.y += 10;

        //Handling the consequences of user input
        acceleration.gravity();
        acceleration.friction();
        collision.collision();
        velocity.clamp(-200, 200, 'x');
        position = add(position, multiply(velocity, Window.time.getTime(true)));

    }

    public void draw(Graphics2D g, int x, int y) {
        drawRectangle(x, y, (int) size.x, (int) size.y, true, new Color(71, 116, 215), g);
    }

    public V2D getSize() {
        return size;

    }

    public V2D getPosition() {
        return position;

    }

    public V2D getVelocity() {
        return velocity;

    }
}
