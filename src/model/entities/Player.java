package model.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import model.world.Game;
import model.entities.enemies.StunTimer;
import controller.GameController;

public class Player extends Entity implements DrawAndUpdate {

    // -------------------------------------------------------------------------
    // Sprites — loaded once per JVM by the class loader, atomically.
    //
    // Two SpriteSets: walk frames and hit frames. Fallbacks are baked in at
    // construction time (no up-hit sprite exists → UP falls back to DOWN frames).
    // -------------------------------------------------------------------------

    private static final SpriteSet WALK_SPRITES;
    private static final SpriteSet HIT_SPRITES;
    private static final BufferedImage BEER_ICON;

    static {
        try {
            var up    = SpriteSet.load(Player.class, "/resources/Up_shotgun_walking_1.png",    "/resources/Up_shotgun_walking_2.png");
            var down  = SpriteSet.load(Player.class, "/resources/Down_shotgun_walking_1.png",  "/resources/Down_shotgun_walking_2.png");
            var left  = SpriteSet.load(Player.class, "/resources/Left_shotgun_walking_1.png",  "/resources/Left_shotgun_walking_2.png");
            var right = SpriteSet.load(Player.class, "/resources/Right_shotgun_walking_1.png", "/resources/Right_shotgun_walking_2.png");
            WALK_SPRITES = new SpriteSet(up, down, left, right);

            var downHit  = SpriteSet.load(Player.class, "/resources/Down_hit_1.png",  "/resources/Down_hit_2.png");
            var leftHit  = SpriteSet.load(Player.class, "/resources/Left_hit_1.png",  "/resources/Left_hit_2.png");
            var rightHit = SpriteSet.load(Player.class, "/resources/Right_hit_1.png", "/resources/Right_hit_2.png");
            // No up-hit sprite — UP falls back to DOWN hit frames.
            HIT_SPRITES = new SpriteSet(downHit, downHit, leftHit, rightHit);

            BEER_ICON = SpriteSet.loadSingle(Player.class, "/resources/beer.png");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------

    private final Game           game;
    private final GameController gameController;

    /**
     * Facing direction — updated by both movement and shoot keys.
     * Kept separate from {@code movementDirection} (inherited) which is used
     * only for tile collision correction.
     */
    private Direction facing = Direction.RIGHT;

    /**
     * Tracks the invincibility window after taking a hit (1 second).
     * Replaces the old bare-field pair (isShocked / shockStartTime) — same
     * pattern as StunTimer on Enemy, just with a longer duration.
     */
    private final StunTimer shockTimer = new StunTimer(1_000_000_000L); // 1 s

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    public Player(Game game, GameController gameController) {
        this.game           = game;
        this.gameController = gameController;
        this.hitBox         = new Rectangle(2, 0, 12, 16);
        setDefaultValues();
    }

    // -------------------------------------------------------------------------
    // Initialisation
    // -------------------------------------------------------------------------

    @Override
    public void setDefaultValues() {
        x            = game.getScreenWidth()  / 2;
        y            = game.getScreenHeight() / 2;
        healthPoints = 6;
        speed        = 4;
        facing       = Direction.RIGHT;
        isAlive      = true;
        // shockTimer starts inactive — no explicit reset needed
    }

    // -------------------------------------------------------------------------
    // Update
    // -------------------------------------------------------------------------

    @Override
    public void update() {
        handleMovement();
        correctTileCollision();
        updateFacingFromShot();
        clampToBounds();
        // shockTimer self-clears — no explicit tick needed here
    }

    private void handleMovement() {
        boolean moving = gameController.isUpPressed()    || gameController.isDownPressed()
                || gameController.isLeftPressed()  || gameController.isRightPressed();
        if (!moving) return;

        if (gameController.isUpPressed()) {
            facing = movementDirection = Direction.UP;    y -= speed;
        } else if (gameController.isDownPressed()) {
            facing = movementDirection = Direction.DOWN;  y += speed;
        } else if (gameController.isLeftPressed()) {
            facing = movementDirection = Direction.LEFT;  x -= speed;
        } else {
            facing = movementDirection = Direction.RIGHT; x += speed;
        }

        tickAnimation(); // inherited from Entity — no duplication
    }

    private void correctTileCollision() {
        if (!game.getCollisionDetection().checkTile(this)) return;
        switch (movementDirection) {
            case UP    -> y += speed;
            case DOWN  -> y -= speed;
            case LEFT  -> x += speed;
            case RIGHT -> x -= speed;
        }
    }

    private void updateFacingFromShot() {
        if (gameController.isShotKeyPressed()) {
            facing = gameController.getShotDirection();
        }
    }

    private void clampToBounds() {
        int ts = game.getTileSize();
        y = Math.max(20,  Math.min(y, game.getScreenHeight() - 60));
        x = Math.max(5,   Math.min(x, game.getScreenWidth()  - ts - 5));
    }

    // -------------------------------------------------------------------------
    // Rendering
    // -------------------------------------------------------------------------

    /**
     * Draws the player sprite and the health bar.
     *
     * <p>Previously two parallel switches (pick image, then draw with offsets) for
     * both the shocked and normal states. Now: pick the right SpriteSet, select
     * the frame, then one switch for position and dimensions.
     */
    @Override
    public void draw(Graphics2D g2) {
        int ts = game.getTileSize();

        BufferedImage image = shockTimer.isActive()
                ? HIT_SPRITES.get(facing, imageNum)
                : WALK_SPRITES.get(facing, imageNum);

        // Sprite dimensions and offsets vary per facing direction — the weapon
        // extends the bounding box differently for each orientation.
        switch (facing) {
            case RIGHT -> g2.drawImage(image, x,      y,      ts * 2, ts,     null);
            case LEFT  -> g2.drawImage(image, x - ts, y,      ts * 2, ts,     null);
            case UP    -> g2.drawImage(image, x,      y - ts, ts,     ts * 2, null);
            case DOWN  -> g2.drawImage(image, x,      y,      ts,     ts * 2, null);
        }

        drawHealthBar(g2, ts);
    }

    private void drawHealthBar(Graphics2D g2, int ts) {
        int iconSize    = ts / 2;
        int iconSpacing = iconSize + 5;
        for (int i = 0; i < healthPoints; i++) {
            g2.drawImage(BEER_ICON, 10 + i * iconSpacing, 20, iconSize, iconSize, null);
        }
    }

    // -------------------------------------------------------------------------
    // Shock / invincibility API
    // -------------------------------------------------------------------------

    /** Starts the post-hit invincibility window. Called by Enemy on collision. */
    public void setShocked() {
        shockTimer.start();
    }

    /** True while the player is in the post-hit invincibility window. */
    public boolean isShocked() {
        return shockTimer.isActive();
    }

    // isAlive() is already provided by Entity — the override here was redundant.
}