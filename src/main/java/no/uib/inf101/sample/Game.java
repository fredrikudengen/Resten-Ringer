package no.uib.inf101.sample;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import no.uib.inf101.sample.entity.Player;

public class Game extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile size
    final int scale = 3; // upscale by 3 to make it larger on screen

    final int tileSize = originalTileSize * scale; // 48x48 tile size
    final int rows = 16;
    final int cols = 12;
    final int screenWidth = tileSize * rows; // 768 pixels
    final int screenHeight = tileSize * cols; // 576 pixels

    // Set player's default position
    public int playerX = 100;
    public int playerY = 100;
    public int playerSpeed = 3; // moves 3 pixels each time

    GameController gameController;
    Thread gameThread;
    Player player;

    // FPS
    int fps = 60;

    public Game() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // improves rendering, doesn't display the render before the whole screen is
                                      // ready (chatGPT 06.04.24)
        gameController = new GameController(this);
        this.addKeyListener(gameController);
        this.setFocusable(true);
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

            long currentTime = System.nanoTime(); // Stores a time in nanoseconds from when it was instantiated in a
                                                  // long type (int but with larger numbers) (chatGPT 07.04.24)

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

        // Change so player position is updated in Player and then call this function

        if (gameController.upPressed == true) {
            playerY -= playerSpeed;
        }
        if (gameController.downPressed == true) {
            playerY += playerSpeed;
        }
        if (gameController.leftPressed == true) {
            playerX -= playerSpeed;
        }
        if (gameController.rightPressed == true) {
            playerX += playerSpeed;
        }

        // PROJECTILES
        // Create a variable projectileCount and then a while(i < projectileCount) to
        // make sure that every projectile on the map is updated
    }

    // The paintComponent method is called by the Java Swing framework every time
    // either the window opens or resizes, or we call .repaint() on this object.
    // Note: NEVER call paintComponent directly yourself
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
    }

}
