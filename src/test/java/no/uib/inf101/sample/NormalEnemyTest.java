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
        player.setX(100);
        player.setY(100);

        // Set enemy position to be close to player
        enemy.setX(100);
        enemy.setY(100);

        boolean result = enemy.getEnemyCollidesWithPlayer();

        Assertions.assertTrue(result, "Enemy should collide with player");
    }
    @Test
    void testUpdate() {
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

        // Set initial health points
        player.setHealthpoints(3);

        // Call update method to simulate enemy movement and collision
        enemy.update();

        // Check if health points are reduced after collision
        Assertions.assertEquals(2, player.getHealthpoints(), "Player's health points should be reduced after collision");

        // Check if enemy is alive
        Assertions.assertTrue(enemy.getIsAlive(), "Enemy should be alive after update");        
    }

}


