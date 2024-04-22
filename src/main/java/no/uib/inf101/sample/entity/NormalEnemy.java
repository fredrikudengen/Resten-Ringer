package no.uib.inf101.sample.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.Inf101Graphics;

public class NormalEnemy extends Entity {

    Game game;
    Player player;

    String direction;

    int imageNum = 1;
    int imageCounter = 0;

    int safeZone = 100;

    private boolean isStopped = false;
    private long stopStartTime = 0;
    private static final long STOP_DURATION = 500000000; // 0.5 seconds in milliseconds

    boolean isMoving = false;

    int xNeededToMove;
    int yNeededToMove;

    private Image up1 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_1.png");
    private Image up2 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_2.png");
    private Image down1 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_1.png");
    private Image down2 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_2.png");
    private Image left1 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_1.png");
    private Image left2 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_2.png");
    private Image right1 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_1.png");
    private Image right2 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_2.png");

    public NormalEnemy(Game game, Player player) {
        this.game = game;
        this.player = player;
        setDefaultValues();
    }

    public void setDefaultValues() {
        Random rand = new Random();
        int screenWidth = game.screenWidth;
        int screenHeight = game.screenHeight;

        // Define the safe zone boundaries
        int safeZoneMinX = Math.max(1, player.x - safeZone);
        int safeZoneMaxX = Math.min(screenWidth - 1, (player.x + safeZone) - 1);
        int safeZoneMinY = Math.max(1, player.y - safeZone);
        int safeZoneMaxY = Math.min(screenHeight - 1, (player.y + safeZone) - 1);

        // Generate random X and Y coordinates outside the safe zone boundaries
        int randomX, randomY;

        // Coin toss to find whether enemy should spawn to the left/above or to the
        // right/below the player
        if (rand.nextBoolean()) {
            // Generate random X outside the safe zone boundaries
            randomX = rand.nextInt(safeZoneMinX);
        } else {
            // Generate random X between safeZoneMaxX and screenWidth
            randomX = ThreadLocalRandom.current().nextInt(safeZoneMaxX, screenWidth);
        }

        if (rand.nextBoolean()) {
            // Generate random Y outside the safe zone boundaries
            randomY = rand.nextInt(safeZoneMinY);
        } else {
            // Generate random Y between safeZoneMaxY and screenHeight
            randomY = ThreadLocalRandom.current().nextInt(safeZoneMaxY, screenHeight);
        }

        x = randomX;
        y = randomY;

        healthPoints = 3;
        speed = 2;
        direction = "right";
        isAlive = true;
    }

    public boolean enemyCollidesWithPlayer() {
        if (rectanglesOverlap(x - (game.tileSize / 2), y - (game.tileSize / 2), x + (game.tileSize / 2),
                y + (game.tileSize / 2), player.getPlayerX() - (game.tileSize / 2),
                player.getPlayerY() + (game.tileSize / 2),
                player.getPlayerX() + (game.tileSize / 2), player.getPlayerY() + (game.tileSize / 2))) {
            return true;
        } else {
            return false;
        }
    }

    public void simulateEnemyMoves() {
        xNeededToMove = player.getPlayerX() - x;
        yNeededToMove = player.getPlayerY() - y;
        if (yNeededToMove < 0) {
            y -= speed;
            yNeededToMove += speed;
            direction = "up";
        }
        if (xNeededToMove < 0) {
            x -= speed;
            xNeededToMove += speed;
            direction = "left";
        }
        if (xNeededToMove > 0) {
            x += speed;
            xNeededToMove -= speed;
            direction = "right";
        }
        if (yNeededToMove > 0) {
            y += speed;
            yNeededToMove -= speed;
        }
    }

    public void knockBackPlayer() {
        if (enemyCollidesWithPlayer() == true) {
            player.setShocked();
            if (direction == "up") {
                y -= 50;
                if (player.y - 100 < 0) {
                    player.x += 100;
                } else {
                    player.y -= 100;
                }
            } else if (direction == "left") {
                x += 50;
                if (player.x - 100 < 0) {
                    player.y += 100;
                } else {
                    player.x -= 100;
                }
            } else if (direction == "right") {
                x -= 50;
                if (player.x + 100 > game.screenWidth) {
                    player.y += 100;
                } else {
                    player.x += 100;
                }
            }
        }
    }

    public void update() {
        if (isStopped) {
            long currentTime = System.nanoTime();
            // Check if the enemy collides with the player
            if (enemyCollidesWithPlayer() && !(player.isShocked)) {
                // Perform actions when collision occurs
                // For example, reduce player's health
                player.healthPoints--;
                knockBackPlayer();

                // Start the stop timer
                isStopped = true;
                stopStartTime = System.nanoTime();
            }
            if (currentTime - stopStartTime >= STOP_DURATION) {
                // If the stop duration has passed, resume movement
                isStopped = false;
            } else {
                // Enemy remains stopped
                return;
            }
        }

        // Check if the enemy collides with the player
        if (enemyCollidesWithPlayer() && !(player.isShocked)) {
            // Perform actions when collision occurs
            // For example, reduce player's health
            player.healthPoints--;
            knockBackPlayer();

            // Start the stop timer
            isStopped = true;
            stopStartTime = System.nanoTime();
        }

        simulateEnemyMoves();

        if (healthPoints <= 0) {
            isAlive = false;
        }
        imageCounter++;
        if (imageCounter > 10) {
            if (imageNum == 1) {
                imageNum = 2;
            } else if (imageNum == 2) {
                imageNum = 1;
            }
            imageCounter = 0;
        }

    }

    public void draw(Graphics2D g2) {

        Image image = null;
        switch (direction) {
            case "up":
                if (imageNum == 1) {
                    image = up1;
                }
                if (imageNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (imageNum == 1) {
                    image = down1;
                }
                if (imageNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (imageNum == 1) {
                    image = left1;
                }
                if (imageNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (imageNum == 1) {
                    image = right1;
                }
                if (imageNum == 2) {
                    image = right2;
                }
                break;
        }
        // Draw player
        if (direction == "right") {
            g2.drawImage(image, x, y, game.tileSize * 2, game.tileSize, null);
        } else if (direction == "left") {
            g2.drawImage(image, x - game.tileSize, y, game.tileSize * 2, game.tileSize, null);
        } else if (direction == "up") {
            g2.drawImage(image, x, y - game.tileSize, game.tileSize, game.tileSize * 2, null);
        }
    }

    // Getters and setters
    public int getNormalEnemyX() {
        return x;
    }

    public int getNormalEnemyY() {
        return y;
    }

    public int getNormalEnemySpeed() {
        return speed;
    }

    public boolean getNormalEnemyIsALive(){
        return isAlive;
    }
}
