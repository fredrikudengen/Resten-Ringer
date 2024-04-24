package no.uib.inf101.sample.entity.enemy;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.Inf101Graphics;
import no.uib.inf101.sample.entity.Entity;
import no.uib.inf101.sample.entity.Player;

public abstract class Enemy extends Entity {

    Game game;
    Player player;

    int imageNum = 1;
    int imageCounter = 0;
    boolean isMoving = false;

    int xNeededToMove;
    int yNeededToMove;

    private boolean isStopped = false;
    private long stopStartTime = 0;
    private static final long STOP_DURATION = 500000000; // 0.5 seconds in milliseconds

    private Image normal_enemy_up_1 = Inf101Graphics.loadImageFromResources("Up_normal_enemy_1.png");
    private Image normal_enemy_up_2 = Inf101Graphics.loadImageFromResources("Up_normal_enemy_2.png");
    private Image normal_enemy_down_1 = Inf101Graphics.loadImageFromResources("Down_normal_enemy_1.png");
    private Image normal_enemy_down_2 = Inf101Graphics.loadImageFromResources("Down_normal_enemy_2.png");

    private Image slow_enemy_left_1 = Inf101Graphics.loadImageFromResources("Left_slow_enemy_1.png");
    private Image slow_enemy_left_2 = Inf101Graphics.loadImageFromResources("Left_slow_enemy_2.png");
    private Image slow_enemy_right_1 = Inf101Graphics.loadImageFromResources("Right_slow_enemy_1.png");
    private Image slow_enemy_right_2 = Inf101Graphics.loadImageFromResources("Right_slow_enemy_2.png");
    private Image slow_enemy_up_1 = Inf101Graphics.loadImageFromResources("Up_slow_enemy_1.png");
    private Image slow_enemy_up_2 = Inf101Graphics.loadImageFromResources("Up_slow_enemy_2.png");

    private Image fast_enemy_up_1 = Inf101Graphics.loadImageFromResources("Up_police_1.png");
    private Image fast_enemy_up_2 = Inf101Graphics.loadImageFromResources("Up_police_2.png");
    private Image fast_enemy_left_1 = Inf101Graphics.loadImageFromResources("Left_police_1.png");
    private Image fast_enemy_left_2 = Inf101Graphics.loadImageFromResources("Left_police_2.png");
    private Image fast_enemy_right_1 = Inf101Graphics.loadImageFromResources("Right_police_1.png");
    private Image fast_enemy_right_2 = Inf101Graphics.loadImageFromResources("Right_police_2.png");

    public Enemy(Game game, Player player) {
        this.game = game;
        this.player = player;
        hitBox = new Rectangle(8, 16, 32, 32);
        setXAndY();
    }

    public abstract void setDefaultValues();

    public void setXAndY() {
        Random rand = new Random();

        int spawnNumber = rand.nextInt(4);
        switch (spawnNumber) {
            case 0:
                x = 50;
                y = 50;
                break;
            case 1:
                x = game.screenWidth - 100;
                y = game.screenHeight - 100;
                break;
            case 2:
                x = game.screenWidth - 100;
                y = 50;
                break;
            case 3:
                x = 50;
                y = game.screenHeight - 50;
        }
    }

    public void simulateEnemyMoves() {
        xNeededToMove = player.getPlayerX() - x;
        yNeededToMove = player.getPlayerY() - y;
        if (Math.abs(xNeededToMove) > Math.abs(yNeededToMove)) {
            // Check tile collision
            collisionOn = false;
            game.collisionDetection.checkTile(this);
            if (collisionOn == true) {
                if (movementDirection == "left") {
                    if (yNeededToMove < 0) {
                        y -= speed;
                    } else if (yNeededToMove > 0) {
                        y += speed;
                    }
                } else if (movementDirection == "right") {
                    if (yNeededToMove < 0) {
                        y -= speed;
                    } else if (yNeededToMove > 0) {
                        y += speed;
                    }
                } else if (movementDirection == "up") {
                    if (xNeededToMove < 0) {
                        x -= speed;
                    } else {
                        x += speed;
                    }
                } else if (movementDirection == "down") {
                    if (xNeededToMove < 0) {
                        x -= speed;
                    } else {
                        x += speed;
                    }
                }
            }
            if (collisionOn == false) {
                if (xNeededToMove < 0) {
                    x -= speed;
                    xNeededToMove += speed;
                    movementDirection = "left";
                } else if (xNeededToMove > 0) {
                    x += speed;
                    xNeededToMove -= speed;
                    movementDirection = "right";
                } else if (yNeededToMove < 0) {
                    y -= speed;
                    yNeededToMove += speed;
                    movementDirection = "up";
                } else if (yNeededToMove > 0) {
                    y += speed;
                    yNeededToMove -= speed;
                    movementDirection = "down";
                }
            }
        }
        if (Math.abs(xNeededToMove) <= Math.abs(yNeededToMove)) {
            // Check tile collision
            collisionOn = false;
            game.collisionDetection.checkTile(this);
            if (collisionOn == true) {
                if (movementDirection == "up") {
                    if (xNeededToMove < 0) {
                        x -= speed;
                    } else {
                        x += speed;
                    }
                } else if (movementDirection == "down") {
                    if (xNeededToMove < 0) {
                        x -= speed;
                    } else {
                        x += speed;
                    }
                } else if (movementDirection == "left") {
                    if (yNeededToMove < 0) {
                        y -= speed;
                    } else if (yNeededToMove > 0) {
                        y += speed;
                    }
                } else if (movementDirection == "right") {
                    if (yNeededToMove < 0) {
                        y -= speed;
                    } else if (yNeededToMove > 0) {
                        y += speed;
                    }
                }
            }

            if (collisionOn == false) {
                if (yNeededToMove < 0) {
                    y -= speed;
                    yNeededToMove += speed;
                    movementDirection = "up";
                } else if (yNeededToMove > 0) {
                    y += speed;
                    yNeededToMove -= speed;
                    movementDirection = "down";
                } else if (xNeededToMove < 0) {
                    x -= speed;
                    xNeededToMove += speed;
                    movementDirection = "left";
                } else if (xNeededToMove > 0) {
                    x += speed;
                    xNeededToMove -= speed;
                    movementDirection = "right";
                }
            }
        }
    }

    public boolean enemyCollidesWithPlayer() {
        return rectanglesOverlap(x + 9, y, x + game.tileSize - 9,
                y + game.tileSize, player.getPlayerX() + 9,
                player.getPlayerY(),
                player.getPlayerX() + game.tileSize - 9, player.getPlayerY() + game.tileSize);

    }

    public void draw(Enemy enemy, Graphics2D g2) {
        Image image = null;

        if (enemy instanceof SlowEnemy) {
            switch (movementDirection) {
                case "up":
                    image = (imageNum == 1) ? slow_enemy_up_1 : slow_enemy_up_2;
                    break;
                case "down":
                    image = (imageNum == 1) ? slow_enemy_right_1 : slow_enemy_right_2;
                    break;
                case "left":
                    image = (imageNum == 1) ? slow_enemy_left_1 : slow_enemy_left_2;
                    break;
                case "right":
                    image = (imageNum == 1) ? slow_enemy_right_1 : slow_enemy_right_2;
                    break;
            }
        }

        if (enemy instanceof NormalEnemy) {
            switch (movementDirection) {
                case "up":
                    image = (imageNum == 1) ? normal_enemy_up_1 : normal_enemy_up_2;
                    break;
                case "down":
                    image = (imageNum == 1) ? normal_enemy_down_1 : normal_enemy_down_2;
                    break;
                case "left":
                    image = (imageNum == 1) ? normal_enemy_down_1 : normal_enemy_down_2;
                    break;
                case "right":
                    image = (imageNum == 1) ? normal_enemy_down_1 : normal_enemy_down_2;
                    break;
            }
        }
        if (enemy instanceof FastEnemy) {
            switch (movementDirection) {
                case "up":
                    image = (imageNum == 1) ? fast_enemy_up_1 : fast_enemy_up_2;
                    break;
                case "down":
                    image = (imageNum == 1) ? fast_enemy_left_1 : fast_enemy_left_2;
                    break;
                case "left":
                    image = (imageNum == 1) ? fast_enemy_left_1 : fast_enemy_left_2;
                    break;
                case "right":
                    image = (imageNum == 1) ? fast_enemy_right_1 : fast_enemy_right_2;
                    break;
            }

        }

        if (image != null) {
            g2.drawImage(image, x, y, game.tileSize, game.tileSize, null);
        }

    }

    public void knockBackPlayer() {
        if (enemyCollidesWithPlayer() == true) {
            player.setShocked();
            if (movementDirection == "up") {
                if (y - 50 < 0) {
                    x -= 50;
                } else {
                    y -= 50;
                }
            } else if (movementDirection == "left") {
                if (x + 50 < game.screenWidth) {
                    y += 50;
                } else {
                    x += 50;
                }
            } else if (movementDirection == "right") {
                if (x - 50 < 0) {
                    y += 50;
                } else {
                    x -= 50;
                }
            } else if (movementDirection == "down") {
                if (y + 50 < game.screenHeight) {
                    y += 50;
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
