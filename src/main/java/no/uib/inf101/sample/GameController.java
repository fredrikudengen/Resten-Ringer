package no.uib.inf101.sample;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import no.uib.inf101.sample.entity.Player;

public class GameController implements java.awt.event.KeyListener {

    Game game;
    Projectile projectile;
    Player player;

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public GameController(Game game) {
        this.game = game;
        this.projectile = new Projectile(0, 0, 0, game);
        game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                projectile.handleMouseClick(e.getX(), e.getY());
            }
        });

        // Pass the GameController instance to the Player constructor
        // and set it accordingly
        this.game.player = new Player(0, 0, 0, game, this);
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
    }
}
