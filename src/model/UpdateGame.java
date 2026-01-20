package model;

import java.io.IOException;
import java.util.ArrayList;

import controller.GameController;
import sound.GameSong;
import entity.Player;
import entity.Projectile;
import entity.enemy.Enemy;
import entity.enemy.FastEnemy;
import entity.enemy.NormalEnemy;
import entity.enemy.SlowEnemy;

public class UpdateGame {

    private Game game;
    private Player player;
    private GameSong gameSong;
    private GameController gameController;

    private boolean bulletTimerCheck = false;
    private boolean addEnemies = true; // Add enemies by default
    private boolean gameOverSoundPlay = false;

    private int enemyMax = 2;
    private int enemiesAtStart = 0;
    private int bulletTimer = 40;

    public UpdateGame(Game game, Player player, GameController gameController) {
        this.game = game;
        this.player = player;
        this.gameController = gameController;
        this.gameSong = new GameSong();
    }

    /**
     * Encapsulates all draw methods used in other classes, along with different
     * game screens. Called upon in Game.
          * @throws IOException 
          */
         public void updateGame() throws IOException {
        GameState gameState = game.getGameState();
        ArrayList<Enemy> enemyList = game.getEnemyList();
        playMusic();

        if (player.getHealthpoints() <= 0) {
            game.setGameState(GameState.GAME_OVER);
        }
        if (gameState == GameState.TITLE_SCREEN) {
            addEnemies = true;
            enemyList.clear();
            resetAllValues();
        }
        // Makes sure enemy positions get initialized only when new game is pressed
        if (gameState == GameState.TITLE_SCREEN && game.getCommandNum() == 0 && gameController.enterKeyPressed) {
            enemyList.clear();
            resetAllValues();
        }
        if (gameState == GameState.WAVE_COMPLETE && gameController.enterKeyPressed) {
            game.setGameState(GameState.ACTIVE_GAME);
        }
        if (gameState == GameState.VICTORY && gameController.rKeyPressed) {
            game.setGameState(GameState.TITLE_SCREEN);
        }
        if (gameState == GameState.GAME_OVER && gameController.rKeyPressed) {
            game.setGameState(GameState.TITLE_SCREEN);
            resetAllValues();
        }
        if (gameState == GameState.WAVE_COMPLETE && gameController.enterKeyPressed) {
            waveComplete();
        }
        if (gameState == GameState.ACTIVE_GAME) {
            updateActiveGame();
        }
        enemiesDefeated();
        checkModeSelect();
    }

    private void resetAllValues() throws IOException {
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();
        ArrayList<Enemy> enemyList = game.getEnemyList();

        player.setHealthpoints(7);
        player.setX(screenWidth / 2);
        player.setY(screenHeight / 2);
        game.setWaveNum(0);
        enemyMax = 2;
        
        // Creates new enemies for next wave/game over/victory
        if (addEnemies) {
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
    }

    private void checkModeSelect() throws IOException {
        ArrayList<Enemy> enemyList = game.getEnemyList();
        String modeSelect = game.getModeSelect();

        if (modeSelect.equals("sober mode")) {
            game.setModeSelect("");
            player.setHealthpoints(10);

            // removes FastEnemy (last enemy added)
            enemyList.remove(enemyList.size() - 1);
            game.setGameState(GameState.ACTIVE_GAME);

        } else if (modeSelect.equals("tipsy mode")) {
            game.setModeSelect("");
            SlowEnemy newSlowEnemy = new SlowEnemy(game, player);
            enemyList.add(newSlowEnemy);
            game.setGameState(GameState.ACTIVE_GAME);

        } else if (modeSelect.equals("drunk mode")) {
            game.setModeSelect("");
            player.setHealthpoints(4);
            enemyMax += 1;
            SlowEnemy newSlowEnemy = new SlowEnemy(game, player);
            enemyList.add(newSlowEnemy);
            FastEnemy newFastEnemy = new FastEnemy(game, player);
            enemyList.add(newFastEnemy);
            game.setGameState(GameState.ACTIVE_GAME);
        }
    }

    private void enemiesDefeated() throws IOException {
        GameState gameState = game.getGameState();
        ArrayList<Enemy> enemyList = game.getEnemyList();

        // Check if all enemies are defeated
        if (enemyList.isEmpty()) {
            if (gameState == GameState.ACTIVE_GAME) {
                if (game.getWaveNum() == 2) {
                    game.setGameState(GameState.VICTORY);
                } else {
                    game.setGameState(GameState.WAVE_COMPLETE);
                    game.setWaveNum(game.getWaveNum() + 1);
                    enemyMax++;
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
                }
            }
        }
    }

    private void playMusic() {
        GameState gameState = game.getGameState();
        String currentSong = gameSong.getSongName();
    
        if (gameState == GameState.TITLE_SCREEN && !"megaman.mid".equals(currentSong)) {
            gameSong.setSongName("megaman.mid");
            gameSong.run();
        } else if (gameState == GameState.ACTIVE_GAME && !"metroid-theme.mid".equals(currentSong)) {
            gameSong.setSongName("metroid-theme.mid");
            gameSong.run();
        } else if (gameState == GameState.GAME_OVER && (!gameOverSoundPlay)) {
            gameSong.doPauseMidiSounds();
            gameOverSoundPlay = true;
        } else if (gameState == GameState.VICTORY && !"Victory.mid".equals(currentSong)) {
            gameSong.setSongName("Victory.mid");
            gameSong.run();
        }

    }

    private void updateActiveGame() throws IOException {
        if (player.getIsAlive() == true) {
            player.update();
        }
        updateProjectile();
        playDeathSound();

        bulletTimer--;
        if (bulletTimer <= 0) {
            bulletTimerCheck = false;
            bulletTimer = 40;
        }
    }

    private void waveComplete() {
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();

        game.setGameState(GameState.ACTIVE_GAME);
        player.setX(screenWidth / 2);
        player.setY(screenHeight / 2);

    }

    private void playDeathSound() {
        ArrayList<Enemy> enemyList = game.getEnemyList();
        for (int i = enemyList.size() - 1; i >= 0; i--) {
            enemyList.get(i).update(); // Update the enemy
            if (enemyList.get(i).getIsAlive() == false) {
                enemyList.remove(i);
            }
        }
    }

    private void updateProjectile() throws IOException {
        ArrayList<Projectile> projectileList = game.getProjectileList();
        ArrayList<Enemy> enemyList = game.getEnemyList();
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();

        if (gameController.shotKeyPressed) {
            // Check if the previous projectile is not alive before creating a new one
            if (bulletTimerCheck == false) {
                Projectile newProjectile = new Projectile(game, player, gameController,
                        gameController.getShotDirection());
                projectileList.add(newProjectile);
                bulletTimerCheck = true;
            } else {
                gameController.shotKeyPressed = false;
            }
        }
        for (int i = enemyList.size() - 1; i >= 0; i--) {
            for (int j = projectileList.size() - 1; j >= 0; j--) {
                Projectile projectile = projectileList.get(j);
                projectile.update();

                if (screenWidth < projectile.getX() || projectile.getX() < 0
                        || screenHeight < projectile.getY() || projectile.getY() < 0
                        || projectile.projectileCollidesWithEnemy(i)) {
                    projectileList.remove(j);
                }
            }
        }
    }
}