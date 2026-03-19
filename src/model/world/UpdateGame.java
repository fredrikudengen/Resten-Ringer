package model.world;

import java.util.List;

import controller.GameController;
import controller.GameState;
import model.entities.Player;
import model.entities.Projectile;
import model.entities.enemies.Enemy;
import model.entities.enemies.FastEnemy;
import model.entities.enemies.NormalEnemy;
import model.entities.enemies.SlowEnemy;

public class UpdateGame {

    private static final int BULLET_TIMER_MAX = 40;
    private static final int TOTAL_WAVES      = 2;

    private final Game           game;
    private final Player         player;
    private final GameController gameController;

    private boolean bulletTimerCheck = false;
    private boolean hasReset         = false;

    private int enemyMax    = 2;
    private int bulletTimer = BULLET_TIMER_MAX;

    public UpdateGame(Game game, Player player, GameController gameController) {
        this.game           = game;
        this.player         = player;
        this.gameController = gameController;
    }

    // -------------------------------------------------------------------------
    // Main loop
    // -------------------------------------------------------------------------

    /**
     * Advances game logic by one tick.
     * No longer throws IOException: enemy and projectile constructors were
     * converted to static-initializer sprite loading in a previous pass.
     */
    public void updateGame() {
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
                if (gameController.isEnterPressed()) waveComplete();
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
                    hasReset = false;
                }
            }
            case ACTIVE_GAME -> updateActiveGame();
        }

        enemiesDefeated();
        checkModeSelect();
    }

    // -------------------------------------------------------------------------
    // Game-state handlers
    // -------------------------------------------------------------------------

    /**
     * Resets all mutable game state back to the start-of-game values.
     *
     * Previously set HP to 7 directly, while Player.setDefaultValues() sets it
     * to 6 — the two were permanently out of sync. Delegating to setDefaultValues()
     * means Player owns its own initialisation; individual difficulty modes then
     * override HP explicitly as before via setHealthpoints().
     */
    private void resetAllValues() {
        player.setDefaultValues();
        game.setWaveNum(0);
        enemyMax = 2;
        spawnEnemies(enemyMax);
    }

    private void waveComplete() {
        game.setGameState(GameState.ACTIVE_GAME);
        player.setX(game.getScreenWidth()  / 2);
        player.setY(game.getScreenHeight() / 2);
    }

    private void checkModeSelect() {
        Game.Mode modeSelect = game.getModeSelect();
        if (modeSelect == null) return;

        List<Enemy> enemyList = game.getEnemyList();
        game.setModeSelect(null);

        // Lock ordering: always acquire enemyList before projectileList (see updateProjectiles).
        synchronized (enemyList) {
            switch (modeSelect) {
                case SOBER -> {
                    player.setHealthpoints(10);
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
        }
        game.setGameState(GameState.ACTIVE_GAME);
    }

    private void enemiesDefeated() {
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
    // Active-game update
    // -------------------------------------------------------------------------

    private void updateActiveGame() {
        if (player.isAlive()) player.update();
        updateProjectiles();
        updateEnemies();

        if (--bulletTimer <= 0) {
            bulletTimerCheck = false;
            bulletTimer      = BULLET_TIMER_MAX;
        }
    }

    /**
     * Fires, advances, and culls projectiles; resolves projectile-enemy hits.
     *
     * <p>Lock ordering fix: previously this method held the projectileList lock
     * while acquiring enemyList (inner), while DrawGame.drawActiveGame() acquired
     * enemyList then projectileList — a classic deadlock pair between the game-loop
     * thread and the Swing EDT. Consistent rule: always acquire enemyList first,
     * then projectileList. All synchronized blocks in this class and DrawGame
     * follow this order.
     */
    private void updateProjectiles() {
        List<Projectile> projectileList = game.getProjectileList();
        List<Enemy>      enemyList      = game.getEnemyList();

        if (gameController.isShotKeyPressed() && !bulletTimerCheck) {
            synchronized (projectileList) {
                projectileList.add(new Projectile(game, player, gameController.getShotDirection()));
            }
            bulletTimerCheck = true;
        }

        // Acquire enemyList first to match the ordering in DrawGame.
        synchronized (enemyList) {
            synchronized (projectileList) {
                for (int j = projectileList.size() - 1; j >= 0; j--) {
                    Projectile p = projectileList.get(j);
                    p.update();

                    if (p.isOutOfBounds()) {
                        projectileList.remove(j);
                        continue;
                    }

                    for (int i = enemyList.size() - 1; i >= 0; i--) {
                        Enemy enemy = enemyList.get(i);
                        if (p.collidesWithEnemy(enemy)) {
                            enemy.setHealthpoints(enemy.getHealthpoints() - 1);
                            projectileList.remove(j);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Ticks each enemy and removes any that died this frame.
     * Enemy.update() sets isAlive = false when healthPoints reaches zero.
     */
    private void updateEnemies() {
        List<Enemy> enemyList = game.getEnemyList();
        synchronized (enemyList) {
            for (int i = enemyList.size() - 1; i >= 0; i--) {
                Enemy enemy = enemyList.get(i);
                enemy.update();
                if (!enemy.isAlive()) enemyList.remove(i);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Populates the enemy list for a new wave: (max/2) NormalEnemies + one
     * SlowEnemy + one FastEnemy.
     */
    private void spawnEnemies(int max) {
        List<Enemy> enemyList = game.getEnemyList();
        synchronized (enemyList) {
            for (int i = 0; i < max / 2; i++) enemyList.add(new NormalEnemy(game, player));
            enemyList.add(new SlowEnemy(game, player));
            enemyList.add(new FastEnemy(game, player));
        }
    }
}