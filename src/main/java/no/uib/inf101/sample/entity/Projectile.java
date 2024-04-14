package no.uib.inf101.sample.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;

public class Projectile extends Entity {

    Game game;
    Player player;
    GameController gameController;

    String shotDirection;
    int imageNum = 1;
    int imageCounter = 0;

    private Image up1 = Toolkit.getDefaultToolkit()
            .getImage(getClass().getResource("images/Up_bullet_1.png"));
    private Image up2 = Toolkit.getDefaultToolkit()
            .getImage(getClass().getResource("images/Up_bullet_2.png"));
    private Image down1 = Toolkit.getDefaultToolkit()
            .getImage(getClass().getResource("images/Down_bullet_1.png"));
    private Image down2 = Toolkit.getDefaultToolkit()
            .getImage(getClass().getResource("images/Down_bullet_2.png"));
    private Image left1 = Toolkit.getDefaultToolkit()
            .getImage(getClass().getResource("images/Left_bullet_1.png"));
    private Image left2 = Toolkit.getDefaultToolkit()
            .getImage(getClass().getResource("images/Left_bullet_2.png"));
    private Image right1 = Toolkit.getDefaultToolkit()
            .getImage(getClass().getResource("images/Right_bullet_1.png"));
    private Image right2 = Toolkit.getDefaultToolkit()
            .getImage(getClass().getResource("images/Right_bullet_2.png"));

    public Projectile(Game game, Player player, GameController gameController, String shotDirection, NormalEnemy normalEnemy) {
        this.game = game;
        this.player = player;
        this.gameController = gameController;
        this.shotDirection = shotDirection;
        this.normalEnemy = normalEnemy;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = player.getPlayerX();

        y = player.getPlayerY();

        speed = 8;

        healthPoints = 40;

        isAlive = true;
    }

    public boolean ProjectileCollidesWithEnemy() {
        if (rectanglesOverlap(normalEnemy.getNormalEnemyX() - (game.tileSize / 2),
                normalEnemy.getNormalEnemyY() - (game.tileSize / 2),
                normalEnemy.getNormalEnemyX() + (game.tileSize / 2),
                normalEnemy.getNormalEnemyY() + (game.tileSize / 2), x - (game.bulletSize / 2),
                y + (game.bulletSize / 2),
                x + (game.bulletSize / 2), y + (game.bulletSize / 2))) {
            normalEnemy.healthPoints--;
            return true;
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

        if (xRight < x2Left || x2Right < xLeft) {
            return false;
        } else if (yBottom < y2Top || y2Bottom < yTop) {
            return false;
        } else {
            return true;
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
        if (shotDirection == "up") {
            y -= speed;
            if (y < 0) {
                gameController.shootUp = false;
            }
        } else if (shotDirection == "down") {
            y += speed;
            if (y > game.screenHeight) {
                gameController.shootDown = false;
            }
        } else if (shotDirection == "left") {
            x -= speed;
            if (x < 0) {
                gameController.shootLeft = false;
            }
        } else if (shotDirection == "right") {
            x += speed;
            if (x > game.screenWidth) {
                gameController.shootRight = false;
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

    public boolean projectTileHitEnemy() {
        if (ProjectileCollidesWithEnemy() == true) {
            isAlive = false;
            return true;
        } else {
            return false;
        }
    }

    public void draw(Graphics2D g2) {
        if (isAlive && !projectTileHitEnemy()) {
            Image image = null;
            switch (shotDirection) {
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
            // Draw Projectile
            if (shotDirection == "right") {
                g2.drawImage(image, x + 40, y + 20, game.bulletSize * 2, game.bulletSize, null);
            } else if (shotDirection == "left") {
                g2.drawImage(image, x - 40, y + 20, game.bulletSize * 2, game.bulletSize, null);
            } else if (shotDirection == "up") {
                g2.drawImage(image, x + 25, y - 20, game.bulletSize, game.bulletSize * 2, null);
            } else if (shotDirection == "down") {
                g2.drawImage(image, x, y + 30, game.bulletSize, game.bulletSize * 2, null);
            }
        }
    }
}