package no.uib.inf101.resten_ringer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import no.uib.inf101.resten_ringer.entity.Player;
import no.uib.inf101.resten_ringer.entity.Projectile;
import no.uib.inf101.resten_ringer.entity.enemy.Enemy;

public class DrawGame {

    private Game game;
    private Player player;

    private int yTitle = 100; 
    private int numTitle = 1; 
    private int counterTitle = 1;

    private BufferedImage background, title, beer, beer2, beer3, keepCalm, map, emptyBeer, gameOver, victoryScreen, waterCooler,
            bottleOfAlcohol, levelSelect;

    public DrawGame(Game game, Player player) {
        this.game = game;
        this.player = player;
        getImage();
    }
    
    /**
     * Encapsulates all draw methods used in other classes, along with different
     * game screens. Called upon in Game.
     * 
     * @param g2 Graphics2D object used to draw.
     */
    public void drawGame(Graphics2D g2) {
        GameState gameState = game.getGameState();
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();

        if (gameState == GameState.TITLE_SCREEN) {
            drawTitleScreen(g2);
        }
        if (gameState == GameState.LEVEL_SELECT) {
            drawLevelSelect(g2);
        }
        if (gameState == GameState.ACTIVE_GAME) {
            drawActiveGame(g2);
        }
        if (gameState == GameState.GAME_OVER) {
            g2.drawImage(gameOver, 0, 0, screenWidth, screenHeight, null);
        }
        if (gameState == GameState.VICTORY) {
            g2.drawImage(victoryScreen, 0, 0, screenWidth, screenHeight, null);
        }
        if (gameState == GameState.WAVE_COMPLETE) {
            drawWaveComplete(g2);
        }
    }

    private void drawWaveComplete(Graphics2D g2) {
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();
        int tileSize = game.getTileSize();
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 26));
        int x = xCenterText("Wave 1 completed", g2);
        g2.drawString("Wave " + game.getWaveNum() + " completed,", x, screenHeight / 2 - 40);
        String text = "press enter to have another drink.";
        x = xCenterText(text, g2);
        g2.drawString(text, x, screenHeight / 2);
        g2.drawImage(emptyBeer, screenWidth / 2 - (tileSize * 2 + 20), screenHeight / 2 + 20, tileSize * 4,
                tileSize * 4, null);
    }

    private void drawActiveGame(Graphics2D g2) {
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();
        ArrayList<Enemy> enemyList = game.getEnemyList();
        ArrayList<Projectile> projectileList = game.getProjectileList();

        g2.drawImage(map, 0, 0, screenWidth, screenHeight, null);

        player.draw(g2);

        int enemiesLeft = enemyList.size();
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

    private void drawTitleScreen(Graphics2D g2) {
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();
        int tileSize = game.getTileSize();
        int commandNum = game.getCommandNum();
        g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);

        // Moves title up and down
        if (yTitle <= 105 && 95 <= yTitle) {
            counterTitle++;
            if (counterTitle > 5) {
                yTitle += numTitle;
                counterTitle = 0;
                if (yTitle == 105 || yTitle == 95) {
                    numTitle = -numTitle;
                }
            }
        }
        g2.drawImage(title, 100, yTitle, 612, 148, null);
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
        text = "LEVELS";
        x = xCenterText(text, g2);
        y += tileSize * 1.5;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawImage(beer, x - (tileSize + 15), y - tileSize, tileSize, tileSize, null);
        }
        text = "QUIT";
        x = xCenterText(text, g2);
        y += tileSize * 1.5;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawImage(beer, x - (tileSize + 15), y - tileSize, tileSize, tileSize, null);
        }

    }

    private void drawLevelSelect(Graphics2D g2) {
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();
        int tileSize = game.getTileSize();
        int commandNum = game.getCommandNum();
        g2.drawImage(levelSelect, 0, 0, screenWidth, screenHeight, null);
        int x = 70;
        if (commandNum == 0) {
            g2.drawImage(waterCooler, 35, 170, tileSize * 6, tileSize * 6, null);
        }
        x = 90 + (tileSize * 4 + 20);
        if (commandNum == 1) {
            g2.drawImage(beer, x, 260, tileSize * 3 + 20, tileSize * 3 + 20, null);
        }
        x = 50 + ((tileSize * 4 + 20) * 2);
        if (commandNum == 2) {
            g2.drawImage(bottleOfAlcohol, x, 190, tileSize * 5 + 30, tileSize * 5 + 30, null);
        }

    }

    private int xCenterText(String text, Graphics2D g2) {
        int screenWidth = game.getScreenWidth();
        int lenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = screenWidth / 2 - lenght / 2;
        return x;
    }

    private void getImage() {
        background = Inf101Graphics.loadImageFromResources("title_background.png");
        title = Inf101Graphics.loadImageFromResources("Resten_ringer.png");
        beer = Inf101Graphics.loadImageFromResources("beer.png");
        beer2 = Inf101Graphics.loadImageFromResources("random_beer_1.jpg");
        beer3 = Inf101Graphics.loadImageFromResources("random_beer_2.jpg");
        keepCalm = Inf101Graphics.loadImageFromResources("keep_calm.jpg");
        map = Inf101Graphics.loadImageFromResources("mapreal.png");
        emptyBeer = Inf101Graphics.loadImageFromResources("beer_empty.png");
        gameOver = Inf101Graphics.loadImageFromResources("GAMEOVER.png");
        victoryScreen = Inf101Graphics.loadImageFromResources("victoryScreen.png");
        waterCooler = Inf101Graphics.loadImageFromResources("water_cooler.png");
        bottleOfAlcohol = Inf101Graphics.loadImageFromResources("bottle_of_alcohol.png");
        levelSelect = Inf101Graphics.loadImageFromResources("level_select.png");
    }
}
