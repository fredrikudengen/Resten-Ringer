package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Entity.Direction;

public class GameController implements KeyListener {

    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean shootUp, shootDown, shootLeft, shootRight;
    private boolean shotKeyPressed;
    private boolean enterKeyPressed;
    private boolean rKeyPressed;

    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (game.getGameState()) {
            case TITLE_SCREEN -> handleTitleScreenInput(key);
            case LEVEL_SELECT -> handleLevelSelectInput(key);
            case ACTIVE_GAME  -> handleGameplayInput(key);
        }

        // These flags are relevant across all states
        if (key == KeyEvent.VK_ENTER) enterKeyPressed = true;
        if (key == KeyEvent.VK_R)     rKeyPressed = true;
    }

    private void handleTitleScreenInput(int key) {
        if (key == KeyEvent.VK_W) {
            int cmd = game.getCommandNum() - 1;
            game.setCommandNum(cmd < 0 ? 2 : cmd);
        } else if (key == KeyEvent.VK_S) {
            int cmd = game.getCommandNum() + 1;
            game.setCommandNum(cmd > 2 ? 0 : cmd);
        } else if (key == KeyEvent.VK_ENTER) {
            switch (game.getCommandNum()) {
                case 0 -> game.setGameState(GameState.ACTIVE_GAME);
                case 1 -> game.setGameState(GameState.LEVEL_SELECT);
                case 2 -> System.exit(0);
            }
        }
    }

    private void handleLevelSelectInput(int key) {
        if (key == KeyEvent.VK_D) {
            int cmd = game.getCommandNum() + 1;
            game.setCommandNum(cmd > 2 ? 0 : cmd);
        } else if (key == KeyEvent.VK_A) {
            int cmd = game.getCommandNum() - 1;
            game.setCommandNum(cmd < 0 ? 2 : cmd);
        } else if (key == KeyEvent.VK_ESCAPE) {
            game.setGameState(GameState.TITLE_SCREEN);
        } else if (key == KeyEvent.VK_ENTER) {
            switch (game.getCommandNum()) {
                case 0 -> { game.setModeSelect(Game.Mode.SOBER); game.setGameState(GameState.ACTIVE_GAME); }
                case 1 -> { game.setModeSelect(Game.Mode.TIPSY); game.setGameState(GameState.ACTIVE_GAME); }
                case 2 -> { game.setModeSelect(Game.Mode.DRUNK); game.setGameState(GameState.ACTIVE_GAME); }
            }
        }
    }

    private void handleGameplayInput(int key) {
        switch (key) {
            case KeyEvent.VK_W -> upPressed    = true;
            case KeyEvent.VK_A -> leftPressed  = true;
            case KeyEvent.VK_S -> downPressed  = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_UP    -> { shootUp    = true; shotKeyPressed = true; }
            case KeyEvent.VK_DOWN  -> { shootDown  = true; shotKeyPressed = true; }
            case KeyEvent.VK_LEFT  -> { shootLeft  = true; shotKeyPressed = true; }
            case KeyEvent.VK_RIGHT -> { shootRight = true; shotKeyPressed = true; }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> upPressed    = false;
            case KeyEvent.VK_A -> leftPressed  = false;
            case KeyEvent.VK_S -> downPressed  = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_UP    -> shootUp    = false;
            case KeyEvent.VK_DOWN  -> shootDown  = false;
            case KeyEvent.VK_LEFT  -> shootLeft  = false;
            case KeyEvent.VK_RIGHT -> shootRight = false;
            case KeyEvent.VK_R     -> rKeyPressed    = false;
            case KeyEvent.VK_ENTER -> enterKeyPressed = false;
        }
        // Recompute rather than tracking per-key release — fixes multi-key release bug
        shotKeyPressed = shootUp || shootDown || shootLeft || shootRight;
    }

    /**
     * Returns the active shooting direction.
     * Defaults to UP if no shoot key is held.
     */
    public Direction getShotDirection() {
        if (shootDown)  return Direction.DOWN;
        if (shootLeft)  return Direction.LEFT;
        if (shootRight) return Direction.RIGHT;
        return Direction.UP;
    }

    public boolean isUpPressed()       { return upPressed; }
    public boolean isDownPressed()     { return downPressed; }
    public boolean isLeftPressed()     { return leftPressed; }
    public boolean isRightPressed()    { return rightPressed; }
    public boolean isShotKeyPressed()  { return shotKeyPressed; }
    public boolean isEnterPressed()    { return enterKeyPressed; }
    public boolean isRKeyPressed()     { return rKeyPressed; }
}