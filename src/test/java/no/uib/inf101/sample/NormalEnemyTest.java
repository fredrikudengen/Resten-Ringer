package no.uib.inf101.sample;

import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.enemy.NormalEnemy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NormalEnemyTest {

    @Test
    void testEnemyCollidesWithPlayer() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        NormalEnemy enemy = new NormalEnemy(game, player);

        // Set player position to be close to enemy
        player.x = 100;
        player.y = 100;

        // Set enemy position to be close to player
        enemy.x = 110;
        enemy.y = 110;

        boolean result = enemy.enemyCollidesWithPlayer();

        Assertions.assertTrue(result, "Enemy should collide with player");
    }
    @Test
    void testUpdate() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        NormalEnemy enemy = new NormalEnemy(game, player);

        // Set player position to be close to enemy
        player.x = 100;
        player.y = 100;
        // Set enemy position to be close to player
        enemy.x = 110;
        enemy.y = 110;

        // Set initial health points
        player.healthPoints = 3;

        // Call update method to simulate enemy movement and collision
        enemy.update();

        // Check if health points are reduced after collision
        Assertions.assertEquals(2, player.healthPoints, "Player's health points should be reduced after collision");

        // Check if enemy is alive
        Assertions.assertTrue(enemy.isAlive, "Enemy should be alive after update");        
    }

}


