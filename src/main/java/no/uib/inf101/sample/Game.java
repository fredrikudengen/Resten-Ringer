package no.uib.inf101.sample;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import no.uib.inf101.sample.entity.NormalEnemy;
import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.Projectile;

public class Game extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile size
    final int originalBulletSize = 8; // 8x8 bullet size
    final int scale = 3; // upscale by 3 to make it larger on screen

    public final int tileSize = originalTileSize * scale; // 48x48 tile size
    public final int bulletSize = originalBulletSize * scale; // 24x24 tile size
    final int rows = 16;
    final int cols = 12;
    public final int screenWidth = tileSize * rows; // 768 pixels
    public final int screenHeight = tileSize * cols; // 576 pixels

    Game game;
    GameController gameController = new GameController(this);
    Thread gameThread;

    Projectile projectile;
    boolean bulletTimerCheck = false;
    int bulletTimer = 40;

    public ArrayList<Projectile> projectileList = new ArrayList<>();

    // FPS
    int fps = 60;

    Player player = new Player(this, gameController);
    NormalEnemy normalEnemy = new NormalEnemy(this, player);

    public Game() {
        this.game = this;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // improves rendering, doesn't display the render before the whole screen is
                                      // ready
        this.addKeyListener(gameController);
        this.setFocusable(true);
        this.requestFocusInWindow();
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

        player.update();

        normalEnemy.update();

        // Check if arrow keys are pressed and create projectile accordingly
        if (gameController.shotKeyPressed)

        {
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

        for (int i = projectileList.size() - 1; i >= 0; i--) {

            projectileList.get(i).update();

            Iterator<Projectile> projectileIterator = projectileList.iterator();
            while (projectileIterator.hasNext()) {
                Projectile projectile = projectileIterator.next();
                projectile.update();

                if (screenWidth < projectile.getProjectileX() || projectile.getProjectileX() < 0
                        || screenHeight < projectile.getProjectileY() || projectile.getProjectileY() < 0) {
                    projectileList.get(i).isAlive = false;
                    projectileIterator.remove(); // Safely remove the projectile using iterator
                } else if (projectileList.get(i).projectTileHitEnemy() == true) {
                    projectileList.get(i).isAlive = false;
                    projectileIterator.remove(); // Safely remove the projectile using iterator
                }
            }
        }

        bulletTimer--;
        if (bulletTimer <= 0) {
            bulletTimerCheck = false;
            bulletTimer = 40;
        }
    }

    // The paintComponent method is called by the Java Swing framework every time
    // either the window opens or resizes, or we call .repaint() on this object.
    // Note: NEVER call paintComponent directly yourself
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);

        normalEnemy.draw(g2);

        for (int i = 0; i < projectileList.size(); i++) {
            if (projectileList.get(i) != null) {
                projectileList.get(i).draw(g2);
            }
        }
    }
}
