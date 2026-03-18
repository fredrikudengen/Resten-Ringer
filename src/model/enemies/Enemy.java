package model.enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import controller.Game;
import controller.GameState;
import model.Entity;
import model.Player;

public abstract class Enemy extends Entity {

    protected Game   game;
    protected Player player;

    private boolean isStopped     = false;
    private long    stopStartTime = 0;

    private static final Random RNG = new Random();  // one instance, reused

    /** 0.5 seconds expressed in nanoseconds (System.nanoTime() unit). */
    private static final long STOP_DURATION_NS = 500_000_000L;

    public Enemy(Game game, Player player) {
        this.game   = game;
        this.player = player;
        this.hitBox = new Rectangle(2, 0, 12, 16);
    }

    @Override
    protected abstract void setDefaultValues();

    /**
     * Draws this enemy. Each subclass provides its own sprite logic.
     *
     * @param g2 Graphics2D context
     */
    public abstract void draw(Graphics2D g2);

    // -------------------------------------------------------------------------
    // Spawning
    // -------------------------------------------------------------------------

    /**
     * Places this enemy at one of the four map corners.
     */
    protected void setSpawn() {
        int sw = game.getScreenWidth();
        int sh = game.getScreenHeight();
        switch (RNG.nextInt(4)) {
            case 0 -> { x = 50;       y = 50; }
            case 1 -> { x = sw - 100; y = sh - 100; }
            case 2 -> { x = sw - 100; y = 50; }
            case 3 -> { x = 50;       y = sh - 50; }
        }
    }

    // -------------------------------------------------------------------------
    // Collision helpers
    // -------------------------------------------------------------------------

    protected boolean enemyCollidesWithPlayer() {
        int ts = game.getTileSize();
        return rectanglesOverlap(
                x + 2,              y,
                x + ts - 2,         y + ts,
                player.getX() + 2,  player.getY(),
                player.getX() + ts - 2, player.getY() + ts);
    }

    // -------------------------------------------------------------------------
    // Movement
    // -------------------------------------------------------------------------

    /**
     * Moves the enemy one step toward the player, respecting tile collision.
     */
    protected void simulateEnemyMoves() {
        int dx = player.getX() - x;
        int dy = player.getY() - y;

        if (Math.abs(dx) > Math.abs(dy)) {
            collisionOn = false;
            game.getCollisionDetection().checkTile(this);
            if (collisionOn) {
                slideAroundCollision();
            } else {
                moveHorizontalFirst(dx, dy);
            }
        } else {
            collisionOn = false;
            game.getCollisionDetection().checkTile(this);
            if (collisionOn) {
                slideAroundCollision();
            } else {
                moveVerticalFirst(dx, dy);
            }
        }
    }

    private void moveHorizontalFirst(int dx, int dy) {
        if (dx < 0)      { x -= speed; movementDirection = Direction.LEFT; }
        else if (dx > 0) { x += speed; movementDirection = Direction.RIGHT; }
        else if (dy < 0) { y -= speed; movementDirection = Direction.UP; }
        else if (dy > 0) { y += speed; movementDirection = Direction.DOWN; }
    }

    private void moveVerticalFirst(int dx, int dy) {
        if (dy < 0)      { y -= speed; movementDirection = Direction.UP; }
        else if (dy > 0) { y += speed; movementDirection = Direction.DOWN; }
        else if (dx < 0) { x -= speed; movementDirection = Direction.LEFT; }
        else if (dx > 0) { x += speed; movementDirection = Direction.RIGHT; }
    }

    /** Nudges the enemy sideways when blocked by a tile. */
    private void slideAroundCollision() {
        switch (movementDirection) {
            case LEFT, RIGHT -> y += speed;
            case UP,   DOWN  -> x += speed;
        }
    }

    // -------------------------------------------------------------------------
    // Knockback
    // -------------------------------------------------------------------------

    protected void knockBackEnemy() {
        int sw = game.getScreenWidth();
        int sh = game.getScreenHeight();
        int ts = game.getTileSize();
        switch (movementDirection) {
            case UP    -> { if (y - 50 >= 20)           y -= 50; }
            case DOWN  -> { if (y + 50 <= sh - 60)      y += 50; }  // fixed: was subtracting
            case LEFT  -> { if (x - 50 >= 20)           x -= 50; }  // fixed: was adding
            case RIGHT -> { if (x + 50 <= sw - ts - 5)  x += 50; }  // fixed: was subtracting
        }
    }

    // -------------------------------------------------------------------------
    // Update
    // -------------------------------------------------------------------------

    /**
     * Advances enemy state: stun timer, collision damage, movement, animation.
     */
    public void update() {
        if (game.getGameState() != GameState.ACTIVE_GAME) return;

        // If currently stunned, wait out the timer then resume
        if (isStopped) {
            if (System.nanoTime() - stopStartTime >= STOP_DURATION_NS) {
                isStopped = false;
            } else {
                return;
            }
        }

        // Deal damage and apply knockback on contact with an unshocked player
        if (enemyCollidesWithPlayer() && !player.isShocked()) {
            player.setHealthpoints(player.getHealthpoints() - 1);
            knockBackEnemy();
            isStopped     = true;
            stopStartTime = System.nanoTime();
            return;
        }

        simulateEnemyMoves();

        if (healthPoints <= 0) {
            isAlive = false;
        }

        // Advance animation frame every 10 ticks
        if (++imageCounter > 10) {
            imageNum    = (imageNum == 1) ? 2 : 1;
            imageCounter = 0;
        }
    }
}