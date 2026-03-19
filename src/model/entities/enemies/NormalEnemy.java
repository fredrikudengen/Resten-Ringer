package model.entities.enemies;

import model.entities.SpriteSet;
import model.world.Game;
import model.entities.Player;

import static model.entities.SpriteSet.load;

public class NormalEnemy extends Enemy {

    // -------------------------------------------------------------------------
    // Sprites
    //
    // NormalEnemy has no distinct LEFT or RIGHT sprites.
    // The DOWN frames are used for all horizontal movement — this fallback is
    // baked into the SpriteSet here rather than scattered across a draw() switch.
    // -------------------------------------------------------------------------

    private static final SpriteSet SPRITES;

    static {
        try {
            var up   = load(NormalEnemy.class, "/resources/Up_normal_enemy_1.png",   "/resources/Up_normal_enemy_2.png");
            var down = load(NormalEnemy.class, "/resources/Down_normal_enemy_1.png", "/resources/Down_normal_enemy_2.png");
            // LEFT and RIGHT both fall back to the DOWN frames.
            SPRITES = new SpriteSet(up, down, down, down);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    public NormalEnemy(Game game, Player player) {
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
        healthPoints = 3;
        speed        = 2;
    }
}