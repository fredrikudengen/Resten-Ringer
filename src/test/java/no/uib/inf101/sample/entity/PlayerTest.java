package no.uib.inf101.sample.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;
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
        assertEquals(game.getScreenWidth() / 2, player.getX());
        assertEquals(game.getScreenHeight() / 2, player.getY());
        assertEquals(6, player.getHealthpoints());
        assertEquals(4, player.getSpeed());
        assertEquals("right", player.getDirection());
        assertTrue(player.getIsAlive());
    }


    @Test
    public void testPlayerMovement() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);

        // Move the player up
        gameController.upPressed = true;
        player.update();
        assertEquals(game.getScreenHeight() / 2 - 4, player.getY()); 

        // Move the player down
        gameController.upPressed = false;
        gameController.downPressed = true;
        player.update();
        assertEquals(game.getScreenHeight() / 2, player.getY()); 
    }
}