package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Game;
import controller.GameController;
import model.enemies.Enemy;

public class Projectile extends Entity implements DrawAndUpdate {

    private static final int BULLET_SIZE = 4 * 3; // original 4px scaled by 3 = 12px

    private final Game             game;
    private final GameController   gameController;
    private final Direction        shotDirection;

    private static BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private static boolean imagesLoaded = false;

    public Projectile(Game game, Player player, GameController gameController,
                      Direction shotDirection) throws IOException {
        this.game           = game;
        this.gameController = gameController;
        this.shotDirection  = shotDirection;
        setDefaultValues(player);
        getImage();
    }

    private void setDefaultValues(Player player) {
        x       = player.getX();
        y       = player.getY();
        speed   = 8;
        isAlive = true;
    }

    @Override
    protected void setDefaultValues() { /* required by Entity; init done via setDefaultValues(Player) */ }

    @Override
    protected void getImage() throws IOException {
        if (imagesLoaded) return;
        up1    = ImageIO.read(getClass().getResourceAsStream("/resources/Up_bullet_1.png"));
        up2    = ImageIO.read(getClass().getResourceAsStream("/resources/Up_bullet_2.png"));
        down1  = ImageIO.read(getClass().getResourceAsStream("/resources/Down_bullet_1.png"));
        down2  = ImageIO.read(getClass().getResourceAsStream("/resources/Down_bullet_2.png"));
        left1  = ImageIO.read(getClass().getResourceAsStream("/resources/Left_bullet_1.png"));
        left2  = ImageIO.read(getClass().getResourceAsStream("/resources/Left_bullet_2.png"));
        right1 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_bullet_1.png"));
        right2 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_bullet_2.png"));
        imagesLoaded = true;
    }

    /**
     * Returns true if this projectile overlaps the given enemy,
     * and applies damage to it if so.
     */
    public boolean projectileCollidesWithEnemy(Enemy enemy) {
        int ts = game.getTileSize();
        return rectanglesOverlap(
                enemy.getX(), enemy.getY(), enemy.getX() + ts, enemy.getY() + ts,
                x, y, x + BULLET_SIZE, y + BULLET_SIZE);
    }

    @Override
    public void update() {
        switch (shotDirection) {
            case UP    -> y -= speed;
            case DOWN  -> y += speed;
            case LEFT  -> x -= speed;
            case RIGHT -> x += speed;
        }

        if (++imageCounter > 10) {
            imageNum     = (imageNum == 1) ? 2 : 1;
            imageCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!isAlive) return;

        BufferedImage image = switch (shotDirection) {
            case UP    -> (imageNum == 1) ? up1    : up2;
            case DOWN  -> (imageNum == 1) ? down1  : down2;
            case LEFT  -> (imageNum == 1) ? left1  : left2;
            case RIGHT -> (imageNum == 1) ? right1 : right2;
        };

        int bs = BULLET_SIZE;
        switch (shotDirection) {
            case RIGHT -> g2.drawImage(image, x + 40, y + 20, bs * 2, bs,     null);
            case LEFT  -> g2.drawImage(image, x - 40, y + 20, bs * 2, bs,     null);
            case UP    -> g2.drawImage(image, x + 25, y - 20, bs,     bs * 2, null);
            case DOWN  -> g2.drawImage(image, x,      y + 30, bs,     bs * 2, null);
        }
    }
}