package entity.enemy;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import model.Game;
import model.GameState;
import entity.Entity;
import entity.Player;

public abstract class Enemy extends Entity {

    protected Game game;
    protected Player player;

    private int imageNum = 1;
    private int imageCounter = 0;
    private int xNeededToMove;
    private int yNeededToMove;

    private boolean isStopped = false;
    private long stopStartTime = 0;
    private static final long STOP_DURATION = 500000000; // 0.5 seconds in milliseconds

    protected BufferedImage normal_enemy_up_1, normal_enemy_up_2, normal_enemy_down_1, normal_enemy_down_2;
    protected BufferedImage slow_enemy_left_1, slow_enemy_left_2, slow_enemy_right_1, slow_enemy_right_2,
            slow_enemy_up_1, slow_enemy_up_2;
    protected BufferedImage fast_enemy_up_1, fast_enemy_up_2, fast_enemy_left_1, fast_enemy_left_2, fast_enemy_right_1,
            fast_enemy_right_2;

    public Enemy(Game game, Player player) {
        this.game = game;
        this.player = player;
        hitBox = new Rectangle(2, 0, 12, 16);
    }

    @Override
    protected abstract void setDefaultValues();

    /**
     * Sets the x and y coordinate of an Enemy in on of the four corners of the map.
     */
    protected void setSpawn() {
        Random rand = new Random();

        int spawnNumber = rand.nextInt(4);
        switch (spawnNumber) {
            case 0:
                x = 50;
                y = 50;
                break;
            case 1:
                x = game.getScreenWidth() - 100;
                y = game.getScreenHeight() - 100;
                break;
            case 2:
                x = game.getScreenWidth() - 100;
                y = 50;
                break;
            case 3:
                x = 50;
                y = game.getScreenHeight() - 50;
        }
    }

    /**
     * Updates the x and y of Enemy so it moves towards the Player.
     */
    protected void simulateEnemyMoves() {
        xNeededToMove = player.getX() - x;
        yNeededToMove = player.getY() - y;
        // If number of x pixels needed to move is larger than y, move to left or right
        // first
        if (Math.abs(xNeededToMove) > Math.abs(yNeededToMove)) {
            collisionOn = false;
            game.collisionDetection.checkTile(this);
            if (collisionOn == true) {
                // Move around collision tile based on direction
                if (movementDirection == "left") {
                    y += speed;
                } else if (movementDirection == "right") {
                    y += speed;
                } else if (movementDirection == "up") {
                    x += speed;
                } else if (movementDirection == "down") {
                    x += speed;
                }
            } else {
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
        // If number of y pixels needed to move is larger than x, move up or down
        // first
        if (Math.abs(xNeededToMove) <= Math.abs(yNeededToMove)) {
            collisionOn = false;
            game.collisionDetection.checkTile(this);
            if (collisionOn == true) {
                if (movementDirection == "left") {
                    y += speed;
                } else if (movementDirection == "right") {
                    y += speed;
                } else if (movementDirection == "up") {
                    x += speed;
                } else if (movementDirection == "down") {
                    x += speed;
                }
            } else {
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

    /**
     * Checks whether Enemy collides with Player. Subtracts 9 because Player sprite
     * is slightly smaller than tileSize.
     * 
     * @return boolean whether Enemy collides with Player or not.
     */
    protected boolean enemyCollidesWithPlayer() {
        return rectanglesOverlap(x + 2 , y, x + game.getTileSize() - 2,
                y + game.getTileSize(), player.getX() + 2,
                player.getY(),
                player.getX() + game.getTileSize() - 2, player.getY() + game.getTileSize());
    }

    /**
     * Draws an image based on the instance of the Enemy argument. This method is
     * called in DrawGame.
     * 
     * @param enemy object used to differentiate between different Enemy sprites.
     * @param g2    Graphics2D object used to draw.
     */
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
            g2.drawImage(image, x, y, game.getTileSize(), game.getTileSize(), null);
        }

    }

    /**
     * Knocks back the enemy if it collides with the player in the opposite
     * direction of it's movement direction
     */
    protected void knockBackEnemy() {
        if (enemyCollidesWithPlayer() == true) {
            player.setShocked();
            if (movementDirection == "up") {
                if (!(y - 50 < 20)) {
                    y -= 50;
                }
            } else if (movementDirection == "left") {
                if (!(x + 50 > game.getScreenWidth() - game.getTileSize() - 5)) {
                    x += 50;
                }
            } else if (movementDirection == "right") {
                if (!(x - 50 < 20)) {
                    x -= 50;
                }
            } else if (movementDirection == "down") {
                if (!(y + 50 < game.getScreenHeight() - 60)) {
                    y -= 50;
                }
            }
        }
    }

    /**
     * Updates Enemy coordinates, knockback, player healthpoints if hit and isAlive.
     */
    public void update() {
        if (game.getGameState() == GameState.ACTIVE_GAME) {
            // Check if the enemy collides with the player
            if (enemyCollidesWithPlayer() && !(player.getIsShocked())) {
                // Perform actions when collision occurs
                // For example, reduce player's health
                player.setHealthpoints(player.getHealthpoints() - 1);

                knockBackEnemy();

                // Start the stop timer
                isStopped = true;
                stopStartTime = System.nanoTime();
            }
            if (isStopped) {
                long currentTime = System.nanoTime();
                // Check if the enemy collides with the player
                if (enemyCollidesWithPlayer() && !(player.getIsShocked())) {
                    // Perform actions when collision occurs
                    // For example, reduce player's health
                    player.setHealthpoints(player.getHealthpoints() - 1);

                    knockBackEnemy();

                    // Start the stop timer
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

    public boolean getEnemyCollidesWithPlayer() {
        return enemyCollidesWithPlayer();
    }
}
