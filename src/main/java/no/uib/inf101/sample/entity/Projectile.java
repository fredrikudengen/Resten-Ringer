package no.uib.inf101.sample.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;

public class Projectile extends Entity {

    Game game;
    Player player;
    GameController gameController;

    public Projectile(Game game, Player player, GameController gameController) {
        this.game = game;
        this.player = player;
        this.gameController = gameController;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = player.getPlayerX();
        System.out.println("Projectile start x = " + x);

        y = player.getPlayerY();
        System.out.println("Projectile start y = " + y);

        speed = 8;
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
        if (gameController.shootUp) {
            y -= speed;
            if (y < 0) {
                gameController.shootUp = false;
            }
        } else if (gameController.shootDown) {
            y += speed;
            if (y > game.screenHeight) {
                gameController.shootDown = false;
            }
        } else if (gameController.shootLeft) {
            x -= speed;
            if (x < 0) {
                gameController.shootLeft = false;
            }
        } else if (gameController.shootRight) {
            x += speed;
            if (x > game.screenWidth) {
                gameController.shootRight = false;
            }
        }
    }

    public void draw(Graphics2D g2) {

        // Draw projectile
        g2.setColor(Color.RED);
        g2.fillRect(x, y, game.tileSize, game.tileSize);

    }
}