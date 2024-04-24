package no.uib.inf101.sample;

import java.awt.event.KeyEvent;

import no.uib.inf101.sample.sound.SoundPlayer;

public class GameController implements java.awt.event.KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean shootUp, shootDown, shootLeft, shootRight;
    public boolean shotKeyPressed;
    public boolean enterKeyPressed;
    Game game;

    public GameController(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.gameState == GameState.WAVE_COMPLETE) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                enterKeyPressed = true;
            }
        }
        if (game.gameState == GameState.TITLE_SCREEN) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                game.commandNum--;
                if (game.commandNum < 0) {
                    game.commandNum = 1;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                game.commandNum++;
                if (game.commandNum > 1) {
                    game.commandNum = 0;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (game.commandNum == 0) {
                    SoundPlayer.glassShatter();
                    game.gameState = GameState.ACTIVE_GAME;

                } else if (game.commandNum == 1) {
                    System.exit(0);
                }

            }
        }
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
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            shootDown = true;
            shotKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            shootLeft = true;
            shotKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            shootRight = true;
            shotKeyPressed = true;
        }
    }

    public String getShotDirection() {
        if (shootDown) {
            return "down";
        } else if (shootLeft) {
            return "left";
        } else if (shootRight) {
            return "right";
        } else {
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
            shotKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            shootDown = false;
            shotKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            shootLeft = false;
            shotKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            shootRight = false;
            shotKeyPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enterKeyPressed = false;
        }

    }
}
