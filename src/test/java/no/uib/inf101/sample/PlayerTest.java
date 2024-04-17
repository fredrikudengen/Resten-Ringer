package no.uib.inf101.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.entity.Player;

public class PlayerTest {
    
    @Test
    public void testPlayerDefaultValues() {
        // Create a Game instance and a GameController instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Test default values
        assertEquals(140, player.x);
        assertEquals(100, player.getPlayerY());
        assertEquals(10, player.healthPoints);
        assertEquals(4, player.getPlayerSpeed());
        assertEquals("down", player.getDirection());
        assertEquals(true, player.isAlive());
    }


    @Test
    public void testPlayerMovement() {
        // Create a Game instance and a GameController instance (you might need mocks or stubs)
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Move the player up
        gameController.upPressed = true;
        player.update();
        assertEquals(96, player.getPlayerY()); // Assuming speed is 4

        // Move the player down
        gameController.upPressed = false;
        gameController.downPressed = true;
        player.update();
        assertEquals(100, player.getPlayerY()); // Assuming speed is 4
    }
}