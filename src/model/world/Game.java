package model.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import controller.GameController;
import controller.GameState;
import model.map.GridFactory;
import model.entities.CollisionDetection;
import model.entities.Player;
import model.entities.Projectile;
import model.entities.enemies.Enemy;
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
    private DrawGame   drawGame;
    private UpdateGame updateGame;

    private final GridFactory        gridFactory;
    private final CollisionDetection collisionDetection;
    private final GameController     gameController;
    private final Player             player;

    private final List<Projectile> projectileList = Collections.synchronizedList(new ArrayList<>());
    private final List<Enemy>      enemyList      = Collections.synchronizedList(new ArrayList<>());

    /**
     * IOException comes only from DrawGame.loadImages() — the last remaining
     * place where images are loaded at construction time rather than in a static
     * initializer. All entity constructors (Player, Enemy subclasses, Projectile)
     * were converted to static-initializer sprite loading in a previous pass.
     */
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
            // No longer catches IOException: UpdateGame.updateGame() was cleaned
            // of all throwing paths after enemy/projectile constructor changes.
            updateGame.updateGame();
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

    public int getTileSize()     { return TILE_SIZE; }
    public int getCols()         { return COLS; }
    public int getRows()         { return ROWS; }
    public int getScreenWidth()  { return SCREEN_WIDTH; }
    public int getScreenHeight() { return SCREEN_HEIGHT; }

    public GameState getGameState()                { return gameState; }
    public void      setGameState(GameState state) { this.gameState = state; }

    public int  getCommandNum()      { return commandNum; }
    public void setCommandNum(int n) { this.commandNum = n; }

    public int  getWaveNum()      { return waveNum; }
    public void setWaveNum(int n) { this.waveNum = n; }

    public Mode getModeSelect()          { return modeSelect; }
    public void setModeSelect(Mode mode) { this.modeSelect = mode; }

    public List<Enemy>      getEnemyList()      { return enemyList; }
    public List<Projectile> getProjectileList() { return projectileList; }

    public GridFactory        getGridFactory()        { return gridFactory; }
    public CollisionDetection getCollisionDetection() { return collisionDetection; }
}