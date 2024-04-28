package no.uib.inf101.resten_ringer.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.resten_ringer.Game;
import no.uib.inf101.resten_ringer.GameController;
import no.uib.inf101.resten_ringer.GameState;
import no.uib.inf101.resten_ringer.entity.Player;

public class NormalEnemyTest {

    @Test
    void testEnemyCollidesWithPlayer() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        NormalEnemy enemy = new NormalEnemy(game, player);

        // Set player position to be close to enemy
        player.setX(100);
        player.setY(100);

        // Set enemy position to be close to player
        enemy.setX(100);
        enemy.setY(100);

        boolean result = enemy.getEnemyCollidesWithPlayer();

        assertTrue(result, "Enemy should collide with player");
    }

    @Test
    void testUpdate() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        NormalEnemy enemy = new NormalEnemy(game, player);
        game.setGameState(GameState.ACTIVE_GAME);

        // Set player position to be close to enemy
        player.setX(100);
        player.setY(100);
        // Set enemy position to be close to player
        enemy.setX(100);
        enemy.setY(100);

        // Set initial health points
        player.setHealthpoints(3);

        // Call update method to simulate enemy movement and collision
        enemy.update();

        // Check if health points are reduced after collision
        assertEquals(2, player.getHealthpoints(), "Player's health points should be reduced after collision");

        // Check if enemy is alive
        assertTrue(enemy.getIsAlive(), "Enemy should be alive after update");
    }

}
