package Physics;

import Unit.Body;

import static Input.Key.L;
import static Input.Key.R;

public class Acceleration {

    Body body;

    V2D time = new V2D(0.0f,0.0f);

    final float g = 9.0f; // Gravity
    final float mu = 0.5f; // Friction

    public Acceleration(Body body) {
        this.body = body;
    }

    public void gravity() {
        if (body.getVelocity().y == 0.0f)
            time.y = 0.1f;
        else
            time.y += 0.15f;

        body.getVelocity().y += g * time.y;
    }

    public void friction() {
        //If there's no external forces and the object is moving, enact friction
        if (!L && !R && body.getVelocity().x != 0.0f) {

            float a = mu * g;

            if (body.getVelocity().x > 0) a *= -1; //Friction acts in opposite direction of motion

            float dV = a * time.x; //Finding change in velocity

            //If added friction makes object move in opposite direction, cancel out object's velocity
            if (time.x != 0.0f && Math.abs(dV) > Math.abs(body.getVelocity().x)) {
                dV = body.getVelocity().x * -1;
            }

            body.getVelocity().x += dV;
            time.x += 0.15f;
        }
        else {
            time.x = 0.0f;
        }
    }
}
