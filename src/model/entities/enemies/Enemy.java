package model.entities.enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import model.world.Game;
import controller.GameState;
import model.entities.Entity;
import model.entities.Player;
import model.entities.SpriteSet;

public abstract class Enemy extends Entity {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    private static final Random RNG = new Random();

    /** Pixels the enemy is knocked back on a hit. */
    private static final int KNOCKBACK_DISTANCE  = 50;

    /** Minimum pixel margin from any screen edge (top / left). */
    private static final int BOUNDARY_MARGIN     = 20;

    /** Extra bottom margin so the enemy doesn't clip below the HUD. */
    private static final int BOUNDARY_MARGIN_BOT = 60;

    /** Right-edge fine-tune offset (accounts for tile-size overshoot). */
    private static final int BOUNDARY_MARGIN_R   = 5;

    /** Spawn offset from each screen corner, in pixels. */
    private static final int SPAWN_OFFSET        = 50;

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------

    protected Game      game;
    protected Player    player;

    /**
     * Directional animation frames for this enemy type.
     * Set by each subclass constructor from its own static SpriteSet.
     * Keeping it here lets draw() live once in Enemy rather than being
     * copy-pasted into every subclass.
     */
    protected SpriteSet spriteSet;

    /**
     * Manages the brief stun that follows a player hit.
     * Extracted from two raw fields (isStopped / stopStartTime) so the stun
     * concept has a single home and the fields can't drift out of sync.
     */
    private final StunTimer stunTimer = new StunTimer(500_000_000L); // 0.5 s

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    public Enemy(Game game, Player player) {
        this.game             = game;
        this.player           = player;
        this.hitBox           = new Rectangle(0, 0, 32, 32);
        // Universal defaults — no need to repeat these in every subclass.
        this.isAlive          = true;
        this.movementDirection = Direction.RIGHT;
    }

    // -------------------------------------------------------------------------
    // Abstract contract
    // -------------------------------------------------------------------------

    @Override
    protected abstract void setDefaultValues();

    // -------------------------------------------------------------------------
    // Rendering
    // -------------------------------------------------------------------------

    /**
     * Draws this enemy using its SpriteSet.
     *
     * <p>Previously overridden in every subclass with identical logic. The only
     * difference between subclasses was which frames mapped to which direction —
     * that mapping is now encoded in the SpriteSet itself (fallbacks are baked
     * in at load time), so one draw() here is sufficient.
     *
     * @param g2 Graphics2D context
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(
                spriteSet.get(movementDirection, imageNum),
                x, y,
                game.getTileSize(), game.getTileSize(),
                null
        );
    }

    // -------------------------------------------------------------------------
    // Update  (reads like a table of contents — each step has its own method)
    // -------------------------------------------------------------------------

    /**
     * Advances enemy state for one game tick.
     * Order matters: dead/inactive guards run first, then combat, then movement.
     */
    public void update() {
        if (game.getGameState() != GameState.ACTIVE_GAME) return;

        // BUG FIX: death was previously checked *after* movement, letting a
        // dead enemy slide one extra tick.
        if (!isAlive) return;

        if (stunTimer.isActive()) return;

        if (handlePlayerCollision()) return;   // returns true when a hit occurred

        simulateEnemyMoves();

        // Must be checked after movement (and after damage applied by handlePlayerCollision
        // or a projectile hit), so UpdateGame.updateEnemies() sees the updated flag
        // on the very next call rather than one tick later.
        if (healthPoints <= 0) isAlive = false;

        tickAnimation();
    }

    // -------------------------------------------------------------------------
    // Combat
    // -------------------------------------------------------------------------

    /**
     * Deals damage, triggers knockback and stun if the enemy touches an
     * unshielded player.
     *
     * @return true when a hit occurred (update() should skip movement this tick)
     */
    private boolean handlePlayerCollision() {
        if (!enemyCollidesWithPlayer() || player.isShocked()) return false;

        player.setHealthpoints(player.getHealthpoints() - 1);
        knockBackEnemy();
        stunTimer.start();
        return true;
    }

    /**
     * Returns true if this enemy's hitbox overlaps the player's.
     *
     * <p>Both hitboxes are defined as {@code new Rectangle(2, 0, 12, 16)}, so
     * {@code getWorldHitBox()} already encodes the 2 px side insets that were
     * previously reconstructed manually via {@code rectanglesOverlap}. The manual
     * version was redundant — {@code entitiesOverlap} gives the same result.
     */
    protected boolean enemyCollidesWithPlayer() {
        return game.getCollisionDetection().entitiesOverlap(this, player);
    }

    // -------------------------------------------------------------------------
    // Knockback
    // -------------------------------------------------------------------------

    /**
     * Pushes the enemy away from the player after a hit.
     *
     * BUG FIX: the original code moved the enemy in the same direction it was
     * already travelling (toward the player), which pushed it deeper into the
     * player's hitbox.  Knockback must be in the *opposite* direction.
     */
    protected void knockBackEnemy() {
        int sw = game.getScreenWidth();
        int sh = game.getScreenHeight();
        switch (movementDirection) {
            // Enemy was heading UP   → knock it DOWN (away from player)
            case UP    -> { if (y + KNOCKBACK_DISTANCE <= sh - BOUNDARY_MARGIN_BOT) y += KNOCKBACK_DISTANCE; }
            // Enemy was heading DOWN → knock it UP
            case DOWN  -> { if (y - KNOCKBACK_DISTANCE >= BOUNDARY_MARGIN)          y -= KNOCKBACK_DISTANCE; }
            // Enemy was heading LEFT → knock it RIGHT
            case LEFT  -> { if (x + KNOCKBACK_DISTANCE <= sw - game.getTileSize() - BOUNDARY_MARGIN_R) x += KNOCKBACK_DISTANCE; }
            // Enemy was heading RIGHT → knock it LEFT
            case RIGHT -> { if (x - KNOCKBACK_DISTANCE >= BOUNDARY_MARGIN)          x -= KNOCKBACK_DISTANCE; }
        }
    }

    // -------------------------------------------------------------------------
    // Movement
    // -------------------------------------------------------------------------

    /**
     * Moves the enemy one step toward the player, respecting tile collision.
     *
     * movementDirection is committed before checkTile() so the collision system
     * tests the correct edge.  dx/dy are computed once and passed through to
     * avoid redundant player-position lookups in slideAroundCollision().
     */
    protected void simulateEnemyMoves() {
        int dx = player.getX() - x;
        int dy = player.getY() - y;

        movementDirection = resolveDirection(dx, dy);

        boolean blocked = game.getCollisionDetection().checkTile(this);

        if (blocked) {
            slideAroundCollision(dx, dy);
        } else if (Math.abs(dx) > Math.abs(dy)) {
            x += (dx < 0) ? -speed : speed;
        } else {
            y += (dy < 0) ? -speed : speed;
        }
    }

    /**
     * Picks the primary movement axis: horizontal when |dx| > |dy|, else vertical.
     * Guaranteed to return a non-null direction because at least one of dx / dy
     * will be non-zero when the enemy isn't standing on the player.
     */
    private Direction resolveDirection(int dx, int dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            return (dx < 0) ? Direction.LEFT : Direction.RIGHT;
        } else {
            return (dy < 0) ? Direction.UP : Direction.DOWN;
        }
    }

    /**
     * Slides along the perpendicular axis when the primary path is blocked.
     *
     * @param dx horizontal delta to player (pre-computed, not re-fetched)
     * @param dy vertical delta to player (pre-computed, not re-fetched)
     */
    private void slideAroundCollision(int dx, int dy) {
        switch (movementDirection) {
            case LEFT, RIGHT -> y += (dy < 0) ? -speed : speed;
            case UP,   DOWN  -> x += (dx < 0) ? -speed : speed;
        }
    }

    // -------------------------------------------------------------------------
    // Spawning
    // -------------------------------------------------------------------------

    /**
     * Places this enemy at one of the four screen corners.
     *
     * Note: if the game ever moves to a scrolling world larger than the screen,
     * this will need to use world coordinates instead.
     */
    protected void setSpawn() {
        int sw = game.getScreenWidth();
        int sh = game.getScreenHeight();
        switch (RNG.nextInt(4)) {
            case 0 -> { x = SPAWN_OFFSET;        y = SPAWN_OFFSET; }
            case 1 -> { x = sw - SPAWN_OFFSET * 2; y = sh - SPAWN_OFFSET * 2; }
            case 2 -> { x = sw - SPAWN_OFFSET * 2; y = SPAWN_OFFSET; }
            case 3 -> { x = SPAWN_OFFSET;        y = sh - SPAWN_OFFSET; }
        }
    }

}