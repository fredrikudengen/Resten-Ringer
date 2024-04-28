package no.uib.inf101.sample.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;
import no.uib.inf101.sample.GameState;
import no.uib.inf101.sample.entity.Player;

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

        game.setGameState(GameState.ACTIVE_GAME);


        // Save the initial X position
        fastEnemy.setX(200);
        fastEnemy.setY(200);
        fastEnemy.setDirection("right");
        
        fastEnemy.update();

        // Check that the enemy has moved to the right
        assertEquals(200 + fastEnemy.getSpeed(), fastEnemy.getX());
    }

    @Test
    public void testSetXAndY() {
        // Create a mock game and player
        Game game = new Game();
        Player player = new Player(game, null);

        // Create a mock enemy instance
        NormalEnemy normalEnemy = new NormalEnemy(game, player);

        // Call the method to set x and y coordinates
        normalEnemy.setSpawn();

        // Get the x and y coordinates of the enemy
        int x = normalEnemy.getX();
        int y = normalEnemy.getY();

        // Check if the enemy is spawned in one of the valid positions
        assertTrue((x == 50 && y == 50) || // Top-left corner
                   (x == game.getScreenWidth() - 100 && y == game.getScreenHeight() - 100) || // Bottom-right corner
                   (x == game.getScreenWidth() - 100 && y == 50) || // Top-right corner
                   (x == 50 && y == game.getScreenHeight() - 50)); // Bottom-left corner
    }

    @Test
    public void testSimulateEnemyMoves() {
        // Create a Game instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Set player coordinates
        player.setX(100); // Sample X coordinate
        player.setY(150); // Sample Y coordinate

        // Create an Enemy instance
        NormalEnemy normalEnemy = new NormalEnemy(game, player);


        // Set enemy coordinates
        normalEnemy.setX(50); // Sample initial X coordinate
        normalEnemy.setY(50); // Sample initial Y coordinate

        // Call the method to simulate enemy movement
        normalEnemy.simulateEnemyMoves();

        // Assert the expected movement based on the player's coordinates
        assertEquals(52, normalEnemy.getY()); // Enemy should move towards the player's Y coordinate
    }

    @Test
    public void testKnockBackEnemy() {
        // Create a Game instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Set player coordinates
        player.setX(100); // Sample X coordinate
        player.setY(150); // Sample Y coordinate

        // Create an Enemy instance
        NormalEnemy normalEnemy = new NormalEnemy(game, player);

        // Set enemy coordinates
        normalEnemy.setX(100); // Sample initial X coordinate
        normalEnemy.setY(150); // Sample initial Y coordinate

        // Set movement direction of the enemy towards the player

        // Call the knockBackEnemy method
        normalEnemy.knockBackEnemy();

        // Assert that the enemy's X coordinate has been adjusted due to knockback
        assertEquals(50, normalEnemy.getX()); // Enemy should move to the left by 50 units
    }

    @Test
    public void testUpdate() {
        // Create a Game instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        game.setGameState(GameState.ACTIVE_GAME);

        // Set player coordinates
        player.setX(100); // Sample X coordinate
        player.setY(100); // Sample Y coordinate

        // Create an Enemy instance
        NormalEnemy normalEnemy = new NormalEnemy(game, player);

        // Set enemy coordinates
        normalEnemy.setX(100); // Sample initial X coordinate
        normalEnemy.setY(100); // Sample initial Y coordinate

        // Set movement direction of the enemy towards the player
        normalEnemy.setDirection("down");

        // Call the update method
        normalEnemy.update();

        // Assert that the player's healthpoints have been reduced due to collision
        assertEquals(5, player.getHealthpoints());

    }
}
