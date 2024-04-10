package no.uib.inf101.sample.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;

public class Player extends Entity {

    GameController gameController;
    Game game;

    private Image playerImage;

    public Player(Game game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;
        this.playerImage = Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("images/Down_shotgun_standing.png"));
        setDefaultValues();
    }

    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        projectile = new Projectile(game, this, gameController);
    }

    public void draw(Graphics2D g2) {

        // Draw player
        g2.drawImage(playerImage, x, y, game.tileSize, game.tileSize, null);
    }

    public void update() {
        if (gameController.upPressed == true) {
            y -= speed;
            System.out.println("Player position is:" + x + "," + y);
            if (y <= -48) {
                y = 576 + 36;
            }
        }
        if (gameController.downPressed == true) {
            y += speed;
            System.out.println("Player position is:" + x + "," + y);
            if (y >= 576 + 36) {
                y = -48;
            }
        }
        if (gameController.leftPressed == true) {
            x -= speed;
            System.out.println("Player position is:" + x + "," + y);
            if (x <= -48) {
                x = 768 + 36;
            }
        }
        if (gameController.rightPressed == true) {
            x += speed;
            System.out.println("Player position is:" + x + "," + y);
            if (x >= 768 + 36) {
                x = -48;
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
}
