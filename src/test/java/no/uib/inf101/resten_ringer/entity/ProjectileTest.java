package no.uib.inf101.resten_ringer.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import org.junit.jupiter.api.Test;

import no.uib.inf101.resten_ringer.Game;
import no.uib.inf101.resten_ringer.GameController;
import no.uib.inf101.resten_ringer.entity.enemy.NormalEnemy;

public class ProjectileTest {

    @Test
    public void testProjectileCollidesWithEnemy() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        Projectile projectile = new Projectile(game, player, gameController, "up");
        game.getEnemyList();

        NormalEnemy normalEnemy = new NormalEnemy(game, player);
        game.addToEnemyList(normalEnemy);

        normalEnemy.setX(10);
        normalEnemy.setY(10);

        projectile.setX(9);
        projectile.setY(9);

        // Collision should be detected
        assertTrue(projectile.projectileCollidesWithEnemy(0));
    }

    @Test
    public void testProjectileDoesntCollidesWithEnemy() {
        Game game = new Game();
        GameController gameController = new GameController(game);
        Player player = new Player(game, gameController);
        Projectile projectile = new Projectile(game, player, gameController, "up");
        game.getEnemyList();

        NormalEnemy normalEnemy = new NormalEnemy(game, player);
        game.addToEnemyList(normalEnemy);

        projectile.setX(100);
        projectile.setY(100);

        normalEnemy.setX(10);
        normalEnemy.setY(10);


        // Collision isn't detected
        assertFalse(projectile.projectileCollidesWithEnemy(0));
    }
}