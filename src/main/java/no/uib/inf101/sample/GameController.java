package no.uib.inf101.sample;

import java.awt.event.KeyEvent;

public class GameController implements java.awt.event.KeyListener {

    Game game;

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean shootUp, shootDown, shootLeft, shootRight;
    public boolean shotKeyPressed;

    public GameController(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            shootUp = true;
            shotKeyPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            shootDown = true;
            shotKeyPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            shootLeft = true;
            shotKeyPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            shootRight = true;
            shotKeyPressed = true;
        }
    }

    public String getShotDirection() {
        if (shootDown) {
            return "down";
        }
        else if (shootLeft) {
            return "left";
        }
        else if (shootRight) {
            return "right";
        }
        else {
            return "up";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            shootUp = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            shootDown = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            shootLeft = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            shootRight = false;
        }
    }
}
