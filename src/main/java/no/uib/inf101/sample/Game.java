package no.uib.inf101.sample;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import no.uib.inf101.sample.entity.NormalEnemy;
import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.Projectile;
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
    public ArrayList<NormalEnemy> enemyList = new ArrayList<>();

    // FPS
    int fps = 60;

    int enemyMax = 3;

    int enemiesAtStart = 0;

    Game game;
    Thread gameThread;
    GameController gameController = new GameController();
    Player player = new Player(this, gameController);
    NormalEnemy normalEnemy = new NormalEnemy(this, player);
    TileFactory tileFactory = new TileFactory(this);
    GameState gameState;

    public Game() {
        this.game = this;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // improves rendering, doesn't display the render before the whole screen is
                                            // ready
        this.addKeyListener(gameController);
        this.setFocusable(true);
        this.requestFocusInWindow();
        gameState = GameState.ACTIVE_GAME;

        // Create 3 enemies at start (if enemyMax is 3)
        for (enemiesAtStart = 0; enemiesAtStart <= enemyMax; enemiesAtStart++) {
            if (enemiesAtStart < enemyMax) {
                NormalEnemy newNormalEnemy = new NormalEnemy(game, player);
                enemyList.add(newNormalEnemy);
            }
        }
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
        int enemyCounter = enemyList.size();

        if (enemyList.isEmpty()) {
            gameState = GameState.VICTORY;
            // for (int i = projectileList.size() - 1; i >= 0; i--) {
            //     projectileList.get(i).update();
            // }
            // enemyMax++;
            // while (enemyCounter <= enemyMax) {
            //     NormalEnemy newNormalEnemy = new NormalEnemy(game, player);
            //     enemyList.add(newNormalEnemy);
            //     enemyCounter++;
            // }

        }

        for (int i = enemyList.size() - 1; i >= 0; i--) {
            enemyList.get(i).update(); // Update the enemy
            if (enemyList.get(i).getNormalEnemyIsALive() == false) {
                enemyList.remove(i);
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

       if (gameState == GameState.ACTIVE_GAME) {
        tileFactory.draw(g2);

        player.draw(g2);

        for (int i = 0; i < enemyList.size(); i++) {
            if (enemyList.get(i) != null) {
                enemyList.get(i).draw(g2);
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
            g2.drawString("Game Over", screenHeight / 2, screenWidth / 2 - 80);
        }
        if (player.getGameState() == GameState.VICTORY) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Victory!", screenHeight / 2, screenWidth / 2 - 80);
        }
    }
}
