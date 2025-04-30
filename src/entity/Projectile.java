package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Game;
import controller.GameController;

public class Projectile extends Entity implements DrawAndUpdate {

    private Game game;
    private Player player;
    private GameController gameController;
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private String shotDirection;
    private final int originalBulletSize = 4; // 8x8 bullet size
    private final int bulletSize = originalBulletSize * 3; // 12x12 tile size

    public Projectile(Game game, Player player, GameController gameController, String shotDirection) throws IOException {
        this.game = game;
        this.player = player;
        this.gameController = gameController;
        this.shotDirection = shotDirection;
        setDefaultValues();
        getImage();
    }

    @Override
    public void setDefaultValues() {
        x = player.x;
        y = player.y;
        speed = 8;
        isAlive = true;
    }

    @Override
    protected void getImage() throws IOException {
        up1 = ImageIO.read(getClass().getResourceAsStream("/resources/Up_bullet_1.png"));
        up2 = ImageIO.read(getClass().getResourceAsStream("/resources/Up_bullet_2.png"));
        down1 = ImageIO.read(getClass().getResourceAsStream("/resources/Down_bullet_1.png"));
        down2 = ImageIO.read(getClass().getResourceAsStream("/resources/Down_bullet_2.png"));
        left1 = ImageIO.read(getClass().getResourceAsStream("/resources/Left_bullet_1.png"));
        left2 = ImageIO.read(getClass().getResourceAsStream("/resources/Left_bullet_2.png"));
        right1 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_bullet_1.png"));
        right2 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_bullet_2.png"));
    }

    /**
     * Uses rectanglesOverlap to check if Projectile collides with Enemy.
     * 
     * @param i index in enemyList.
     * @return boolean whether Projectile hit enemy or not.
     */
    public boolean projectileCollidesWithEnemy(int i) {
        if (rectanglesOverlap(game.getEnemyList().get(i).x,
                game.getEnemyList().get(i).y,
                game.getEnemyList().get(i).x + game.getTileSize(),
                game.getEnemyList().get(i).y + game.getTileSize(), x,
                y,
                x + bulletSize,
                y + bulletSize)) {
            game.getEnemyList().get(i).healthPoints--;
            if (game.getEnemyList().get(i).healthPoints <= 0) {
                game.getEnemyList().get(i).isAlive = false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update() {
        if (shotDirection == "up") {
            y -= speed;
            if (y < 0) {
                gameController.shootUp = false;
            }
        } else if (shotDirection == "down") {
            y += speed;
            if (y > game.getScreenHeight()) {
                gameController.shootDown = false;
            }
        } else if (shotDirection == "left") {
            x -= speed;
            if (x < 0) {
                gameController.shootLeft = false;
            }
        } else if (shotDirection == "right") {
            x += speed;
            if (x > game.getScreenWidth()) {
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

    @Override
    public void draw(Graphics2D g2) {
        if (isAlive) {
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
                g2.drawImage(image, x + 40, y + 20, bulletSize * 2, bulletSize, null);
            } else if (shotDirection == "left") {
                g2.drawImage(image, x - 40, y + 20, bulletSize * 2, bulletSize, null);
            } else if (shotDirection == "up") {
                g2.drawImage(image, x + 25, y - 20, bulletSize, bulletSize * 2, null);
            } else if (shotDirection == "down") {
                g2.drawImage(image, x, y + 30, bulletSize, bulletSize * 2, null);
            }
        }
    }
}