package no.uib.inf101.sample.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;
import no.uib.inf101.sample.GameState;
import no.uib.inf101.sample.Inf101Graphics;

public class Player extends Entity {

    GameController gameController;
    Game game;
    GameState gameState;

    private String direction;
    private String movementDirection;

    private boolean isShocked;
    private static final long SHOCK_DURATION = 1000000000;
    private long shockStartTime = 0;

    private void getPlayerImage() {
    up1 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_1.png");
    up2 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_2.png");
    down1 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_1.png");
    down2 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_2.png");
    left1 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_1.png");
    left2 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_2.png");
    right1 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_1.png");
    right2 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_2.png");
    downHit1 = Inf101Graphics.loadImageFromResources("Down_hit_1.png");
    downHit2 = Inf101Graphics.loadImageFromResources("Down_hit_2.png");
    leftHit1 = Inf101Graphics.loadImageFromResources("Left_hit_1.png");
    leftHit2 = Inf101Graphics.loadImageFromResources("Left_hit_2.png");
    rightHit1 = Inf101Graphics.loadImageFromResources("Right_hit_1.png");
    rightHit2 = Inf101Graphics.loadImageFromResources("Right_hit_2.png");
    }

    private void loadHealthIcon(){
        beer = Inf101Graphics.loadImageFromResources("beer.png");
}

    public Player(Game game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;
        hitBox = new Rectangle(8, 16, 32, 32);
        setDefaultValues();
        getPlayerImage();
        loadHealthIcon();
    }

    protected void setDefaultValues() {
        x = game.getScreenWidth() / 2;
        y = game.getScreenWidth() / 2;
        healthPoints = 6;
        speed = 4;
        direction = "right";
        isAlive = true;
        isShocked = false;
    }

    public void update() {
        // Check tile collision
        collisionOn = false;
        game.collisionDetection.checkTile(this);
        // Handle movement based on collision detection
        if (collisionOn) {
            // Reset player position based on the direction of movement
            switch (movementDirection) {
                case "up":
                    y += speed; // Move the player back down
                    break;
                case "down":
                    y -= speed; // Move the player back up
                    break;
                case "left":
                    x += speed; // Move the player back to the right
                    break;
                case "right":
                    x -= speed; // Move the player back to the left
                    break;
            }
        }

        // If collision is false, player can move
        if (collisionOn == false) {
            if (gameController.downPressed || gameController.leftPressed || gameController.rightPressed
                    || gameController.upPressed) {
                if (gameController.upPressed == true) {
                    direction = "up";
                    movementDirection = "up";
                    y -= speed;
                } else if (gameController.downPressed == true) {
                    direction = "down";
                    movementDirection = "down";
                    y += speed;
                } else if (gameController.leftPressed == true) {
                    direction = "left";
                    movementDirection = "left";
                    x -= speed;
                } else if (gameController.rightPressed == true) {
                    direction = "right";
                    movementDirection = "right";
                    x += speed;
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
        if (gameController.shootDown == true) {
            direction = "down";
        } else if (gameController.shootUp == true) {
            direction = "up";
        } else if (gameController.shootLeft == true) {
            direction = "left";
        } else if (gameController.shootRight == true) {
            direction = "right";
        }

        if (y <= 20) {
            y = 20;
        }
        if (y >= game.getScreenHeight() - 60) {
            y = game.getScreenHeight() - 60;
        }
        if (x <= 5) {
            x = 5;
        }
        if (x >= game.getScreenWidth() - game.getTileSize() - 5) {
            x = game.getScreenWidth() - game.getTileSize() - 5;
        }
    }

    public void draw(Graphics2D g2) {
        if (isShocked) {
            Image hitImage = null;
            switch (direction) {
                case "up":
                    if (imageNum == 1) {
                        hitImage = downHit1;
                    }
                    if (imageNum == 2) {
                        hitImage = downHit2;
                    }
                    break;
                case "down":
                    if (imageNum == 1) {
                        hitImage = downHit1;
                    }
                    if (imageNum == 2) {
                        hitImage = downHit2;
                    }
                    break;
                case "left":
                    if (imageNum == 1) {
                        hitImage = leftHit1;
                    }
                    if (imageNum == 2) {
                        hitImage = leftHit2;
                    }
                    break;
                case "right":
                    if (imageNum == 1) {
                        hitImage = rightHit1;
                    }
                    if (imageNum == 2) {
                        hitImage = rightHit2;
                    }
                    break;
            }

            if (direction == "right") {
                g2.drawImage(hitImage, x, y, game.getTileSize() * 2, game.getTileSize(), null);
            } else if (direction == "left") {
                g2.drawImage(hitImage, x - game.getTileSize(), y, game.getTileSize() * 2, game.getTileSize(), null);
            } else if (direction == "up") {
                g2.drawImage(hitImage, x, y, game.getTileSize(), game.getTileSize() * 2, null);
            } else if (direction == "down") {
                g2.drawImage(hitImage, x, y, game.getTileSize(), game.getTileSize() * 2, null);
            }

            if (isShocked) {
                long currentTime = System.nanoTime();
                if (currentTime - shockStartTime >= SHOCK_DURATION) {
                    isShocked = false;
                }
            }

        } else {
            Image playerImage = null;
            switch (direction) {
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
            if (direction == "right") {
                g2.drawImage(playerImage, x, y, game.getTileSize() * 2, game.getTileSize(), null);
            } else if (direction == "left") {
                g2.drawImage(playerImage, x - game.getTileSize(), y, game.getTileSize() * 2, game.getTileSize(), null);
            } else if (direction == "up") {
                g2.drawImage(playerImage, x, y - game.getTileSize(), game.getTileSize(), game.getTileSize() * 2, null);
            } else if (direction == "down") {
                g2.drawImage(playerImage, x, y, game.getTileSize(), game.getTileSize() * 2, null);
            }
        }

        // Draw hearts
        for (int i = 0; i < healthPoints; i++) {
            g2.drawImage(beer, 10 + (i * (game.getTileSize() / 2 + 5)), 20, game.getTileSize() / 2,
                    game.getTileSize() / 2, null);
        }
    }

    public boolean getIsShocked() {
        return this.isShocked;
    }

    public void setShocked() {
        isShocked = true;
        shockStartTime = System.nanoTime();
    }

}
