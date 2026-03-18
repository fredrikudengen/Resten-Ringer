package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Player;
import model.Projectile;
import model.enemies.Enemy;
import model.enemies.FastEnemy;
import model.enemies.NormalEnemy;
import model.enemies.SlowEnemy;

public class UpdateGame {

    private static final int BULLET_TIMER_MAX  = 40;
    private static final int TOTAL_WAVES       = 2;

    private final Game           game;
    private final Player         player;
    private final GameController gameController;

    private boolean bulletTimerCheck = false;
    private boolean gameOverSoundPlayed = false;
    private boolean hasReset = false;

    private int enemyMax    = 2;
    private int bulletTimer = BULLET_TIMER_MAX;

    public UpdateGame(Game game, Player player, GameController gameController) {
        this.game           = game;
        this.player         = player;
        this.gameController = gameController;
    }

    /**
     * Main update loop — advances game logic each tick.
     */
    public void updateGame() throws IOException {
        GameState gameState = game.getGameState();

        if (player.getHealthpoints() <= 0 && gameState == GameState.ACTIVE_GAME) {
            game.setGameState(GameState.GAME_OVER);
            return;
        }

        switch (gameState) {
            case TITLE_SCREEN -> {
                if (!hasReset) {
                    game.getEnemyList().clear();
                    resetAllValues();
                    hasReset = true;
                }
            }
            case WAVE_COMPLETE -> {
                if (gameController.isEnterPressed()) {
                    waveComplete();
                }
            }
            case VICTORY -> {
                if (gameController.isRKeyPressed()) {
                    game.setGameState(GameState.TITLE_SCREEN);
                    hasReset = false;
                }
            }
            case GAME_OVER -> {
                if (gameController.isRKeyPressed()) {
                    game.setGameState(GameState.TITLE_SCREEN);
                    gameOverSoundPlayed = false;
                    hasReset = false;
                }
            }
            case ACTIVE_GAME -> updateActiveGame();
        }

        enemiesDefeated();
        checkModeSelect();
    }

    // -------------------------------------------------------------------------
    // Game state handlers
    // -------------------------------------------------------------------------

    private void resetAllValues() throws IOException {
        player.setHealthpoints(7);
        player.setX(game.getScreenWidth()  / 2);
        player.setY(game.getScreenHeight() / 2);
        game.setWaveNum(0);
        enemyMax = 2;
        spawnEnemies(enemyMax);
    }

    private void waveComplete() {
        game.setGameState(GameState.ACTIVE_GAME);
        player.setX(game.getScreenWidth()  / 2);
        player.setY(game.getScreenHeight() / 2);
    }

    private void checkModeSelect() throws IOException {
        Game.Mode modeSelect = game.getModeSelect();
        if (modeSelect == null) return;

        ArrayList<Enemy> enemyList = game.getEnemyList();
        game.setModeSelect(null);

        switch (modeSelect) {
            case SOBER -> {
                player.setHealthpoints(10);
                // Remove FastEnemy (last enemy added by spawnEnemies)
                if (!enemyList.isEmpty()) enemyList.remove(enemyList.size() - 1);
            }
            case TIPSY -> enemyList.add(new SlowEnemy(game, player));
            case DRUNK -> {
                player.setHealthpoints(4);
                enemyMax++;
                enemyList.add(new SlowEnemy(game, player));
                enemyList.add(new FastEnemy(game, player));
            }
        }
        game.setGameState(GameState.ACTIVE_GAME);
    }

    private void enemiesDefeated() throws IOException {
        if (!game.getEnemyList().isEmpty()) return;
        if (game.getGameState() != GameState.ACTIVE_GAME) return;

        if (game.getWaveNum() >= TOTAL_WAVES) {
            game.setGameState(GameState.VICTORY);
        } else {
            game.setWaveNum(game.getWaveNum() + 1);
            enemyMax++;
            spawnEnemies(enemyMax);
            game.setGameState(GameState.WAVE_COMPLETE);
        }
    }

    // -------------------------------------------------------------------------
    // Active game updates
    // -------------------------------------------------------------------------

    private void updateActiveGame() throws IOException {
        if (player.isAlive()) {
            player.update();
        }
        updateProjectiles();
        updateEnemies();

        bulletTimer--;
        if (bulletTimer <= 0) {
            bulletTimerCheck = false;
            bulletTimer = BULLET_TIMER_MAX;
        }
    }

    private void updateEnemies() {
        ArrayList<Enemy> enemyList = game.getEnemyList();
        for (int i = enemyList.size() - 1; i >= 0; i--) {
            Enemy enemy = enemyList.get(i);
            enemy.update();
            if (!enemy.isAlive()) {
                enemyList.remove(i);
            }
        }
    }

    private void updateProjectiles() throws IOException {
        ArrayList<Projectile> projectileList = game.getProjectileList();
        ArrayList<Enemy>      enemyList      = game.getEnemyList();
        int screenWidth  = game.getScreenWidth();
        int screenHeight = game.getScreenHeight();

        if (gameController.isShotKeyPressed() && !bulletTimerCheck) {
            projectileList.add(new Projectile(game, player, gameController,
                    gameController.getShotDirection()));
            bulletTimerCheck = true;
        }

        for (int j = projectileList.size() - 1; j >= 0; j--) {
            Projectile projectile = projectileList.get(j);
            projectile.update();

            boolean outOfBounds = projectile.getX() > screenWidth  || projectile.getX() < 0
                    || projectile.getY() > screenHeight || projectile.getY() < 0;

            boolean hitEnemy = false;
            for (int i = enemyList.size() - 1; i >= 0; i--) {
                if (projectile.projectileCollidesWithEnemy(enemyList.get(i))) {
                    hitEnemy = true;
                    enemyList.get(i).setHealthpoints(enemyList.get(i).getHealthpoints() - 1);
                    break;
                }
            }

            if (outOfBounds || hitEnemy) {
                projectileList.remove(j);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Spawns a wave of enemies: (max/2) NormalEnemies + one SlowEnemy + one FastEnemy.
     */
    private void spawnEnemies(int max) throws IOException {
        ArrayList<Enemy> enemyList = game.getEnemyList();
        for (int i = 0; i < max / 2; i++) {
            enemyList.add(new NormalEnemy(game, player));
        }
        enemyList.add(new SlowEnemy(game, player));
        enemyList.add(new FastEnemy(game, player));
    }
}