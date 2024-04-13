package no.uib.inf101.sample.entity;

import java.awt.Graphics2D;
import java.awt.Toolkit;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;

public class PistolEnemy extends Entity {

    GameController gameController;
    Game game;
    Player player;

    String direction;

    int imageNum = 1;
    int imageCounter = 0;

    boolean isMoving = false;

    int xNeededToMove;
    int yNeededToMove;

    int spawnTime = 180;

    public PistolEnemy(Game game, Player player) {
        this.game = game;
        this.player = player;
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 200;
        y = 200;
        speed = 2;
        direction = "down";
    }

    public void simulateEnemyMoves() {
        xNeededToMove = player.getPlayerX() - x;
        yNeededToMove = player.getPlayerY() - y;
        if (xNeededToMove < 0) {

            x -= speed;
            xNeededToMove += speed;
            direction = "left";

        }
        if (xNeededToMove > 0) {
            x += speed;
            xNeededToMove -= speed;
            direction = "right";

        }
        if (yNeededToMove < 0) {
            y -= speed;
            yNeededToMove += speed;
            direction = "up";

        }
        if (yNeededToMove > 0) {
            y += speed;
            yNeededToMove -= speed;
            direction = "down";

        }
    }

    public void update() {

        System.out.println("Enemy position is: " + x + ", " + y);

        simulateEnemyMoves();

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

    // Getters and setters
    public int getPistolEnemyX() {
        return x;
    }

    public int getPistolEnemyY() {
        return y;
    }

    public int getPistolEnemySpeed() {
        return speed;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("images/beer.png")), x, y, game.tileSize, game.tileSize, null);
    }
}
