package no.uib.inf101.sample;

import java.awt.event.KeyEvent;

public class GameController implements java.awt.event.KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean shootUp, shootDown, shootLeft, shootRight;
    public boolean shotKeyPressed;
    public boolean enterKeyPressed;
    public boolean RKeyPressed;
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
                game.gameState = GameState.ACTIVE_GAME;
            }
        }
        if (game.gameState == GameState.LEVEL_SELECT) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                game.setCommandNum(game.getCommandNum() + 1);;
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
                game.gameState = GameState.TITLE_SCREEN;
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (game.getCommandNum() == 0) {
                    game.setModeSelect("sober mode"); 
                    game.gameState = GameState.ACTIVE_GAME;
                }
                if (game.getCommandNum() == 1) {
                    game.setModeSelect("tipsy mode"); 
                    game.gameState = GameState.ACTIVE_GAME;
                }
                if (game.getCommandNum() == 2) {
                    game.setModeSelect("drunk mode"); 
                    game.gameState = GameState.ACTIVE_GAME;
                }
            }
        }
        if (game.gameState == GameState.VICTORY) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                game.gameState = GameState.TITLE_SCREEN;
            }
        }
        if (game.gameState == GameState.TITLE_SCREEN) {
            if (e.getKeyCode() == KeyEvent.VK_W) {

                game.setCommandNum(game.getCommandNum() - 1);
                if (game.getCommandNum() < 0) {
                    game.setCommandNum(2); 
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                game.setCommandNum(game.getCommandNum() + 1);
                if (game.getCommandNum() > 2) {
                    game.setCommandNum(0);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (game.getCommandNum() == 0) {
                    game.gameState = GameState.ACTIVE_GAME;
                }
                if (game.getCommandNum() == 1) {
                    game.gameState = GameState.LEVEL_SELECT;
                }
                if (game.getCommandNum() == 2) {
                    System.exit(0);
                }
            }
        }
        if (game.gameState == GameState.GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                game.gameState = GameState.TITLE_SCREEN;
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enterKeyPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            RKeyPressed = true;
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
        if (e.getKeyCode() == KeyEvent.VK_R) {
            RKeyPressed = false;
        }

    }
}
