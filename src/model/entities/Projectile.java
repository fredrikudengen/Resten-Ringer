package model.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import model.world.Game;
import model.entities.enemies.Enemy;

public class Projectile extends Entity implements DrawAndUpdate {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Bullet render size: original 4 px asset scaled up by 3. */
    private static final int BULLET_SIZE = 4 * 3; // = 12 px

    /**
     * Pixel offsets from the player's origin (x, y) to the gun barrel for each
     * firing direction. Makes bullets appear to emerge from the weapon rather
     * than the player's top-left corner.
     */
    private static final int BARREL_OFFSET_SIDE_X = 40; // LEFT / RIGHT: horizontal barrel offset
    private static final int BARREL_OFFSET_SIDE_Y = 20; // LEFT / RIGHT: vertical barrel offset
    private static final int BARREL_OFFSET_UP_X   = 25; // UP:   horizontal centering offset
    private static final int BARREL_OFFSET_UP_Y   = 20; // UP:   upward barrel offset
    private static final int BARREL_OFFSET_DOWN_Y  = 30; // DOWN: downward barrel offset

    // -------------------------------------------------------------------------
    // Sprites — loaded once per JVM by the class loader, atomically.
    // -------------------------------------------------------------------------

    private static final SpriteSet SPRITES;

    static {
        try {
            var up    = SpriteSet.load(Projectile.class, "/resources/Up_bullet_1.png",    "/resources/Up_bullet_2.png");
            var down  = SpriteSet.load(Projectile.class, "/resources/Down_bullet_1.png",  "/resources/Down_bullet_2.png");
            var left  = SpriteSet.load(Projectile.class, "/resources/Left_bullet_1.png",  "/resources/Left_bullet_2.png");
            var right = SpriteSet.load(Projectile.class, "/resources/Right_bullet_1.png", "/resources/Right_bullet_2.png");
            SPRITES = new SpriteSet(up, down, left, right);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------

    private final Game      game;
    private final Direction shotDirection;

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    /**
     * No longer throws IOException — sprite loading moved to the static initializer.
     *
     * @param game          the game instance (for screen bounds)
     * @param player        spawning player (position source)
     * @param shotDirection direction the bullet travels
     */
    public Projectile(Game game, Player player, Direction shotDirection) {
        this.game          = game;
        this.shotDirection = shotDirection;

        // Entity.setDefaultValues() is intentionally not called here — Projectile
        // needs the Player reference to set position, so init is done inline.
        x       = player.getX();
        y       = player.getY();
        speed   = 8;
        isAlive = true;
        hitBox  = new Rectangle(0, 0, BULLET_SIZE, BULLET_SIZE);
    }

    // -------------------------------------------------------------------------
    // Bounds and collision
    // -------------------------------------------------------------------------

    /**
     * Returns true if this projectile has left the visible screen area.
     * The caller (UpdateGame) should remove it from the active list when true.
     */
    public boolean isOutOfBounds() {
        return x > game.getScreenWidth()  || x < 0
                || y > game.getScreenHeight() || y < 0;
    }

    /**
     * Returns true if this projectile's hitbox intersects the given enemy's.
     * Delegates to {@link CollisionDetection#entitiesOverlap} — the single home
     * for all collision logic in the codebase.
     */
    public boolean collidesWithEnemy(Enemy enemy) {
        return game.getCollisionDetection().entitiesOverlap(this, enemy);
    }

    // -------------------------------------------------------------------------
    // Update
    // -------------------------------------------------------------------------

    @Override
    public void update() {
        switch (shotDirection) {
            case UP    -> y -= speed;
            case DOWN  -> y += speed;
            case LEFT  -> x -= speed;
            case RIGHT -> x += speed;
        }
        tickAnimation(); // inherited from Entity
    }

    // -------------------------------------------------------------------------
    // Rendering
    // -------------------------------------------------------------------------

    @Override
    public void draw(Graphics2D g2) {
        if (!isAlive) return;

        BufferedImage image = SPRITES.get(shotDirection, imageNum);
        int bs = BULLET_SIZE;

        // Offset the bullet to the gun barrel position for each facing direction.
        switch (shotDirection) {
            case RIGHT -> g2.drawImage(image, x + BARREL_OFFSET_SIDE_X, y + BARREL_OFFSET_SIDE_Y, bs * 2, bs,     null);
            case LEFT  -> g2.drawImage(image, x - BARREL_OFFSET_SIDE_X, y + BARREL_OFFSET_SIDE_Y, bs * 2, bs,     null);
            case UP    -> g2.drawImage(image, x + BARREL_OFFSET_UP_X,   y - BARREL_OFFSET_UP_Y,   bs,     bs * 2, null);
            case DOWN  -> g2.drawImage(image, x,                         y + BARREL_OFFSET_DOWN_Y, bs,     bs * 2, null);
        }
    }
}