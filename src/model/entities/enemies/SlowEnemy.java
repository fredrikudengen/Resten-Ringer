package model.entities.enemies;

import model.entities.SpriteSet;
import model.world.Game;
import model.entities.Player;

import static model.entities.SpriteSet.load;

public class SlowEnemy extends Enemy {

    // -------------------------------------------------------------------------
    // Sprites — loaded exactly once per JVM, atomically, by the class loader.
    //
    // The static initializer replaces the old (imagesLoaded + getImage()) pattern
    // which was not thread-safe and forced IOException through the constructor.
    // ExceptionInInitializerError is the correct signal for "a required resource
    // is missing — this class cannot be used".
    // -------------------------------------------------------------------------

    private static final SpriteSet SPRITES;

    static {
        try {
            var up    = load(SlowEnemy.class, "/resources/Up_slow_enemy_1.png",    "/resources/Up_slow_enemy_2.png");
            var left  = load(SlowEnemy.class, "/resources/Left_slow_enemy_1.png",  "/resources/Left_slow_enemy_2.png");
            var right = load(SlowEnemy.class, "/resources/Right_slow_enemy_1.png", "/resources/Right_slow_enemy_2.png");
            // No distinct DOWN sprite — right frames used as the fallback.
            SPRITES = new SpriteSet(up, right, left, right);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    public SlowEnemy(Game game, Player player) {
        super(game, player);
        this.spriteSet = SPRITES;
        setDefaultValues();
    }

    // -------------------------------------------------------------------------
    // Stats
    // -------------------------------------------------------------------------

    @Override
    protected void setDefaultValues() {
        setSpawn();
        healthPoints = 4;
        speed        = 1;
        // isAlive and movementDirection are set to their defaults in Enemy()
    }
}