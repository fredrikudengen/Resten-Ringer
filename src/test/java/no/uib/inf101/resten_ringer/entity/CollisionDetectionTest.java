package no.uib.inf101.resten_ringer.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.resten_ringer.Game;
import no.uib.inf101.resten_ringer.entity.enemy.NormalEnemy;

public class CollisionDetectionTest {

    private Game game;
    private CollisionDetection collisionDetection;

    @BeforeEach
    public void setup() {
        game = new Game();
        collisionDetection = new CollisionDetection(game);
    }

    @Test
    public void testCheckTile_PlayerUp_NoCollision() {
        Player player = new Player(game, null);
        player.setMovementDirection("up");
        player.setX(100);
        player.setY(100);
        player.setSpeed(5);
        player.setCollisionOn(false);
        collisionDetection.checkTile(player);
        assertFalse(player.getCollisionOn());
    }

    @Test
    public void testCheckTile_EnemyLeft_NoCollision() {
        NormalEnemy enemy = new NormalEnemy(game, null);
        enemy.setMovementDirection("left");
        enemy.setX(100);
        enemy.setY(100);
        enemy.setSpeed(5);
        enemy.setCollisionOn(false);
        collisionDetection.checkTile(enemy);
        assertFalse(enemy.getCollisionOn());
    }

    @Test
    public void testCheckTile_EnemyRight_Collision() {
        NormalEnemy enemy = new NormalEnemy(game, null);
        enemy.setMovementDirection("right");
        enemy.setX(game.getTileSize() * 5);
        enemy.setY(50);
        enemy.setSpeed(5);
        enemy.setCollisionOn(false);
        collisionDetection.checkTile(enemy);
        assertTrue(enemy.getCollisionOn());
    }
}
