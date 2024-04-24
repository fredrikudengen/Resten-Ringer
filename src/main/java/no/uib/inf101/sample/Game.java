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

public class Game extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile size
    final int originalBulletSize = 8; // 8x8 bullet size
    final int scale = 3; // upscale by 3 to make it larger on screen

    public final int tileSize = originalTileSize * scale; // 48x48 tile size
    public final int bulletSize = originalBulletSize * scale; // 24x24 tile size
    public final int cols = 16;
    public final int rows = 12;
    public final int screenWidth = tileSize * cols; // 768 pixels
    public final int screenHeight = tileSize * rows; // 576 pixels

    private volatile boolean running = true; // Flag to control the game loop

    Projectile projectile;
    boolean bulletTimerCheck = false;
    int bulletTimer = 40;

    public ArrayList<Projectile> projectileList = new ArrayList<>();
    public ArrayList<Enemy> enemyList = new ArrayList<>();

    // FPS
    int fps = 60;

    int enemyMax = 2;

    int enemiesAtStart = 0;
    int waveNumber = 1;

    public int commandNum = 0;

    Game game;
    Thread gameThread;
    GameController gameController = new GameController(this);
    Player player = new Player(this, gameController);
    NormalEnemy normalEnemy = new NormalEnemy(this, player);
    TileFactory tileFactory = new TileFactory(this);
    GameState gameState;
    public CollisionDetection collisionDetection = new CollisionDetection(this);

    private Image background = Inf101Graphics.loadImageFromResources("title_background.png");
    private Image title = Inf101Graphics.loadImageFromResources("Resten_ringer.png");
    private Image beer = Inf101Graphics.loadImageFromResources("beer.png");
    private Image beer2 = Inf101Graphics.loadImageFromResources("random_beer_1.jpg");
    private Image beer3 = Inf101Graphics.loadImageFromResources("random_beer_2.jpg");
    private Image keepCalm = Inf101Graphics.loadImageFromResources("keep_calm.jpg");
    private Image map = Inf101Graphics.loadImageFromResources("mapreal.png");

    public Game() {
        this.game = this;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // improves rendering, doesn't display the render before the whole screen is
                                      // ready
        this.addKeyListener(gameController);
        this.setFocusable(true);
        this.requestFocusInWindow();
        gameState = GameState.TITLE_SCREEN;

        // Create 3 enemies at start (if enemyMax is 3)
        for (enemiesAtStart = 0; enemiesAtStart <= (enemyMax / 2); enemiesAtStart++) {
            if (enemiesAtStart < enemyMax) {
                NormalEnemy newNormalEnemy = new NormalEnemy(game, player);
                enemyList.add(newNormalEnemy);
            }
        }
        SlowEnemy newSlowEnemy = new SlowEnemy(game, player);
        enemyList.add(newSlowEnemy);
        FastEnemy newFastEnemy = new FastEnemy(game, player);
        enemyList.add(newFastEnemy);

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

        while (running == true) {

            // Check if the game should stop (e.g., player dies)
            if (gameState == GameState.GAME_OVER) {
                stopGame(); // Call method to stop the game
            }

            // 1 UPDATE: update information such as character positions
            update();

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

    public void update() {
        if (gameState == GameState.ACTIVE_GAME) {
            if (player.isAlive == true) {
                player.update();
            }

            // Check if arrow keys are pressed and create projectile accordingly
            if (gameController.shotKeyPressed) {
                // Check if the previous projectile is not alive before creating a new one
                if (bulletTimerCheck == false) {

                    Projectile newProjectile = new Projectile(this, player, gameController,
                            gameController.getShotDirection(),
                            normalEnemy);
                    projectileList.add(newProjectile);
                    gameController.shotKeyPressed = false;
                    bulletTimerCheck = true;
                } else {
                    gameController.shotKeyPressed = false;
                }
            }

            for (int i = enemyList.size() - 1; i >= 0; i--) {
                for (int j = projectileList.size() - 1; j >= 0; j--) {
                    Projectile projectile = projectileList.get(j); // Retrieve the projectile
                    projectile.update(); // Update the projectile

                    // Check conditions for removing the projectile
                    if (screenWidth < projectile.getProjectileX() || projectile.getProjectileX() < 0
                            || screenHeight < projectile.getProjectileY() || projectile.getProjectileY() < 0
                            || projectile.projectTileHitEnemy(i)) {
                        projectileList.remove(j); // Remove the projectile if necessary
                    }
                }
            }
            for (int i = enemyList.size() - 1; i >= 0; i--) {
                enemyList.get(i).update(); // Update the enemy
                if (enemyList.get(i).isAlive == false) {
                    enemyList.remove(i);
                }
            }
        }

        // Check if all enemies are defeated
        if (enemyList.isEmpty()) {
            // Set game state to WAVE_COMPLETE
            gameState = GameState.WAVE_COMPLETE;
            if (gameController.enterKeyPressed) {
                waveNumber++;
                gameState = GameState.ACTIVE_GAME;
                // Create 3 enemies at start (if enemyMax is 3)
                for (enemiesAtStart = 0; enemiesAtStart <= (enemyMax); enemiesAtStart++) {
                    if (enemiesAtStart < enemyMax) {
                        NormalEnemy newNormalEnemy = new NormalEnemy(game, player);
                        enemyList.add(newNormalEnemy);
                    }
                }
                SlowEnemy newSlowEnemy = new SlowEnemy(game, player);
                enemyList.add(newSlowEnemy);
                FastEnemy newFastEnemy = new FastEnemy(game, player);
                enemyList.add(newFastEnemy);
                player.x = getWidth() / 2;
                player.y = getHeight() / 2;
                enemyMax ++;
            }
        }

        bulletTimer--;
        if (bulletTimer <= 0) {
            bulletTimerCheck = false;
            bulletTimer = 40;
        }
    }

    // Method to stop the game
    private void stopGame() {
        running = false; // Set the flag to stop the game
        // Perform any cleanup or additional actions when stopping the game
    }

    // The paintComponent method is called by the Java Swing framework every time
    // either the window opens or resizes, or we call .repaint() on this object.
    // Note: NEVER call paintComponent directly yourself
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameState == GameState.TITLE_SCREEN) {
            drawTitleScreen(g2);
        }

        if (gameState == GameState.ACTIVE_GAME) {
            g2.drawImage(map, 0, 0, screenWidth, screenHeight, null);

            player.draw(g2);

            int enemiesLeft = game.enemyList.size();
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Symbol Bold", Font.BOLD, 20));
            g2.drawString("ENEMIES LEFT: " + enemiesLeft, 10, 70);

            for (int i = 0; i < enemyList.size(); i++) {
                if (enemyList.get(i) != null) {
                    enemyList.get(i).draw(enemyList.get(i), g2);
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    projectileList.get(i).draw(g2);
                }
            }
        }

        if (player.getGameState() == GameState.GAME_OVER) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Game Over", getHeight() / 2, getHeight() / 2 - 80);
        }
        if (gameState == GameState.VICTORY) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 26));
            g2.drawString("Victory!", getWidth() / 2, getHeight() / 2 - 80);
        }
        if (gameState == GameState.WAVE_COMPLETE) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 26));
            int x = xCenterText("Wave 1 completed", g2);
            g2.drawString("Wave " + waveNumber + "completed,", x, getHeight() / 2 - 40);
            String text = "Press enter to have another drink.";
            x = xCenterText(text, g2);
            g2.drawString(text, x, getHeight() / 2);

        }
    }

    private void drawTitleScreen(Graphics2D g2) {
        g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);
        g2.drawImage(title, 100, 100, 612, 148, null);
        g2.drawImage(beer2, 50, 350, tileSize * 3, tileSize * 3, null);
        g2.drawImage(beer3, 600, 300, tileSize * 3, tileSize * 3, null);
        g2.drawImage(keepCalm, 70, 30, tileSize * 2, tileSize * 2, null);

        g2.setFont(new Font("IMPACT", Font.BOLD, 50));
        g2.setColor(Color.BLACK);
        String text = "NEW GAME";
        int x = xCenterText(text, g2);
        int y = tileSize * 7;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawImage(beer, x - (tileSize + 15), y - tileSize, tileSize, tileSize, null);
        }

        text = "QUIT";
        x = xCenterText(text, g2);
        y += tileSize * 1.5;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawImage(beer, x - (tileSize + 15), y - tileSize, tileSize, tileSize, null);
        }

    }

    private int xCenterText(String text, Graphics2D g2) {
        int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = screenWidth / 2 - lenght / 2;
        return x;
    }

}
