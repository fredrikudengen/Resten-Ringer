package model.entities.enemies;

import model.entities.SpriteSet;
import model.world.Game;
import model.entities.Player;

import static model.entities.SpriteSet.load;

public class FastEnemy extends Enemy {

    // -------------------------------------------------------------------------
    // Sprites
    //
    // FastEnemy has no distinct DOWN sprite.
    // The LEFT frames are used as the downward fallback — declared here so
    // draw() in Enemy needs no special-case knowledge of this type.
    // -------------------------------------------------------------------------

    private static final SpriteSet SPRITES;

    static {
        try {
            var up    = load(FastEnemy.class, "/resources/Up_police_1.png",    "/resources/Up_police_2.png");
            var left  = load(FastEnemy.class, "/resources/Left_police_1.png",  "/resources/Left_police_2.png");
            var right = load(FastEnemy.class, "/resources/Right_police_1.png", "/resources/Right_police_2.png");
            // DOWN falls back to LEFT frames.
            SPRITES = new SpriteSet(up, left, left, right);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    public FastEnemy(Game game, Player player) {
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
        healthPoints = 2;
        speed        = 3;
    }
}