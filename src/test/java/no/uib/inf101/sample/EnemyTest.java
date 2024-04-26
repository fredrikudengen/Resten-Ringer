package no.uib.inf101.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.enemy.FastEnemy;
import no.uib.inf101.sample.entity.enemy.NormalEnemy;
import no.uib.inf101.sample.entity.enemy.SlowEnemy;

public class EnemyTest {
    @Test
    public void testFastEnemyDefaultValues() {
        // Create a Game instance and a GameController instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Create a FastEnemy instance
        FastEnemy fastEnemy = new FastEnemy(game, player);

        // Test default values
        assertEquals(2, fastEnemy.getHealthpoints());
        assertEquals(3, fastEnemy.getSpeed());
        assertEquals("right", fastEnemy.getDirection());
        assertTrue(fastEnemy.getIsAlive());
    }

    @Test
    public void testNormalEnemyDefaultValues() {
        // Create a Game instance and a GameController instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Create a NormalEnemy instance
        NormalEnemy normalEnemy = new NormalEnemy(game, player);

        assertEquals(3, normalEnemy.getHealthpoints());
        assertEquals(2, normalEnemy.getSpeed());
        assertEquals("right", normalEnemy.getDirection());
        assertTrue(normalEnemy.getIsAlive());
    }

    @Test
    public void testSlowEnemyDefaultValues() {
        // Create a Game instance and a GameController instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Create a SlowEnemy instance
        SlowEnemy slowEnemy = new SlowEnemy(game, player);

        assertEquals(4, slowEnemy.getHealthpoints());
        assertEquals(1, slowEnemy.getSpeed());
        assertEquals("right", slowEnemy.getDirection());
        assertTrue(slowEnemy.getIsAlive());
    }

    @Test
    public void testFastEnemyMovement() {
        // Create a Game instance and a GameController instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Create a FastEnemy instance
        FastEnemy fastEnemy = new FastEnemy(game, player);

        // Save the initial X position
        fastEnemy.setX(100);

        // Move the enemy
        fastEnemy.update();

        // Check that the enemy has moved to the right
        assertEquals(100 + fastEnemy.getSpeed(), fastEnemy.getX());
    }

}
