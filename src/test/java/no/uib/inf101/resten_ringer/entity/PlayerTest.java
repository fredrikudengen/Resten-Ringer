package no.uib.inf101.resten_ringer.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.resten_ringer.Game;
import no.uib.inf101.resten_ringer.GameController;

public class PlayerTest {
    
    @Test
    public void testPlayerDefaultValues() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);

        // Test default values
        assertEquals(game.getScreenWidth() / 2, player.getX());
        assertEquals(game.getScreenHeight() / 2, player.getY());
        assertEquals(6, player.getHealthpoints());
        assertEquals(4, player.getSpeed());
        assertEquals("right", player.getMovementDirection());
        assertTrue(player.getIsAlive());
    }


    @Test
    public void testPlayerMovement() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);

        gameController.upPressed = true;
        player.update();
        assertEquals(game.getScreenHeight() / 2 - 4, player.getY()); 

        gameController.upPressed = false;
        gameController.downPressed = true;
        player.update();
        assertEquals(game.getScreenHeight() / 2, player.getY()); 
    }
}