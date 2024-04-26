package no.uib.inf101.sample.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.entity.enemy.NormalEnemy;
import no.uib.inf101.sample.entity.enemy.SlowEnemy;

public abstract class Entity {

    Projectile projectile;
    Player player;
    NormalEnemy normalEnemy;
    SlowEnemy slowEnemy;
    Game game;
    BufferedImage up1, up2, left1, left2, right1, right2, down1, down2, downHit1, downHit2, leftHit1, leftHit2, rightHit1, rightHit2, beer;

    protected int x;
    protected int y;
    protected int speed;
    protected int healthPoints;
    protected boolean isAlive;
    protected String movementDirection = "right";
    protected int imageNum = 1;
    protected int imageCounter = 0;

    protected Rectangle hitBox;
    protected boolean collisionOn = false;


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

    protected abstract void setDefaultValues();

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
    public boolean getIsAlive() {
        return isAlive;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public String getDirection() {
        return movementDirection;
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