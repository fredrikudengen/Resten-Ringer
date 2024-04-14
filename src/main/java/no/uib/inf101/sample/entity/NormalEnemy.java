package no.uib.inf101.sample.entity;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Random;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;

public class NormalEnemy extends Entity {

    GameController gameController;
    Game game;
    Player player;

    String direction;

    int imageNum = 1;
    int imageCounter = 0;

    boolean isMoving = false;

    int xNeededToMove;
    int yNeededToMove;

    public NormalEnemy(Game game, Player player) {
        this.game = game;
        this.player = player;
        setDefaultValues();
    }

    public void setDefaultValues() {
        Random rand = new Random();
        
        x = rand.nextInt(game.screenWidth) ;
        y = rand.nextInt(game.screenHeight);
        healthPoints = 3;
        speed = 2;
        direction = "down";
        isAlive = true;
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

        simulateEnemyMoves();

        if (healthPoints <= 0) {
            isAlive = false;
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

    // Getters and setters
    public int getNormalEnemyX() {
        return x;
    }

    public int getNormalEnemyY() {
        return y;
    }

    public int getNormalEnemySpeed() {
        return speed;
    }

    public void draw(Graphics2D g2) {
        if (isAlive == true) {
        g2.drawImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("images/beer.png")), x, y, game.tileSize, game.tileSize, null);
        }
    }
}
