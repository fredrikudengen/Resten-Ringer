package no.uib.inf101.sample.entity.enemy;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.Inf101Graphics;
import no.uib.inf101.sample.entity.Player;

public class NormalEnemy extends Enemy {

    public NormalEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        setDefaultValues();
        getImage();
    }

    @Override
    protected void setDefaultValues() {
        setSpawn();
        healthPoints = 3;
        speed = 2;
        isAlive = true;
        movementDirection = "right";

    }

    @Override
    protected void getImage() {
        normal_enemy_up_1 = Inf101Graphics.loadImageFromResources("Up_normal_enemy_1.png");
        normal_enemy_up_2 = Inf101Graphics.loadImageFromResources("Up_normal_enemy_2.png");
        normal_enemy_down_1 = Inf101Graphics.loadImageFromResources("Down_normal_enemy_1.png");
        normal_enemy_down_2 = Inf101Graphics.loadImageFromResources("Down_normal_enemy_2.png");
    }
}