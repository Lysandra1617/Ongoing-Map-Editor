package Physics;

public class V2D {

    public float x = 0;
    public float y = 0;

    public V2D() {}

    public V2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(V2D v) {
        x += v.x;
        y += v.y;
    }
    public void subtract(V2D v) {
        x -= v.x;
        y -= v.y;
    }

    public void multiply(V2D v) {
        x *= v.x;
        y *= v.y;
    }
    public void divide(V2D v) {
        x /= v.x;
        y /= v.y;
    }

    public void setTo(V2D v) {
        x = v.x;
        y = v.y;
    }
    public void setTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void addTo(float amount, char direction) {
        if (direction == 'Y'|| direction == 'y') {
            y += amount;
        }
        else if (direction == 'X'|| direction == 'x') {
            x += amount;
        }
        else if (direction == 'B' || direction == 'b') {
            y += amount;
            x += amount;
        }
    }
    public void clamp(float min, float max, char direction) {
        if (direction == 'Y' || direction == 'y') {
            y = Math.max(y, min);
            y = Math.min(y, max);
        }
        else if (direction == 'X' || direction == 'x') {
            x = Math.max(x, min);
            x = Math.min(x, max);
        }
        else if (direction == 'B' || direction == 'b') {
            y = Math.max(y, min);
            y = Math.min(y, max);
            x = Math.max(x, min);
            x = Math.min(x, max);
        }
    }

    public void print() {
        System.out.print("(" + x + ", " + y + ")");

    }
    public boolean equalTo(V2D v) {
        return x == v.x && y == v.y;
    }

    public static V2D add(V2D v1, V2D v2) {

        return new V2D(v1.x + v2.x,  v1.y + v2.y);
    }
    public static V2D subtract(V2D v1, V2D v2) {

        return new V2D(v1.x - v2.x,  v1.y - v2.y);
    }

    public static V2D multiply(V2D v1, V2D v2) {

        return new V2D(v1.x * v2.x, v1.y * v2.y);
    }
    public static V2D multiply(V2D v, float scalar) {

        return new V2D(v.x * scalar, v.y * scalar);
    }

    public static V2D divide(V2D v1, V2D v2) {

        return new V2D(v1.x / v2.x, v1.y / v2.y);
    }
    public static V2D divide(V2D v, float scalar) {

        return new V2D(v.x / scalar, v.y / scalar);
    }

    public static V2D absolute(V2D v) {

        return new V2D(Math.abs(v.x), Math.abs(v.y));
    }
    public static float magnitude(V2D v) {

        return (float) Math.sqrt((v.x)*(v.x) + (v.y)*(v.y));
    }
    public static V2D normalize(V2D v) {
        float magnitudeReciprocal = 1 / magnitude(v);
        return new V2D(v.x * magnitudeReciprocal, v.y * magnitudeReciprocal);
    }

    public static void swap(V2D v1, V2D v2, char component) {
        if (component == 'X'|| component == 'x') {
            float temp = v1.x;

            v1.x = v2.x;
            v2.x = temp;
        }
        else if (component == 'Y' || component == 'y') {
            float temp = v1.y;

            v1.y = v2.y;
            v2.y = temp;
        }
    }
}
