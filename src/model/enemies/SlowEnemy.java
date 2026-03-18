package model.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import controller.Game;
import model.Player;

public class SlowEnemy extends Enemy {

    private static BufferedImage left1, left2, right1, right2, up1, up2;
    private static boolean imagesLoaded;

    public SlowEnemy(Game game, Player player) throws IOException {
        super(game, player);
        setDefaultValues();
        getImage();
    }

    @Override
    protected void setDefaultValues() {
        setSpawn();
        healthPoints      = 4;
        speed             = 1;
        isAlive           = true;
        movementDirection = Direction.RIGHT;
    }

    @Override
    protected void getImage() throws IOException {
        if (imagesLoaded) return;
        left1  = ImageIO.read(getClass().getResourceAsStream("/resources/Left_slow_enemy_1.png"));
        left2  = ImageIO.read(getClass().getResourceAsStream("/resources/Left_slow_enemy_2.png"));
        right1 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_slow_enemy_1.png"));
        right2 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_slow_enemy_2.png"));
        up1    = ImageIO.read(getClass().getResourceAsStream("/resources/Up_slow_enemy_1.png"));
        up2    = ImageIO.read(getClass().getResourceAsStream("/resources/Up_slow_enemy_2.png"));
        imagesLoaded = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = switch (movementDirection) {
            case UP              -> (imageNum == 1) ? up1    : up2;
            case LEFT            -> (imageNum == 1) ? left1  : left2;
            case RIGHT, DOWN     -> (imageNum == 1) ? right1 : right2;
        };
        g2.drawImage(image, x, y, game.getTileSize(), game.getTileSize(), null);
    }
}