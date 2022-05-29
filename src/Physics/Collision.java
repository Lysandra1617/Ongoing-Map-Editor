package Physics;

import Unit.Body;
import Unit.Unit;
import Utilities.Pair;
import java.util.ArrayList;
import java.util.Comparator;

import static Unit.Tile.TILE_HEIGHT;
import static Unit.Tile.TILE_WIDTH;
import static Map.Map.*;
import static Physics.V2D.*;

import Main.Window;

public class Collision {

    Body tBody;

    public Collision(Body colliding) {
        this.tBody = colliding;
    }

    public void collision() {
        Unit[][] map = getMap();
        V2D contactPoint = new V2D(0, 0);
        V2D contactNormal = new V2D(0, 0);
        float[] contactTime = new float[1];
        ArrayList<Pair<Float, Integer>> collisions = new ArrayList<>();

        //Determining bounds of collision detection, instead of checking hundreds of tiles, amount of tiles checked is reduced to around 25
        int tBodyRow = (int) tBody.getPosition().y / TILE_HEIGHT;
        int tBodyCol = (int) tBody.getPosition().x / TILE_WIDTH;

        int span = 2;
        int spanEnd = span + 1;

        int startRow = Math.max(tBodyRow - span, 0);
        int endRow = Math.min(tBodyRow + spanEnd, ROWS);

        int startCol = Math.max(tBodyCol - span, 0);
        int endCol = Math.min(tBodyCol + spanEnd, COLUMNS);

        //First-proof to find all collisions
        for (int r = startRow; r < endRow; r++) {
            for (int c = startCol; c < endCol; c++) {
                if (map[r][c] == null) continue;

                if (predictedIntersection(map[r][c].getPosition(), map[r][c].getSize(), contactPoint, contactNormal, contactTime, Window.time.getTime(true))) {
                    collisions.add(new Pair<>(contactTime[0], r * COLUMNS + c));
                }
            }
        }

        //Sort the collisions by contact time
        collisions.sort(Comparator.comparing(Pair::getFirst));

        //Second-proof, handle the closest collisions first
        for (Pair<Float, Integer> pair : collisions) {
            int r = pair.getSecond() / COLUMNS;
            int c = pair.getSecond() % COLUMNS;
            if (map[r][c] == null) continue;

            if (predictedIntersection(map[r][c].getPosition(), map[r][c].getSize(), contactPoint, contactNormal, contactTime, Window.time.getTime(true))) {
                V2D absoluteVelocity = absolute(tBody.getVelocity());
                V2D adjustedVelocity = multiply(contactNormal, absoluteVelocity);
                tBody.getVelocity().add(multiply(adjustedVelocity, 1 - contactTime[0]));
            }
        }
    }

    boolean predictedIntersection(V2D obstaclePosition, V2D obstacleSize, V2D contactPoint, V2D contactNormal, float[] contactTime, float elapsedTime) {
        if (tBody.getVelocity().x == 0.0f && tBody.getVelocity().y == 0.0f) {
            return false;
        }

        V2D expObstaclePosition = (subtract(obstaclePosition, divide(tBody.getSize(), 2.0f)));
        V2D expObstacleSize = (add(obstacleSize, tBody.getSize()));

        V2D tOrigin = add(tBody.getPosition(), divide(tBody.getSize(), 2.0f));
        V2D tSlope = multiply(tBody.getVelocity(), elapsedTime);

        if (trajectoryIntersection(expObstaclePosition, expObstacleSize, tOrigin, tSlope, contactPoint, contactNormal, contactTime)) {
            //If the contact time is less than 1.0, the collision does not happen on the infinite ray
            return contactTime[0] < 1.0f;
        }

        return false;
    }

    boolean trajectoryIntersection(V2D obstaclePosition, V2D obstacleSize, V2D tOrigin, V2D tSlope, V2D contactPoint, V2D contactNormal, float[] contactTime) {

        V2D nearTime = divide(subtract(obstaclePosition, tOrigin), tSlope);
        V2D farTime = divide(subtract(add(obstaclePosition, obstacleSize), tOrigin), tSlope);

        if (Float.isNaN(nearTime.x) || Float.isNaN(nearTime.y) || Float.isNaN(farTime.x) || Float.isNaN(farTime.y)) {
            return false;
        }

        if (nearTime.x > farTime.x)
            swap(nearTime, farTime, 'x');
        if (nearTime.y > farTime.y)
            swap(nearTime, farTime, 'y');

        if (nearTime.x > farTime.y || nearTime.y > farTime.x)
            return false;

        float nearTimeHit = Math.max(nearTime.x, nearTime.y);
        contactTime[0] = nearTimeHit;

        float farTimeHit = Math.min(farTime.x, farTime.y);
        if (farTimeHit < 0 || nearTimeHit < 0)
            return false;

        contactPoint.setTo(add(tOrigin, multiply(tSlope, nearTimeHit)));

        if (nearTime.x > nearTime.y) {
            if (tSlope.x < 0)
                contactNormal.setTo(1, 0);
            else
                contactNormal.setTo(-1, 0);
        }
        else if (nearTime.x < nearTime.y) {
            if (tSlope.y < 0)
                contactNormal.setTo(0, 1);
            else
                contactNormal.setTo(0, -1);
        }

        return true;
    }
}
