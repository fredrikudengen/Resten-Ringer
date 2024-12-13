package entity;


import java.awt.Rectangle;
import java.io.IOException;

public abstract class Entity {

    protected int x;
    protected int y;
    protected int speed;
    protected int healthPoints;
    protected int imageNum = 1;
    protected int imageCounter = 0;

    protected boolean isAlive;
    protected String movementDirection = "right";

    protected Rectangle hitBox;
    protected boolean collisionOn = false;

    /**
     * Checks whether two rectangles (hitboxes of two entities) overlap eachother 
     * 
     * @param x1 first x coordinate of first rectangle 
     * @param y1 first y coordinate of first rectangle 
     * @param x2 second x coordinate of first rectangle 
     * @param y2 second y coordinate of first rectangle 
     * @param x3 first x coordinate of second rectangle 
     * @param y3 first y coordinate of second rectangle 
     * @param x4 second x coordinate of second rectangle 
     * @param y4 second y coordinate of second rectangle 
     * @return if the two rectangles overlap
     */
    protected static boolean rectanglesOverlap(int x1, int y1, int x2, int y2,
            int x3, int y3, int x4, int y4) {
        int xLeft = Math.min(x1, x2);
        int xRight = Math.max(x1, x2);
        int yTop = Math.min(y1, y2);
        int yBottom = Math.max(y1, y2);

        int x2Left = Math.min(x3, x4);
        int x2Right = Math.max(x3, x4);
        int y2Top = Math.min(y3, y4);
        int y2Bottom = Math.max(y3, y4);

        if (xRight < x2Left || x2Right < xLeft || yBottom < y2Top || y2Bottom < yTop) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Sets default values of object after the contructor is made.
     */
    protected abstract void setDefaultValues();

    /**
     * Sets the image variables as image files.
     */
    
    protected abstract void getImage() throws IOException;

    // Getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(String movementDirection) {
        this.movementDirection = movementDirection;
    }

    public void setHealthpoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getHealthpoints() {
        return this.healthPoints;
    }

    public Rectangle getHitBox() {
        return this.hitBox;
    }

    public boolean getCollisionOn() {
        return this.collisionOn;
    }

    public void setCollisionOn(boolean statement) {
        this.collisionOn = statement;
    }
}