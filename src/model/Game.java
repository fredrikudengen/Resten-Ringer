package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.GameController;
import map.GridFactory;
import entity.CollisionDetection;
import entity.Player;
import entity.Projectile;
import entity.enemy.Enemy;

public class Game extends JPanel implements Runnable {

    private final int originalTileSize = 16; // 16x16 tile size
    private final int scale = 3; // upscale by 3 to make it larger on screen
    private final int tileSize = originalTileSize * scale; // 48x48 tile size
    private final int cols = 16;
    private final int rows = 12;
    private final int screenWidth = tileSize * cols; // 768 pixels
    private final int screenHeight = tileSize * rows; // 576 pixels
    private final int FPS = 60;

    private String modeSelect = "";

    private int commandNum = 0;
    private int waveNum = 0;

    private Game game;
    private Thread gameThread;
    private GameState gameState;
    private DrawGame drawGame;
    private UpdateGame updateGame;
    public GridFactory gridFactory = new GridFactory(this);
    public CollisionDetection collisionDetection = new CollisionDetection(this);
    private GameController gameController = new GameController(this);
    private Player player = new Player(this, gameController);

    private ArrayList<Projectile> projectileList = new ArrayList<>();
    private ArrayList<Enemy> enemyList = new ArrayList<>();

    public Game() throws IOException {
        this.game = this;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(gameController);
        this.setFocusable(true);
        this.requestFocusInWindow();
        drawGame = new DrawGame(this, player);
        updateGame = new UpdateGame(game, player, gameController);
        gameState = GameState.TITLE_SCREEN;
    }

    /**
     * Starts a new Thread used to update and draw the game 60 times a second.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Retrieved from
    // https://www.youtube.com/watch?v=VpH33Uw-_0E&t=1281s&ab_channel=RyiSnow 08.04
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // One second in nanoseconds divided by number of fps we want (draws a
                                                // frame every 0.01666 seconds)

        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            try {
                updateGame.updateGame();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // converts to milliseconds from nanoseconds which is required
                                                         // in sleep

                // If the update and repaint took more than 1 frame (0.01666 seconds), then no
                // sleep is required
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); // Stops the game loop for the remainingTime before nextDrawtime

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // The paintComponent method is called by the Java Swing framework every time
    // either the window opens or resizes, or we call .repaint() on this object.
    // Note: NEVER call paintComponent directly yourself
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        drawGame.drawGame(g2);
    }

    // Getters and setters
    public int getCommandNum() {
        return this.commandNum;
    }

    public void setCommandNum(int num) {
        this.commandNum = num;
    }

    public String getModeSelect() {
        return this.modeSelect;
    }

    public void setModeSelect(String modeSelect) {
        this.modeSelect = modeSelect;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public int getCols() {
        return this.cols;
    }

    public int getRows() {
        return this.rows;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public ArrayList<Enemy> getEnemyList() {
        return this.enemyList;
    }

    public void addToEnemyList(Enemy enemy) {
        enemyList.add(enemy);
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public ArrayList<Projectile> getProjectileList() {
        return this.projectileList;
    }
    public int getWaveNum() {
        return this.waveNum;
    }
    public void setWaveNum(int waveNum) {
        this.waveNum = waveNum;
    }
}
