package no.uib.inf101.resten_ringer.entity.enemy;

import no.uib.inf101.resten_ringer.Game;
import no.uib.inf101.resten_ringer.Inf101Graphics;
import no.uib.inf101.resten_ringer.entity.Player;

public class FastEnemy extends Enemy {

    public FastEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        setDefaultValues();
        getImage();
    }

    @Override
    protected void setDefaultValues() {
        setSpawn();
        healthPoints = 2;
        speed = 3;
        isAlive = true;
        movementDirection = "right";

    }

    @Override
    protected void getImage() {
        fast_enemy_up_1 = Inf101Graphics.loadImageFromResources("Up_police_1.png");
        fast_enemy_up_2 = Inf101Graphics.loadImageFromResources("Up_police_2.png");
        fast_enemy_left_1 = Inf101Graphics.loadImageFromResources("Left_police_1.png");
        fast_enemy_left_2 = Inf101Graphics.loadImageFromResources("Left_police_2.png");
        fast_enemy_right_1 = Inf101Graphics.loadImageFromResources("Right_police_1.png");
        fast_enemy_right_2 = Inf101Graphics.loadImageFromResources("Right_police_2.png");
    }
}
