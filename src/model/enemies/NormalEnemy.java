package model.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import controller.Game;
import model.Player;

public class NormalEnemy extends Enemy {

    private static BufferedImage up1, up2, down1, down2;
    private static boolean imagesLoaded;

    public NormalEnemy(Game game, Player player) throws IOException {
        super(game, player);
        setDefaultValues();
        getImage();
    }

    @Override
    protected void setDefaultValues() {
        setSpawn();
        healthPoints      = 3;
        speed             = 2;
        isAlive           = true;
        movementDirection = Direction.RIGHT;
    }

    @Override
    protected void getImage() throws IOException {
        if (imagesLoaded) return;
        up1   = ImageIO.read(getClass().getResourceAsStream("/resources/Up_normal_enemy_1.png"));
        up2   = ImageIO.read(getClass().getResourceAsStream("/resources/Up_normal_enemy_2.png"));
        down1 = ImageIO.read(getClass().getResourceAsStream("/resources/Down_normal_enemy_1.png"));
        down2 = ImageIO.read(getClass().getResourceAsStream("/resources/Down_normal_enemy_2.png"));
        imagesLoaded = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        // No distinct left/right sprites — uses down frames for all horizontal movement
        BufferedImage image = switch (movementDirection) {
            case UP              -> (imageNum == 1) ? up1   : up2;
            case DOWN, LEFT, RIGHT -> (imageNum == 1) ? down1 : down2;
        };
        g2.drawImage(image, x, y, game.getTileSize(), game.getTileSize(), null);
    }
}