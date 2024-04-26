package no.uib.inf101.sample;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.Projectile;
import no.uib.inf101.sample.entity.enemy.Enemy;
import no.uib.inf101.sample.entity.enemy.FastEnemy;
import no.uib.inf101.sample.entity.enemy.NormalEnemy;
import no.uib.inf101.sample.entity.enemy.SlowEnemy;
import no.uib.inf101.sample.map.TileFactory;
import no.uib.inf101.sample.sound.GameSong;
import no.uib.inf101.sample.sound.SoundPlayer;

public class Game extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile size
    final int scale = 3; // upscale by 3 to make it larger on screen

    private final int tileSize = originalTileSize * scale; // 48x48 tile size
    private final int cols = 16;
    private final int rows = 12;
    private final int screenWidth = tileSize * cols; // 768 pixels
    private final int screenHeight = tileSize * rows; // 576 pixels

    Projectile projectile;

    private ArrayList<Projectile> projectileList = new ArrayList<>();
    private ArrayList<Enemy> enemyList = new ArrayList<>();

    // FPS
    private int fps = 60;
    private String modeSelect = "";

    private int commandNum = 0;
    private int waveNumber = 0;

    Game game;
    Thread gameThread;
    GameState gameState;
    TileFactory tileFactory = new TileFactory(this);
    public CollisionDetection collisionDetection = new CollisionDetection(this);
    SoundPlayer soundPlayer = new SoundPlayer();
    GameController gameController = new GameController(this);
    Player player = new Player(this, gameController);
    private DrawGame drawGame;
    private UpdateGame updateGame;

    public Game() {
        System.out.println(screenWidth);
        this.game = this;
        drawGame = new DrawGame(this, player);
        updateGame = new UpdateGame(game, player, gameController);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // improves rendering, doesn't display the render before the whole screen is
                                      // ready
        this.addKeyListener(gameController);
        this.setFocusable(true);
        this.requestFocusInWindow();
        gameState = GameState.TITLE_SCREEN;
    }

    public void startGameThread() {

        gameThread = new Thread(this); // Passing gamepanel to Thread to instatiate it
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps; // One second in nanoseconds divided by number of fps we want (draws a
                                                // frame every 0.01666 seconds)

        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            // 1 UPDATE: update information such as character positions
            updateGame.updateGame();

            // 2 DRAW: draw the screen with the updated information
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
                // TODO Auto-generated catch block
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

    public GameState getGameState() {
        return this.gameState;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Projectile> getProjectileList() {
        return this.projectileList;
    }
    public int getWaveNum(){
        return this.waveNumber;
    }
    public void setWaveNum(int waveNum){
        this.waveNumber = waveNum;
    }
}
