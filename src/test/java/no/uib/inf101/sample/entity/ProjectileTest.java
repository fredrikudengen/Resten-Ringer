package no.uib.inf101.sample.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.GameController;
import no.uib.inf101.sample.entity.enemy.NormalEnemy;

public class ProjectileTest {

    @Test
    public void testProjectileCollidesWithEnemy() {
        // Create a Game instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Create a Projectile instance
        Projectile projectile = new Projectile(game, player, gameController, "up");
        game.getEnemyList();

        // Create an Enemy instance
        NormalEnemy normalEnemy = new NormalEnemy(game, player);
        game.addToEnemyList(normalEnemy);

        normalEnemy.setX(10);
        normalEnemy.setY(10);

        // Set projectile position to overlap with the enemy
        projectile.setX(9);
        projectile.setY(9);

        // Ensure the collision is detected
        assertTrue(projectile.projectileCollidesWithEnemy(0));
    }

    @Test
    public void testProjectileDoesntCollidesWithEnemy() {
        // Create a Game instance
        Game game = new Game();
        GameController gameController = new GameController(game);

        // Create a Player instance
        Player player = new Player(game, gameController);

        // Create a Projectile instance
        Projectile projectile = new Projectile(game, player, gameController, "up");
        game.getEnemyList();

        // Create an Enemy instance
        NormalEnemy normalEnemy = new NormalEnemy(game, player);
        game.addToEnemyList(normalEnemy);
        projectile.setX(100);
        projectile.setY(100);

        normalEnemy.setX(10);
        normalEnemy.setY(10);

        // Set projectile position to overlap with the enemy

        // Ensure the collision is detected
        assertFalse(projectile.projectileCollidesWithEnemy(0));
    }
}