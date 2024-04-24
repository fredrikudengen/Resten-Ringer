package no.uib.inf101.sample.entity.enemy;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.Inf101Graphics;
import no.uib.inf101.sample.entity.Entity;
import no.uib.inf101.sample.entity.Player;

public class Enemy extends Entity {

    Game game;
    Player player;

    int imageNum = 1;
    int imageCounter = 0;

    int safeZone = 100;

    boolean isMoving = false;

    int xNeededToMove;
    int yNeededToMove;

    private boolean isStopped = false;
    private long stopStartTime = 0;
    private static final long STOP_DURATION = 500000000; // 0.5 seconds in milliseconds

    private Image up1 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_1.png");
    private Image up2 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_2.png");
    private Image down1 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_1.png");
    private Image down2 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_2.png");
    private Image left1 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_1.png");
    private Image left2 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_2.png");
    private Image right1 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_1.png");
    private Image right2 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_2.png");

    public Enemy(Game game, Player player) {
        this.game = game;
        this.player = player;
        hitBox = new Rectangle(8, 16, 32, 32);
    }

    public void setXAndY() {
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
    }

    public void simulateEnemyMoves() {
        xNeededToMove = player.getPlayerX() - x;
        yNeededToMove = player.getPlayerY() - y;
        
        // Check tile collision
        collisionOn = false;
        //game.collisionDetection.checkTile(this);
        if (collisionOn == true) {
            if (movementDirection == "up") {
                if (xNeededToMove < 0) {
                    x -= speed;
                    movementDirection = "left";
                } else {
                    x += speed;
                    movementDirection = "right";
                }
            } else if (movementDirection == "down") {
                if (xNeededToMove < 0) {
                    x -= speed;
                    movementDirection = "left";
                } else {
                    x += speed;
                    movementDirection = "right";
                }
            } else if (movementDirection == "left") {
                    if (yNeededToMove < 0) {
                        y -= speed;
                        movementDirection = "up";
                    } else if (yNeededToMove > 0) {
                        y += speed;
                        movementDirection = "down";
                    }
            } else if (movementDirection == "right") {
                    if (yNeededToMove < 0) {
                        y -= speed;
                        movementDirection = "up";
                    } else if (yNeededToMove > 0) {
                        y += speed;
                        movementDirection = "down";
                    } 
                }
        }
            if (collisionOn == false) {
                if (yNeededToMove < 0) {
                    y -= speed;
                    yNeededToMove += speed;
                    movementDirection = "up";
                }
                else if (xNeededToMove < 0) {
                    x -= speed;
                    xNeededToMove += speed;
                    movementDirection = "left";
                }
                else if (xNeededToMove > 0) {
                    x += speed;
                    xNeededToMove -= speed;
                    movementDirection = "right";
                }
                else if (yNeededToMove > 0) {
                    y += speed;
                    yNeededToMove -= speed;
                }
            }
        
    }

    public boolean enemyCollidesWithPlayer() {
        return rectanglesOverlap(x + 9, y, x + game.tileSize - 9,
                y + game.tileSize, player.getPlayerX() + 9,
                player.getPlayerY(),
                player.getPlayerX() + game.tileSize - 9, player.getPlayerY() + game.tileSize);

    }

    public void draw(Graphics2D g2) {
        Image playerImage = null;
            switch (movementDirection) {
                case "up":
                    if (imageNum == 1) {
                        playerImage = up1;
                    }
                    if (imageNum == 2) {
                        playerImage = up2;
                    }
                    break;
                case "down":
                    if (imageNum == 1) {
                        playerImage = down1;
                    }
                    if (imageNum == 2) {
                        playerImage = down2;
                    }
                    break;
                case "left":
                    if (imageNum == 1) {
                        playerImage = left1;
                    }
                    if (imageNum == 2) {
                        playerImage = left2;
                    }
                    break;
                case "right":
                    if (imageNum == 1) {
                        playerImage = right1;
                    }
                    if (imageNum == 2) {
                        playerImage = right2;
                    }
                    break;
            }
            // Draw player
            if (movementDirection == "right") {
                g2.drawImage(playerImage, x, y, game.tileSize * 2, game.tileSize, null);
            } else if (movementDirection == "left") {
                g2.drawImage(playerImage, x - game.tileSize, y, game.tileSize * 2, game.tileSize, null);
            } else if (movementDirection == "up") {
                g2.drawImage(playerImage, x, y - game.tileSize, game.tileSize, game.tileSize * 2, null);
            } else if (movementDirection == "down") {
                g2.drawImage(playerImage, x, y, game.tileSize, game.tileSize * 2, null);
            }
        }
    // Enemy enemy = new Enemy(game, player);
    // Image image = null;

    // if (enemy instanceof FastEnemy) {
    // switch (direction) {
    // case "up":
    // image = (imageNum == 1) ? fast_enemy_up_1 : fast_enemy_up_2;
    // break;
    // case "down":
    // image = (imageNum == 1) ? fast_enemy_down_1 : fast_enemy_down_2;
    // break;
    // case "left":
    // image = (imageNum == 1) ? fast_enemy_left_1 : fast_enemy_left_2;
    // break;
    // case "right":
    // image = (imageNum == 1) ? fast_enemy_right_1 : fast_enemy_right_2;
    // break;
    // }
    // } else if (enemy instanceof NormalEnemy) {
    // switch (direction) {
    // case "up":
    // image = (imageNum == 1) ? normal_enemy_up_1 : normal_enemy_up_2;
    // break;
    // case "down":
    // image = (imageNum == 1) ? normal_enemy_down_1 : normal_enemy_down_2;
    // break;
    // case "left":
    // image = (imageNum == 1) ? normal_enemy_left_1 : normal_enemy_left_2;
    // break;
    // case "right":
    // image = (imageNum == 1) ? normal_enemy_right_1 : normal_enemy_right_2;
    // break;
    // }
    // } else if (enemy instanceof SlowEnemy) {
    // switch (direction) {
    // case "up":
    // image = (imageNum == 1) ? slow_enemy_up_1 : slow_enemy_up_2;
    // break;
    // case "down":
    // image = (imageNum == 1) ? slow_enemy_down_1 : slow_enemy_down_2;
    // break;
    // case "left":
    // image = (imageNum == 1) ? slow_enemy_left_1 : slow_enemy_left_2;
    // break;
    // case "right":
    // image = (imageNum == 1) ? slow_enemy_right_1 : slow_enemy_right_2;
    // break;
    // }
    // }

    // if (image != null) {
    // g2.drawImage(image, x, y, game.tileSize, game.tileSize, null);
    // }

    public void knockBackPlayer() {
        if (enemyCollidesWithPlayer() == true) {
            player.setShocked();
            if (movementDirection == "up") {
                y -= 50;
                if (player.y - 100 < 0) {
                    player.x += 100;
                } else {
                    player.y -= 50;
                }
            } else if (movementDirection == "left") {
                x += 50;
                if (player.x - 100 < 0) {
                    player.y += 100;
                } else {
                    player.x -= 50;
                }
            } else if (movementDirection == "right") {
                x -= 50;
                if (player.x + 100 > game.screenWidth) {
                    player.y += 100;
                } else {
                    player.x += 50;
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
}
