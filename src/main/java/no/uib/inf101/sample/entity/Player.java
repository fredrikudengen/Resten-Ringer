package no.uib.inf101.sample.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;

public class Player extends Entity{

    GameController gameController;
    Game game;

    public Player(Game game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;

        setDefaultValues();
    }

    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        projectile = new Projectile(game, this);
    }

    public void draw(Graphics2D g2) {
        
        // Draw player
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, game.tileSize, game.tileSize);

    }

    public void update() {
        if (gameController.upPressed == true) {
            y -= speed;
            System.out.println("Player position is:" + x + "," + y);
        }
        if (gameController.downPressed == true) {
            y += speed;
            System.out.println("Player position is:" + x + "," + y);
        }
        if (gameController.leftPressed == true) {
            x -= speed;
            System.out.println("Player position is:" + x + "," + y);
        }
        if (gameController.rightPressed == true) {
            x += speed;
            System.out.println("Player position is:" + x + "," + y);
        }
        if (gameController.shotKeyPressed == true) {
            projectile.shootProjectile(300, 0);

            game.projectileList.add(projectile);
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
