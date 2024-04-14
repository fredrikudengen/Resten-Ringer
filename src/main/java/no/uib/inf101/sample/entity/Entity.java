package no.uib.inf101.sample.entity;

import no.uib.inf101.sample.Game;

public class Entity {

    public Projectile projectile;
    public Player player;
    public NormalEnemy normalEnemy;
    public Game game;

    public int x;
    public int y;
    public int speed;
    public int healthPoints;

    public boolean isAlive;

    public boolean collidesWith(Entity other) {
        if (other instanceof NormalEnemy) {
            NormalEnemy enemy = (NormalEnemy) other;
            if (rectanglesOverlap(x - (game.tileSize / 2), y - (game.tileSize / 2), x + (game.tileSize / 2),
                    y + (game.tileSize / 2), x - (game.tileSize / 2), y + (game.tileSize / 2),
                    x + (game.tileSize / 2), y + (game.tileSize / 2))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

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

}
