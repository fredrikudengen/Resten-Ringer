package no.uib.inf101.sample;

import java.util.ArrayList;

import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.Projectile;
import no.uib.inf101.sample.entity.enemy.Enemy;
import no.uib.inf101.sample.entity.enemy.FastEnemy;
import no.uib.inf101.sample.entity.enemy.NormalEnemy;
import no.uib.inf101.sample.entity.enemy.SlowEnemy;
import no.uib.inf101.sample.sound.GameSong;
import no.uib.inf101.sample.sound.SoundPlayer;

public class UpdateGame {

    Game game;
    Player player;
    GameSong gameSong;
    GameController gameController;

    private boolean gameOverSoundPlay = false;
    private int enemyMax = 2;

    private int enemiesAtStart = 0;
    private boolean bulletTimerCheck = false;
    private boolean addEnemies = true; // Add enemies by default
    private int bulletTimer = 40;
    private int waveNumber = 1;

    public UpdateGame(Game game, Player player, GameController gameController) {
        this.game = game;
        this.player = player;
        this.gameController = gameController;
        this.gameSong = new GameSong();

    }

    public void resetAllValues() {
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();
        ArrayList<Enemy> enemyList = game.getEnemyList();

        player.setHealthpoints(7);
        player.setX(screenWidth / 2);
        player.setY(screenHeight / 2);
        if (addEnemies) {
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

    }

    public void updateGame() {
        GameState gameState = game.getGameState();
        ArrayList<Enemy> enemyList = game.getEnemyList();
        String modeSelect = game.getModeSelect();
        playMusic();

        if (player.getHealthpoints() <= 0) {
            game.setGameState(GameState.GAME_OVER);
        }
        if (gameState == GameState.TITLE_SCREEN) {
            addEnemies = true;
            enemyList.clear();
            resetAllValues();
        }
        if (gameState == GameState.GAME_OVER) {
            if (gameController.RKeyPressed) {
                game.setGameState(GameState.TITLE_SCREEN);
                addEnemies = true;
                resetAllValues();
            }
        }
        // Check if all enemies are defeated
        if (enemyList.isEmpty()) {
            // Set game state to WAVE_COMPLETE
            if (gameState == GameState.ACTIVE_GAME) {
                if (game.getWaveNum() == 2) {
                    game.setGameState(GameState.VICTORY);
                } else {
                    game.setGameState(GameState.WAVE_COMPLETE);
                    game.setWaveNum(game.getWaveNum() + 1);
                    enemyMax++;
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
                }
            }
        }
        if (gameState == GameState.VICTORY && gameController.RKeyPressed) {
            game.setGameState(GameState.TITLE_SCREEN);
        }
        if (gameState == GameState.WAVE_COMPLETE && gameController.enterKeyPressed) {
            waveComplete();
        }
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

        if (gameState == GameState.ACTIVE_GAME) {

            updateActiveGame();

            bulletTimer--;
            if (bulletTimer <= 0) {
                bulletTimerCheck = false;
                bulletTimer = 40;
            }
        }
    }

    private void playMusic() {
        GameState gameState = game.getGameState();
        String currentSong = gameSong.getSongName();
        if (gameState == GameState.TITLE_SCREEN && !currentSong.equals("megaman.mid")) {
            gameSong.setSongName("megaman.mid");
            gameSong.run();
        } else if (gameState == GameState.ACTIVE_GAME && !currentSong.equals("metroid-theme.mid")) {
            gameSong.setSongName("metroid-theme.mid");
            gameSong.run();
        } else if (gameState == GameState.GAME_OVER && (!gameOverSoundPlay)) {
            gameSong.doPauseMidiSounds();
            gameOverSoundPlay = true;
            SoundPlayer.gameOver();
        } else if (gameState == GameState.VICTORY && !currentSong.equals("Victory.mid")) {
            gameSong.setSongName("Victory.mid");
            gameSong.run();
        }
    }

    private void updateActiveGame() {
        if (player.getIsAlive() == true) {
            player.update();
        }

        updateProjectile();

        playDeathSound();
    }

    private void waveComplete() {
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();

        game.setGameState(GameState.ACTIVE_GAME);
        SoundPlayer.playBeerCanOpen();
        player.setX(screenWidth / 2);
        player.setY(screenHeight / 2);

    }

    private void playDeathSound() {
        ArrayList<Enemy> enemyList = game.getEnemyList();
        for (int i = enemyList.size() - 1; i >= 0; i--) {
            enemyList.get(i).update(); // Update the enemy
            if (enemyList.get(i).getIsAlive() == false) {
                if (enemyList.get(i) instanceof NormalEnemy) {
                    SoundPlayer.manDie1();
                } else if (enemyList.get(i) instanceof SlowEnemy) {
                    SoundPlayer.manDie3();
                } else {
                    SoundPlayer.mandDie2();
                }
                enemyList.remove(i);
            }
        }
    }

    private void updateProjectile() {
        ArrayList<Projectile> projectileList = game.getProjectileList();
        ArrayList<Enemy> enemyList = game.getEnemyList();
        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();

        // Check if arrow keys are pressed and create projectile accordingly
        if (gameController.shotKeyPressed) {
            // Check if the previous projectile is not alive before creating a new one
            if (bulletTimerCheck == false) {
                Projectile newProjectile = new Projectile(game, player, gameController,
                        gameController.getShotDirection());
                projectileList.add(newProjectile);
                SoundPlayer.gunShot();
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
                if (screenWidth < projectile.getX() || projectile.getX() < 0
                        || screenHeight < projectile.getY() || projectile.getY() < 0
                        || projectile.projectTileHitEnemy(i)) {
                    projectileList.remove(j); // Remove the projectile if necessary
                }
            }
        }
    }
}
