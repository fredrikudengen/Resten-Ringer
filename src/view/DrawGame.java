package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import controller.Game;
import model.enemies.Enemy;
import model.Player;
import model.Projectile;

public class DrawGame {

    // -------------------------------------------------------------------------
    // Fonts
    // -------------------------------------------------------------------------
    private static final Font FONT_HUD        = new Font("Arial", Font.BOLD, 20);
    private static final Font FONT_MENU_TITLE = new Font("Arial", Font.BOLD, 36);
    private static final Font FONT_WAVE_TEXT  = new Font("Arial", Font.BOLD, 28);

    // -------------------------------------------------------------------------
    // Title animation constants
    // -------------------------------------------------------------------------
    private static final int TITLE_Y_MIN       = 95;
    private static final int TITLE_Y_MAX       = 105;
    private static final int TITLE_Y_START     = 100;
    private static final int TITLE_FRAME_DELAY = 5;

    // -------------------------------------------------------------------------
    // Title image geometry
    // -------------------------------------------------------------------------
    private static final int TITLE_IMG_X      = 100;
    private static final int TITLE_IMG_W      = 612;
    private static final int TITLE_IMG_H      = 148;

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------
    private final Game   game;
    private final Player player;

    // Title screen animation state
    private int yTitle       = TITLE_Y_START;
    private int titleDir     = 1;
    private int titleCounter = 1;

    // -------------------------------------------------------------------------
    // Images
    // -------------------------------------------------------------------------
    private BufferedImage background, title, beer, beer2, beer3, keepCalm, map, emptyBeer,
            gameOver, victoryScreen, waterCooler, bottleOfAlcohol, levelSelect;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    public DrawGame(Game game, Player player) throws IOException {
        this.game   = game;
        this.player = player;
        loadImages();
    }

    // -------------------------------------------------------------------------
    // Main draw dispatcher
    // -------------------------------------------------------------------------

    /**
     * Routes to the correct screen based on the current game state.
     *
     * @param g2 Graphics2D context
     */
    public void drawGame(Graphics2D g2) {
        switch (game.getGameState()) {
            case TITLE_SCREEN  -> drawTitleScreen(g2);
            case LEVEL_SELECT  -> drawLevelSelect(g2);
            case ACTIVE_GAME   -> drawActiveGame(g2);
            case WAVE_COMPLETE -> drawWaveComplete(g2);
            case GAME_OVER     -> g2.drawImage(gameOver,      0, 0, game.getScreenWidth(), game.getScreenHeight(), null);
            case VICTORY       -> g2.drawImage(victoryScreen, 0, 0, game.getScreenWidth(), game.getScreenHeight(), null);
        }
    }

    // -------------------------------------------------------------------------
    // Screen drawers
    // -------------------------------------------------------------------------

    private void drawTitleScreen(Graphics2D g2) {
        int sw  = game.getScreenWidth();
        int sh  = game.getScreenHeight();
        int ts  = game.getTileSize();
        int cmd = game.getCommandNum();

        g2.drawImage(background, 0, 0, sw, sh, null);

        updateTitleAnimation();
        g2.drawImage(title,    TITLE_IMG_X, yTitle, TITLE_IMG_W, TITLE_IMG_H, null);
        g2.drawImage(beer2,     50, 350, ts * 3, ts * 3, null);
        g2.drawImage(beer3,    600, 300, ts * 3, ts * 3, null);
        g2.drawImage(keepCalm,  70,  30, ts * 2, ts * 2, null);

        g2.setFont(FONT_MENU_TITLE);
        g2.setColor(Color.BLACK);

        String[] labels = { "NEW GAME", "LEVELS", "QUIT" };
        int y = ts * 7;
        for (int i = 0; i < labels.length; i++) {
            int x = xCenterText(labels[i], g2);
            g2.drawString(labels[i], x, y);
            if (cmd == i) g2.drawImage(beer, x - (ts + 15), y - ts, ts, ts, null);
            y += (int) (ts * 1.5);
        }
    }

    private void drawLevelSelect(Graphics2D g2) {
        int sw  = game.getScreenWidth();
        int sh  = game.getScreenHeight();
        int ts  = game.getTileSize();
        int cmd = game.getCommandNum();

        g2.drawImage(levelSelect, 0, 0, sw, sh, null);

        // Each column is offset by (ts * 4 + 20) from the previous one
        int colSpacing = ts * 4 + 20;

        if (cmd == 0) drawLevelIcon(g2, waterCooler,     35,               170, ts * 6,      ts * 6);
        if (cmd == 1) drawLevelIcon(g2, beer,             90 + colSpacing,  260, ts * 3 + 20, ts * 3 + 20);
        if (cmd == 2) drawLevelIcon(g2, bottleOfAlcohol,  50 + colSpacing * 2, 190, ts * 5 + 30, ts * 5 + 30);
    }

    private void drawActiveGame(Graphics2D g2) {
        int sw = game.getScreenWidth();
        int sh = game.getScreenHeight();
        ArrayList<Enemy>      enemies     = game.getEnemyList();
        ArrayList<Projectile> projectiles = game.getProjectileList();

        g2.drawImage(map, 0, 0, sw, sh, null);
        player.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(FONT_HUD);
        g2.drawString("ENEMIES LEFT: " + enemies.size(), 10, 70);

        for (Enemy enemy : enemies) {
            if (enemy != null) enemy.draw(g2);
        }
        for (Projectile p : projectiles) {
            if (p != null) p.draw(g2);
        }
    }

    private void drawWaveComplete(Graphics2D g2) {
        int sw = game.getScreenWidth();
        int sh = game.getScreenHeight();
        int ts = game.getTileSize();

        g2.setColor(Color.RED);
        g2.setFont(FONT_WAVE_TEXT);

        String header = "Wave " + game.getWaveNum() + " completed,";
        g2.drawString(header, xCenterText(header, g2), sh / 2 - 40);

        String prompt = "press enter to have another drink.";
        g2.drawString(prompt, xCenterText(prompt, g2), sh / 2);

        g2.drawImage(emptyBeer, sw / 2 - (ts * 2 + 20), sh / 2 + 20, ts * 4, ts * 4, null);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** Advances the floating title animation by one frame. */
    private void updateTitleAnimation() {
        if (yTitle >= TITLE_Y_MIN && yTitle <= TITLE_Y_MAX) {
            if (++titleCounter > TITLE_FRAME_DELAY) {
                yTitle += titleDir;
                titleCounter = 0;
                if (yTitle == TITLE_Y_MAX || yTitle == TITLE_Y_MIN) {
                    titleDir = -titleDir;
                }
            }
        }
    }

    /** Draws a level-select icon image at the given position and size. */
    private void drawLevelIcon(Graphics2D g2, BufferedImage img, int x, int y, int w, int h) {
        g2.drawImage(img, x, y, w, h, null);
    }

    /** Returns the x-coordinate that horizontally centres {@code text} on screen. */
    private int xCenterText(String text, Graphics2D g2) {
        int width = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return game.getScreenWidth() / 2 - width / 2;
    }

    /** Loads a {@link BufferedImage} from the classpath. */
    private BufferedImage loadImage(String path) throws IOException {
        URL url = getClass().getResource(path);
        if (url == null) throw new IOException("Resource not found on classpath: " + path);
        return ImageIO.read(url);
    }

    private void loadImages() throws IOException {
        background      = loadImage("/resources/title_background.png");
        title           = loadImage("/resources/Resten_ringer.png");
        beer            = loadImage("/resources/beer.png");
        beer2           = loadImage("/resources/random_beer_1.jpg");
        beer3           = loadImage("/resources/random_beer_2.jpg");
        keepCalm        = loadImage("/resources/keep_calm.jpg");
        map             = loadImage("/resources/mapreal.png");
        emptyBeer       = loadImage("/resources/beer_empty.png");
        gameOver        = loadImage("/resources/GAMEOVER.png");
        victoryScreen   = loadImage("/resources/victoryScreen.png");
        waterCooler     = loadImage("/resources/water_cooler.png");
        bottleOfAlcohol = loadImage("/resources/bottle_of_alcohol.png");
        levelSelect     = loadImage("/resources/Level_select.png");
    }
}