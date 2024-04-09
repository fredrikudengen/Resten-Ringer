package no.uib.inf101.sample.entity;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;

public class Player {

    GameController gameController;
    Game game;

    private int playerX;
    private int playerY;
    private int playerSpeed;

    public Player(int x, int y, int speed, Game game, GameController gameController) {
        this.playerX = x;
        this.playerY = y;
        this.playerSpeed = speed;
        this.game = game;
        this.gameController = gameController;
    }

    public void update() {
        if (gameController.upPressed == true) {
            playerY -= playerSpeed;
            System.out.println("Player position is:" + playerX + "," + playerY);
        }
        if (gameController.downPressed == true) {
            playerY += playerSpeed;
            System.out.println("Player position is:" + playerX + "," + playerY);
        }
        if (gameController.leftPressed == true) {
            playerX -= playerSpeed;
            System.out.println("Player position is:" + playerX + "," + playerY);
        }
        if (gameController.rightPressed == true) {
            playerX += playerSpeed;
            System.out.println("Player position is:" + playerX + "," + playerY);
        }
    }

    // Getters and setters
    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int x) {
        this.playerX = x;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int y) {
        this.playerY = y;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int speed) {
        this.playerSpeed = speed;
    }




}
