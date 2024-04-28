package no.uib.inf101.resten_ringer.entity.enemy;

import no.uib.inf101.resten_ringer.Game;
import no.uib.inf101.resten_ringer.Inf101Graphics;
import no.uib.inf101.resten_ringer.entity.Player;

public class SlowEnemy extends Enemy {

    public SlowEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        setDefaultValues();
        getImage();
    }
    
    @Override
    protected void setDefaultValues() {
        setSpawn();
        healthPoints = 4;
        speed = 1;
        isAlive = true;
        movementDirection = "right";

    }

    @Override
    protected void getImage() {
        slow_enemy_left_1 = Inf101Graphics.loadImageFromResources("Left_slow_enemy_1.png");
        slow_enemy_left_2 = Inf101Graphics.loadImageFromResources("Left_slow_enemy_2.png");
        slow_enemy_right_1 = Inf101Graphics.loadImageFromResources("Right_slow_enemy_1.png");
        slow_enemy_right_2 = Inf101Graphics.loadImageFromResources("Right_slow_enemy_2.png");
        slow_enemy_up_1 = Inf101Graphics.loadImageFromResources("Up_slow_enemy_1.png");
        slow_enemy_up_2 = Inf101Graphics.loadImageFromResources("Up_slow_enemy_2.png");
    }
}
