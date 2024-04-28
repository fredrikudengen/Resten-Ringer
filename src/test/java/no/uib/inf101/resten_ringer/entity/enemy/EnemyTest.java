package no.uib.inf101.resten_ringer.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.resten_ringer.Game;
import no.uib.inf101.resten_ringer.GameController;
import no.uib.inf101.resten_ringer.GameState;
import no.uib.inf101.resten_ringer.entity.Player;

public class EnemyTest {

    @Test
    public void testFastEnemyDefaultValues() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        FastEnemy fastEnemy = new FastEnemy(game, player);

        assertEquals(2, fastEnemy.getHealthpoints());
        assertEquals(3, fastEnemy.getSpeed());
        assertEquals("right", fastEnemy.getMovementDirection());
        assertTrue(fastEnemy.getIsAlive());
    }

    @Test
    public void testNormalEnemyDefaultValues() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        NormalEnemy normalEnemy = new NormalEnemy(game, player);

        assertEquals(3, normalEnemy.getHealthpoints());
        assertEquals(2, normalEnemy.getSpeed());
        assertEquals("right", normalEnemy.getMovementDirection());
        assertTrue(normalEnemy.getIsAlive());
    }

    @Test
    public void testSlowEnemyDefaultValues() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        SlowEnemy slowEnemy = new SlowEnemy(game, player);

        assertEquals(4, slowEnemy.getHealthpoints());
        assertEquals(1, slowEnemy.getSpeed());
        assertEquals("right", slowEnemy.getMovementDirection());
        assertTrue(slowEnemy.getIsAlive());
    }

    @Test
    public void testFastEnemyMovement() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        FastEnemy fastEnemy = new FastEnemy(game, player);

        game.setGameState(GameState.ACTIVE_GAME);
        fastEnemy.setX(200);
        fastEnemy.setY(200);
        fastEnemy.setMovementDirection("right");
        fastEnemy.update();

        // Check that the enemy has moved to the right
        assertEquals(200 + fastEnemy.getSpeed(), fastEnemy.getX());
    }

    @Test
    public void testSetXAndY() {
        Game game = new Game();
        Player player = new Player(game, null);
        NormalEnemy normalEnemy = new NormalEnemy(game, player);
        normalEnemy.setSpawn();

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
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        NormalEnemy normalEnemy = new NormalEnemy(game, player);

        // Set player coordinates
        player.setX(100); // resten_ringer X coordinate
        player.setY(150); // Sample Y coordinate

        // Set enemy coordinates
        normalEnemy.setX(50);
        normalEnemy.setY(50); 

        normalEnemy.simulateEnemyMoves();

        // Assert the expected movement based on the player's coordinates
        assertEquals(52, normalEnemy.getY()); // Enemy should move towards the player's Y coordinate
    }

    @Test
    public void testKnockBackEnemy() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        NormalEnemy normalEnemy = new NormalEnemy(game, player);

        player.setX(100);
        player.setY(150); 

        normalEnemy.setX(100); 
        normalEnemy.setY(150); 

        normalEnemy.knockBackEnemy();

        // Assert that the enemy's X coordinate has been adjusted due to knockback
        assertEquals(50, normalEnemy.getX()); 
    }

    @Test
    public void testUpdate() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        NormalEnemy normalEnemy = new NormalEnemy(game, player);

        game.setGameState(GameState.ACTIVE_GAME);

        // Set player coordinates
        player.setX(100); 
        player.setY(100); 

        // Set enemy coordinates
        normalEnemy.setX(100);
        normalEnemy.setY(100);

        normalEnemy.setMovementDirection("down");
        normalEnemy.update();

        // Assert that the player's healthpoints have been reduced due to collision
        assertEquals(5, player.getHealthpoints());

    }
}
