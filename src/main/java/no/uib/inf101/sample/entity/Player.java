package no.uib.inf101.sample.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;
import no.uib.inf101.sample.Inf101Graphics;

public class Player extends Entity {

    GameController gameController;
    Game game;

    String direction;

    int imageNum = 1;
    int imageCounter = 0;

    boolean isMoving = false;

    private Image up1 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_1.png");
    private Image up2 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_2.png");
    private Image down1 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_1.png");
    private Image down2 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_2.png");
    private Image left1 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_1.png");
    private Image left2 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_2.png");
    private Image right1 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_1.png");
    private Image right2 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_2.png");

    public Player(Game game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;
        setDefaultValues();
    }

    public void setDefaultValues() {
        // x = game.screenWidth/2;
        // y = game.screenHeight/2;
        x = 140;
        y = 100;
        healthPoints = 10;
        speed = 4;
        direction = "down";
        isAlive = true;
    }

    public void update() {

        if (healthPoints <= 0) {
            isAlive = false;
        }

        if (gameController.upPressed == true || gameController.downPressed == true || gameController.leftPressed == true
                || gameController.rightPressed == true) {
            isMoving = true;
        } else {
            isMoving = false;
        }

        if (gameController.shootDown == true && isMoving == false) {
            direction = "down";
        } else if (gameController.shootLeft == true && isMoving == false) {
            direction = "left";
        } else if (gameController.shootRight == true && isMoving == false) {
            direction = "right";
        } else if (gameController.shootUp == true && isMoving == false) {
            direction = "up";
        } else if (isMoving == true) {

            if (gameController.upPressed == true) {
                y -= speed;
                if (y <= 0) {
                    y = 0;
                }
                if (gameController.shootDown == true) {
                    direction = "down";
                } else if (gameController.shootUp == true) {
                    direction = "up";
                } else if (gameController.shootLeft == true) {
                    direction = "left";
                } else if (gameController.shootRight == true) {
                    direction = "right";
                } else {
                    direction = "up";
                }
            }

            if (gameController.downPressed == true) {
                y += speed;
                if (y >= game.screenHeight - game.tileSize) {
                    y = game.screenHeight - game.tileSize;
                }
                if (gameController.shootDown == true) {
                    direction = "down";
                } else if (gameController.shootUp == true) {
                    direction = "up";
                } else if (gameController.shootLeft == true) {
                    direction = "left";
                } else if (gameController.shootRight == true) {
                    direction = "right";
                } else {
                    direction = "down";
                }
            }
            if (gameController.leftPressed == true) {
                x -= speed;
                if (x <= 0) {
                    x = 0;
                }
                if (gameController.shootDown == true) {
                    direction = "down";
                } else if (gameController.shootUp == true) {
                    direction = "up";
                } else if (gameController.shootLeft == true) {
                    direction = "left";
                } else if (gameController.shootRight == true) {
                    direction = "right";
                } else {
                    direction = "left";
                }
            }
            if (gameController.rightPressed == true) {
                x += speed;
                if (x >= game.screenWidth - game.tileSize) {
                    x = game.screenWidth - game.tileSize;
                }
                if (gameController.shootDown == true) {
                    direction = "down";
                } else if (gameController.shootUp == true) {
                    direction = "up";
                } else if (gameController.shootLeft == true) {
                    direction = "left";
                } else if (gameController.shootRight == true) {
                    direction = "right";
                } else {
                    direction = "right";
                }
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

    // Getters and setters
    public int getPlayerX() {
        return x;
    }

    public int getPlayerY() {
        return y;
    }

    public int getPlayerSpeed() {
        return speed;
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
        } else if (direction == "down") {
            g2.drawImage(image, x, y, game.tileSize, game.tileSize * 2, null);
        }
    }

    public Object getDirection() {
        return direction;
    }

    public Object isAlive() {
        return isAlive;
    }
}
