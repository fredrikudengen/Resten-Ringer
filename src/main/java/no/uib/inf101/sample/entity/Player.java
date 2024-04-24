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

    String direction;
    public String movementDirection;
    String knockBackDirection;

    int imageNum = 1;
    int imageCounter = 0;

    boolean isMoving = false;
    public boolean isShocked;
    static final long SHOCK_DURATION = 1000000000;
    long shockStartTime = 0;

    private Image up1 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_1.png");
    private Image up2 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_2.png");
    private Image down1 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_1.png");
    private Image down2 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_2.png");
    private Image left1 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_1.png");
    private Image left2 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_2.png");
    private Image right1 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_1.png");
    private Image right2 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_2.png");
    private Image downHit1 = Inf101Graphics.loadImageFromResources("Down_hit_1.png");
    private Image downHit2 = Inf101Graphics.loadImageFromResources("Down_hit_2.png");
    private Image leftHit1 = Inf101Graphics.loadImageFromResources("Left_hit_1.png");
    private Image leftHit2 = Inf101Graphics.loadImageFromResources("Left_hit_2.png");
    private Image rightHit1 = Inf101Graphics.loadImageFromResources("Right_hit_1.png");
    private Image rightHit2 = Inf101Graphics.loadImageFromResources("Right_hit_2.png");

    private Image beer = Inf101Graphics.loadImageFromResources("beer.png");

    public Player(Game game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;
        hitBox = new Rectangle(8, 16, 32, 32);
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = game.screenWidth / 2 - game.tileSize / 2;
        y = game.screenHeight / 2 - game.tileSize / 2;
        healthPoints = 10;
        speed = 4;
        direction = "right";
        isAlive = true;
        isShocked = false;
    }

    public void setShocked() {
        isShocked = true;
        shockStartTime = System.nanoTime();
    }

    public void update() {
        System.out.println("X = " + x);
        System.out.println("Y = " + y);
        if (healthPoints <= 0) {
            gameState = GameState.GAME_OVER;
        }
        // Check tile collision
        collisionOn = false;
        game.collisionDetection.checkTile(this);
        if (collisionOn == true) {
            if (movementDirection == "up") {
                if (gameController.downPressed == true) {
                    direction = "down";
                    movementDirection = "down";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        y += speed;
                    }
                } else if (gameController.leftPressed == true) {
                    direction = "left";
                    movementDirection = "left";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        x -= speed;
                    }

                } else if (gameController.rightPressed == true) {
                    direction = "right";
                    movementDirection = "right";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        x += speed;
                    }
                }
            } else if (movementDirection == "down") {
                if (gameController.upPressed == true) {
                    direction = "up";
                    movementDirection = "up";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        y -= speed;
                    }
                } else if (gameController.leftPressed == true) {
                    direction = "left";
                    movementDirection = "left";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        x -= speed;
                    }
                } else if (gameController.rightPressed == true) {
                    direction = "right";
                    movementDirection = "right";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        x += speed;
                    }
                }
            } else if (movementDirection == "right") {
                if (gameController.upPressed == true) {
                    direction = "up";
                    movementDirection = "up";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        y -= speed;
                    }
                } else if (gameController.downPressed == true) {
                    direction = "down";
                    movementDirection = "down";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        y += speed;
                    }
                } else if (gameController.leftPressed == true) {
                    direction = "left";
                    movementDirection = "left";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        x -= speed;
                    }
                }
            } else if (movementDirection == "left") {
                if (gameController.upPressed == true) {
                    direction = "up";
                    movementDirection = "up";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        y -= speed;
                    }
                } else if (gameController.downPressed == true) {
                    direction = "down";
                    movementDirection = "down";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        y += speed;
                    }
                } else if (gameController.rightPressed == true) {
                    direction = "right";
                    movementDirection = "right";
                    game.collisionDetection.checkTile(this);
                    if (!collisionOn) {
                        x += speed;
                    }
                }
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

        if (y <= 10) {
            y = 10;
        }
        if (y >= game.screenHeight - game.tileSize - 10) {
            y = game.screenHeight - game.tileSize - 10;
        }
        if (x <= 5) {
            x = 5;
        }
        if (x >= game.screenWidth - game.tileSize - 5) {
            x = game.screenWidth - game.tileSize - 5;
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
                g2.drawImage(hitImage, x, y, game.tileSize * 2, game.tileSize, null);
            } else if (direction == "left") {
                g2.drawImage(hitImage, x - game.tileSize, y, game.tileSize * 2, game.tileSize, null);
            } else if (direction == "up") {
                g2.drawImage(hitImage, x, y, game.tileSize, game.tileSize * 2, null);
            } else if (direction == "down") {
                g2.drawImage(hitImage, x, y, game.tileSize, game.tileSize * 2, null);
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
                g2.drawImage(playerImage, x, y, game.tileSize * 2, game.tileSize, null);
            } else if (direction == "left") {
                g2.drawImage(playerImage, x - game.tileSize, y, game.tileSize * 2, game.tileSize, null);
            } else if (direction == "up") {
                g2.drawImage(playerImage, x, y - game.tileSize, game.tileSize, game.tileSize * 2, null);
            } else if (direction == "down") {
                g2.drawImage(playerImage, x, y, game.tileSize, game.tileSize * 2, null);
            }
        }

        // Draw hearts
        for (int i = 0; i < healthPoints; i++) {
            g2.drawImage(beer, 10 + (i * (game.tileSize / 2 + 5)), 20, game.tileSize / 2, game.tileSize / 2, null);
        }
    }

    public String getDirection() {
        return direction;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getPlayerX() {
        return x;
    }

    public int getPlayerY() {
        return y;
    }
}
