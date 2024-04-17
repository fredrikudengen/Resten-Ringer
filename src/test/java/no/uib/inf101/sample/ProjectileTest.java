package no.uib.inf101.sample;


import no.uib.inf101.sample.entity.NormalEnemy;
import no.uib.inf101.sample.entity.Player;
import no.uib.inf101.sample.entity.Projectile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectileTest {

    @Test
    void testProjectileCollidesWithEnemy() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);

        NormalEnemy normalEnemy = new NormalEnemy(game, player);
        Projectile projectile = new Projectile(game, player, gameController, "up", normalEnemy);

        // Set projectile position to collide with enemy
        projectile.x = normalEnemy.getNormalEnemyX();
        projectile.y = normalEnemy.getNormalEnemyY();

        boolean result = projectile.ProjectileCollidesWithEnemy();

        // Check if the projectile collides with the enemy
        Assertions.assertTrue(result, "Projectile should collide with enemy");
        // Check if the enemy's health points are reduced after collision
        Assertions.assertEquals(2, normalEnemy.healthPoints, "Enemy's health points should be reduced after collision");
    }

    @Test
    void testUpdate() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);

        NormalEnemy normalEnemy = new NormalEnemy(game, player);
        Projectile projectile = new Projectile(game, player, gameController, "up", normalEnemy);

        // Set initial projectile position
        projectile.x = player.getPlayerX();
        projectile.y = player.getPlayerY();

        // Call update method to simulate projectile movement
        projectile.update();

        // Check if projectile position is updated according to its direction
        Assertions.assertTrue(projectile.y < player.getPlayerY(), "Projectile should move upwards");
    }
}
