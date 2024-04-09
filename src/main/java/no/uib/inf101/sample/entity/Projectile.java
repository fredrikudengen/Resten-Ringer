package no.uib.inf101.sample.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import no.uib.inf101.sample.Game;

public class Projectile extends Entity {

    private int quadrant;

    Game game;
    Player player;

    public Projectile(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public void setDefaultValues() {
        x = player.getPlayerX();
        System.out.println("Projectile start x = " + x);
        y = player.getPlayerY();
        System.out.println("Projectile start y = " + y);
        speed = 10;
    }

    public void shootProjectile(int mouseX, int mouseY) {
        
        setDefaultValues();

        System.out.println("Coordinate clicked was " + mouseX + " " + mouseY);

        Point point = new Point(mouseX, mouseY);

        System.out.println("player.getPlayerX = " + player.getPlayerX() + " " + "player.getPlayerY = " + player.getPlayerY());

        quadrant = segmentArea(player.getPlayerX(), player.getPlayerY(), point);
    }

    // Segment directions
    private static final int SEGMENT_ABOVE = 0;
    private static final int SEGMENT_TOP_RIGHT = 1;
    private static final int SEGMENT_RIGHT = 2;
    private static final int SEGMENT_BOTTOM_RIGHT = 3;
    private static final int SEGMENT_BELOW = 4;
    private static final int SEGMENT_BOTTOM_LEFT = 5;
    private static final int SEGMENT_LEFT = 6;
    private static final int SEGMENT_TOP_LEFT = 7;

    public int segmentArea(int playerX, int playerY, Point coordinate) {

        int clickedX = coordinate.x;
        int clickedY = coordinate.y;

        // Calculate the boundaries for the three equal areas

        int areaWidth = game.screenWidth / 3; // Total width divided into three areas
        int leftBoundary = playerX - areaWidth; // Left boundary of the middle area
        int rightBoundary = playerX + areaWidth; // Right boundary of the middle area

        // Determine the area based on the relative position of the clicked coordinates
        if (clickedY < playerY) {
            // Clicked point is above the player

            if (clickedX < leftBoundary) {
                System.out.println("Clicked area was top left");
                return SEGMENT_TOP_LEFT;
            } else if (clickedX > rightBoundary) {
                System.out.println("Clicked area was top right");
                return SEGMENT_TOP_RIGHT;
            } else {
                System.out.println("Clicked area was above");
                return SEGMENT_ABOVE;
            }
        } else if (clickedY > playerY) {
            // Clicked point is below the player

            if (clickedX < leftBoundary) {
                System.out.println("Clicked area was bottom left");
                return SEGMENT_BOTTOM_LEFT;

            } else if (clickedX > rightBoundary) {
                System.out.println("Clicked area was bottom right");
                return SEGMENT_BOTTOM_RIGHT;

            } else {
                System.out.println("Clicked area was below");
                return SEGMENT_BELOW;
            }
        } else {
            // Clicked point coincides with player position
            System.out.println("Clicked area coincides with player position");
            return SEGMENT_BELOW;
        }
    }

    // Getters and setters
    public int getProjectileX() {
        return x;
    }

    public int getProjectileY() {
        return y;
    }

    public int getProjectileSpeed() {
        return speed;
    }

    // Called upon once every frame in Game
    public void update() {

        // Update the position based on the segment area
        switch (quadrant) {

            case SEGMENT_ABOVE:
                y -= speed; // Move upward
                break;

            case SEGMENT_TOP_RIGHT:
                x += speed; // Move diagonally up-right
                y -= speed;
                break;

            case SEGMENT_RIGHT:
                x += speed; // Move right
                break;

            case SEGMENT_BOTTOM_RIGHT:
                x += speed; // Move diagonally down-right
                y += speed;
                break;

            case SEGMENT_BELOW:
                y += speed; // Move downward
                break;

            case SEGMENT_BOTTOM_LEFT:
                x -= speed; // Move diagonally down-left
                y += speed;
                break;

            case SEGMENT_LEFT:
                x -= speed; // Move left
                break;

            case SEGMENT_TOP_LEFT:
                x -= speed; // Move diagonally up-left
                y -= speed;
                break;
        }
    }
    public void draw(Graphics2D g2) {
        
        // Draw projectile
        g2.setColor(Color.RED);
        g2.fillRect(x, y, game.tileSize, game.tileSize);

    }
    
}
