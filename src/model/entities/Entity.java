package model.entities;

import java.awt.Rectangle;

public abstract class Entity {

    /** Shared direction enum for movement and projectile firing. */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /** Number of game ticks between animation-frame toggles. Shared by all entity types. */
    protected static final int ANIMATION_TICK_RATE = 10;

    protected int x;
    protected int y;
    protected int speed;
    protected int healthPoints;
    protected int imageNum     = 1;
    protected int imageCounter = 0;

    protected boolean   isAlive;
    protected Direction movementDirection = Direction.RIGHT;

    protected Rectangle hitBox;

    // -------------------------------------------------------------------------
    // Template methods
    // -------------------------------------------------------------------------

    /**
     * Sets default values — called after the constructor.
     *
     * <p>Not abstract: Projectile cannot satisfy a no-arg version of this because
     * it needs a Player reference to initialise position. Subclasses that do have
     * a sensible no-arg reset call this and override it; subclasses that don't
     * (Projectile) simply omit it.
     */
    protected void setDefaultValues() {}

    // -------------------------------------------------------------------------
    // Shared behaviour — lives here so subclasses don't copy-paste it
    // -------------------------------------------------------------------------

    /**
     * Advances the animation frame counter and toggles between frame 1 and 2
     * every {@link #ANIMATION_TICK_RATE} ticks.
     *
     * <p>Previously duplicated verbatim in Enemy, Player, and Projectile.
     */
    protected void tickAnimation() {
        if (++imageCounter > ANIMATION_TICK_RATE) {
            imageNum     = (imageNum == 1) ? 2 : 1;
            imageCounter = 0;
        }
    }

    /**
     * Returns this entity's hitbox in world coordinates.
     * {@code hitBox} stores offset and size relative to (x, y).
     *
     * <p>This is the single source of truth for bounds — used by CollisionDetection
     * for both tile checks and entity-vs-entity tests via
     * {@link CollisionDetection#entitiesOverlap}.
     */
    public Rectangle getWorldHitBox() {
        return new Rectangle(x, y, hitBox.width, hitBox.height);
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    public int  getX()                            { return x; }
    public void setX(int x)                       { this.x = x; }

    public int  getY()                            { return y; }
    public void setY(int y)                       { this.y = y; }

    public int  getSpeed()                        { return speed; }
    public void setSpeed(int speed)               { this.speed = speed; }

    public boolean isAlive()                      { return isAlive; }

    public Direction getMovementDirection()       { return movementDirection; }
    public void setMovementDirection(Direction d) { this.movementDirection = d; }

    public int  getHealthpoints()                 { return healthPoints; }
    public void setHealthpoints(int hp)           { this.healthPoints = hp; }

    public Rectangle getHitBox()                  { return hitBox; }
}