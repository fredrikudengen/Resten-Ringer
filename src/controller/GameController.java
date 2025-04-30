package controller;

import java.awt.event.KeyEvent;

import model.Game;
import model.GameState;

public class GameController implements java.awt.event.KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean shootUp, shootDown, shootLeft, shootRight;
    public boolean shotKeyPressed;
    public boolean enterKeyPressed;
    private Game game;
    public boolean rKeyPressed;

    public GameController(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        GameState gameState = game.getGameState();

        if (gameState == GameState.TITLE_SCREEN) {
            // Changes commandNum (which option to press) based on up (W key) or down (S
            // key)
            if (e.getKeyCode() == KeyEvent.VK_W) {
                game.setCommandNum(game.getCommandNum() - 1);
                if (game.getCommandNum() < 0) {
                    game.setCommandNum(2);
                }
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                game.setCommandNum(game.getCommandNum() + 1);
                if (game.getCommandNum() > 2) {
                    game.setCommandNum(0);
                }
            }
            // Changes gameState according to which option you chose
            if (game.getCommandNum() == 0 && e.getKeyCode() == KeyEvent.VK_ENTER) {
                game.setGameState(GameState.ACTIVE_GAME);
            } else if (game.getCommandNum() == 1 && e.getKeyCode() == KeyEvent.VK_ENTER) {
                game.setGameState(GameState.LEVEL_SELECT);
            } else if (game.getCommandNum() == 2 && e.getKeyCode() == KeyEvent.VK_ENTER) {
                System.exit(0);
            }
        }
        if (gameState == GameState.LEVEL_SELECT) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                game.setCommandNum(game.getCommandNum() + 1);
                ;
                if (game.getCommandNum() > 2) {
                    game.setCommandNum(0);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                game.setCommandNum(game.getCommandNum() - 1);
                if (game.getCommandNum() < 0) {
                    game.setCommandNum(2);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                game.setGameState(GameState.TITLE_SCREEN);
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (game.getCommandNum() == 0) {
                    game.setModeSelect("sober mode");
                    game.setGameState(GameState.ACTIVE_GAME);
                }
                if (game.getCommandNum() == 1) {
                    game.setModeSelect("tipsy mode");
                    game.setGameState(GameState.ACTIVE_GAME);
                }
                if (game.getCommandNum() == 2) {
                    game.setModeSelect("drunk mode");
                    game.setGameState(GameState.ACTIVE_GAME);
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            rKeyPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_A) {
            leftPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_D) {
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enterKeyPressed = true;
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
        if (e.getKeyCode() == KeyEvent.VK_R) {
            rKeyPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enterKeyPressed = false;
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
}