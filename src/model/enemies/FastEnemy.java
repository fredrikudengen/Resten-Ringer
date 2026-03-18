package model.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import controller.Game;
import model.Player;

public class FastEnemy extends Enemy {

    private static BufferedImage up1, up2, left1, left2, right1, right2;
    private static boolean imagesLoaded;

    public FastEnemy(Game game, Player player) throws IOException {
        super(game, player);
        setDefaultValues();
        getImage();
    }

    @Override
    protected void setDefaultValues() {
        setSpawn();
        healthPoints      = 2;
        speed             = 3;
        isAlive           = true;
        movementDirection = Direction.RIGHT;
    }

    @Override
    protected void getImage() throws IOException {
        if (imagesLoaded) return;
        up1    = ImageIO.read(getClass().getResourceAsStream("/resources/Up_police_1.png"));
        up2    = ImageIO.read(getClass().getResourceAsStream("/resources/Up_police_2.png"));
        left1  = ImageIO.read(getClass().getResourceAsStream("/resources/Left_police_1.png"));
        left2  = ImageIO.read(getClass().getResourceAsStream("/resources/Left_police_2.png"));
        right1 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_police_1.png"));
        right2 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_police_2.png"));
        imagesLoaded = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        // No distinct down sprite — uses left frames for downward movement
        BufferedImage image = switch (movementDirection) {
            case UP              -> (imageNum == 1) ? up1    : up2;
            case RIGHT           -> (imageNum == 1) ? right1 : right2;
            case LEFT, DOWN      -> (imageNum == 1) ? left1  : left2;
        };
        g2.drawImage(image, x, y, game.getTileSize(), game.getTileSize(), null);
    }
}