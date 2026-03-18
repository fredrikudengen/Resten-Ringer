package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import map.GridFactory;
import model.CollisionDetection;
import model.Player;
import model.Projectile;
import model.enemies.Enemy;
import view.DrawGame;

public class Game extends JPanel implements Runnable {

    /**
     * Difficulty modes selectable from the level-select screen.
     */
    public enum Mode {
        SOBER, TIPSY, DRUNK
    }

    // --- Screen / tile constants ---
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE              = 3;
    private static final int TILE_SIZE          = ORIGINAL_TILE_SIZE * SCALE; // 48 px
    private static final int COLS               = 16;
    private static final int ROWS               = 12;
    private static final int SCREEN_WIDTH       = TILE_SIZE * COLS;  // 768 px
    private static final int SCREEN_HEIGHT      = TILE_SIZE * ROWS;  // 576 px
    private static final int FPS                = 60;

    // --- Mutable game state ---
    private Mode      modeSelect = null;
    private int       commandNum = 0;
    private int       waveNum    = 0;
    private GameState gameState;

    // --- Infrastructure ---
    private Thread     gameThread;
    private DrawGame drawGame;
    private UpdateGame updateGame;

    private final GridFactory        gridFactory;
    private final CollisionDetection collisionDetection;
    private final GameController     gameController;
    private final Player             player;

    private final ArrayList<Projectile> projectileList = new ArrayList<>();
    private final ArrayList<Enemy>      enemyList      = new ArrayList<>();

    public Game() throws IOException {
        gridFactory        = new GridFactory(this);
        collisionDetection = new CollisionDetection(this);
        gameController     = new GameController(this);
        player             = new Player(this, gameController);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(gameController);
        this.setFocusable(true);
        this.requestFocusInWindow();

        drawGame   = new DrawGame(this, player);
        updateGame = new UpdateGame(this, player, gameController);
        gameState  = GameState.TITLE_SCREEN;
    }

    /** Starts the game loop thread. */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Retrieved from https://www.youtube.com/watch?v=VpH33Uw-_0E&t=1281s (RyiSnow)
    @Override
    public void run() {
        final double drawInterval = 1_000_000_000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            try {
                updateGame.updateGame();
            } catch (IOException e) {
                System.err.println("Error during game update: " + e.getMessage());
                e.printStackTrace();
            }

            repaint();

            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1_000_000;
                Thread.sleep((long) Math.max(0, remainingTime));
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Called by Swing on repaint. Never invoke directly.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame.drawGame((Graphics2D) g);
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    public int       getTileSize()    { return TILE_SIZE; }
    public int       getCols()        { return COLS; }
    public int       getRows()        { return ROWS; }
    public int       getScreenWidth() { return SCREEN_WIDTH; }
    public int       getScreenHeight(){ return SCREEN_HEIGHT; }

    public GameState getGameState()               { return gameState; }
    public void      setGameState(GameState state) { this.gameState = state; }

    public int  getCommandNum()       { return commandNum; }
    public void setCommandNum(int n)  { this.commandNum = n; }

    public int  getWaveNum()          { return waveNum; }
    public void setWaveNum(int n)     { this.waveNum = n; }

    public Mode getModeSelect()           { return modeSelect; }
    public void setModeSelect(Mode mode)  { this.modeSelect = mode; }

    public ArrayList<Enemy>      getEnemyList()      { return enemyList; }
    public ArrayList<Projectile> getProjectileList() { return projectileList; }

    public GridFactory        getGridFactory()        { return gridFactory; }
    public CollisionDetection getCollisionDetection() { return collisionDetection; }
}