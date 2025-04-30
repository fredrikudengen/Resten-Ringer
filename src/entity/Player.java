package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Game;
import controller.GameController;


public class Player extends Entity implements DrawAndUpdate {

    private GameController gameController;
    private Game game;

    private String direction;
    private String movementDirection;

    private boolean isShocked;
    private static final long SHOCK_DURATION = 1000000000;
    private long shockStartTime = 0;

    private BufferedImage up1, up2, left1, left2, right1, right2, down1, down2, downHit1, downHit2, leftHit1,
            leftHit2, rightHit1, rightHit2, beer;

    public Player(Game game, GameController gameController) throws IOException {
        this.game = game;
        this.gameController = gameController;
        hitBox = new Rectangle(2, 0, 12, 16);
        setDefaultValues();
        getImage();
    }

    @Override
    public void setDefaultValues() {
        x = game.getScreenWidth() / 2;
        y = game.getScreenHeight() / 2;
        healthPoints = 6;
        speed = 4;
        direction = "right";
        isAlive = true;
        isShocked = false;
    }

    @Override
    protected void getImage() throws IOException {
        up1 = ImageIO.read(getClass().getResourceAsStream("/resources/Up_shotgun_walking_1.png"));
        up2 = ImageIO.read(getClass().getResourceAsStream("/resources/Up_shotgun_walking_2.png"));
        down1 = ImageIO.read(getClass().getResourceAsStream("/resources/Down_shotgun_walking_1.png"));
        down2 = ImageIO.read(getClass().getResourceAsStream("/resources/Down_shotgun_walking_2.png"));
        left1 = ImageIO.read(getClass().getResourceAsStream("/resources/Left_shotgun_walking_1.png"));
        left2 = ImageIO.read(getClass().getResourceAsStream("/resources/Left_shotgun_walking_2.png"));
        right1 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_shotgun_walking_1.png"));
        right2 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_shotgun_walking_2.png"));
        downHit1 = ImageIO.read(getClass().getResourceAsStream("/resources/Down_hit_1.png"));
        downHit2 = ImageIO.read(getClass().getResourceAsStream("/resources/Down_hit_2.png"));
        leftHit1 = ImageIO.read(getClass().getResourceAsStream("/resources/Left_hit_1.png"));
        leftHit2 = ImageIO.read(getClass().getResourceAsStream("/resources/Left_hit_2.png"));
        rightHit1 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_hit_1.png"));
        rightHit2 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_hit_2.png"));
        beer = ImageIO.read(getClass().getResourceAsStream("/resources/beer.png"));
    }

    @Override
    public void update() {
        collisionOn = false;
        game.collisionDetection.checkTile(this);
        if (collisionOn) {
            switch (movementDirection) {
                case "up":
                    y += speed; 
                    break;
                case "down":
                    y -= speed; 
                    break;
                case "left":
                    x += speed; 
                    break;
                case "right":
                    x -= speed; 
                    break;
            }
        }
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

    @Override
    public void draw(Graphics2D g2) {
        if (isShocked) {
            Image hitImage = null;
            switch (direction) {
                case "up":
                    hitImage = (imageNum == 1) ? downHit1 : downHit2;
                    break;
                case "down":
                    hitImage = (imageNum == 1) ? downHit1 : downHit2;
                    break;
                case "left":
                    hitImage = (imageNum == 1) ? leftHit1 : leftHit2;
                    break;
                case "right":
                    hitImage = (imageNum == 1) ? rightHit1 : rightHit2;
                    break;
            }
            // Draw player when shocked
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
                    playerImage = (imageNum == 1) ? up1 : up2;
                    break;
                case "down":
                    playerImage = (imageNum == 1) ? down1 : down2;
                    break;
                case "left":
                    playerImage = (imageNum == 1) ? left1 : left2;
                    break;
                case "right":
                    playerImage = (imageNum == 1) ? right1 : right2;
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
