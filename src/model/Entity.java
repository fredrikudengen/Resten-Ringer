package model;

import java.awt.Rectangle;
import java.io.IOException;

public abstract class Entity {

    /** Shared direction enum for movement and projectile firing. */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected int x;
    protected int y;
    protected int speed;
    protected int healthPoints;
    protected int imageNum     = 1;
    protected int imageCounter = 0;

    protected boolean   isAlive;
    protected Direction movementDirection = Direction.RIGHT;

    protected Rectangle hitBox;
    protected boolean   collisionOn = false;

    /**
     * Returns whether the two axis-aligned rectangles defined by their corner
     * coordinates overlap.
     *
     * @param x1 left   x of rect A
     * @param y1 top    y of rect A
     * @param x2 right  x of rect A
     * @param y2 bottom y of rect A
     * @param x3 left   x of rect B
     * @param y3 top    y of rect B
     * @param x4 right  x of rect B
     * @param y4 bottom y of rect B
     * @return {@code true} if the rectangles overlap
     */
    protected static boolean rectanglesOverlap(int x1, int y1, int x2, int y2,
                                               int x3, int y3, int x4, int y4) {
        int aLeft   = Math.min(x1, x2);
        int aRight  = Math.max(x1, x2);
        int aTop    = Math.min(y1, y2);
        int aBottom = Math.max(y1, y2);

        int bLeft   = Math.min(x3, x4);
        int bRight  = Math.max(x3, x4);
        int bTop    = Math.min(y3, y4);
        int bBottom = Math.max(y3, y4);

        return !(aRight < bLeft || bRight < aLeft || aBottom < bTop || bBottom < aTop);
    }

    /** Sets default values — called after the constructor. */
    protected abstract void setDefaultValues();

    /** Loads sprite images into image fields. */
    protected abstract void getImage() throws IOException;

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    public int getX()                             { return x; }
    public void setX(int x)                       { this.x = x; }

    public int getY()                             { return y; }
    public void setY(int y)                       { this.y = y; }

    public int getSpeed()                         { return speed; }
    public void setSpeed(int speed)               { this.speed = speed; }

    public boolean isAlive()                      { return isAlive; }

    public Direction getMovementDirection()       { return movementDirection; }
    public void setMovementDirection(Direction d) { this.movementDirection = d; }

    public int getHealthpoints()                  { return healthPoints; }
    public void setHealthpoints(int hp)           { this.healthPoints = hp; }

    public Rectangle getHitBox()                  { return hitBox; }

    public boolean isCollisionOn()                { return collisionOn; }
    public void setCollisionOn(boolean value)     { this.collisionOn = value; }
}