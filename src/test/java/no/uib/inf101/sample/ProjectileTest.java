package no.uib.inf101.sample;


import no.uib.inf101.sample.entity.NormalEnemy;
import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.Projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ProjectileTest {


    @Test
    void testUpdate() {
        Game game = new Game();
        GameController gameController = new GameController();
        Player player = new Player(game, gameController);

        NormalEnemy normalEnemy = new NormalEnemy(game, player);
        Projectile projectile = new Projectile(game, player, gameController, "up", normalEnemy);

        // Set initial projectile position
        projectile.x = player.getPlayerX();
        projectile.y = player.getPlayerY();

        // Call update method to simulate projectile movement
        projectile.update();

        // Check if projectile position is updated according to its direction
        assertTrue(projectile.y < player.getPlayerY(), "Projectile should move upwards");
    }
}
