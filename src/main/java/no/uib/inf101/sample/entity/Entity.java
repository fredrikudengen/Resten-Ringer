package no.uib.inf101.sample.entity;

import java.awt.Rectangle;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.entity.enemy.NormalEnemy;
import no.uib.inf101.sample.entity.enemy.SlowEnemy;

public class Entity {

    public Projectile projectile;
    public Player player;
    public NormalEnemy normalEnemy;
    public SlowEnemy slowEnemy;
    public Game game;

    public int x;
    public int y;
    public int speed;
    public int healthPoints;
    public boolean isAlive;
    public String movementDirection = "right";

    public Rectangle hitBox;
    public boolean collisionOn = false;

    public static boolean rectanglesOverlap(int x1, int y1, int x2, int y2,
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

    // Getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return isAlive;
    }
}