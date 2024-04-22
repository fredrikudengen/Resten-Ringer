package no.uib.inf101.sample.entity;

import no.uib.inf101.sample.Game;

public class SlowEnemy extends Enemy {

    Game game;
    Player player;

    public SlowEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        setDefaultValues();
    }

    public void setDefaultValues() {
        setXAndY();
        healthPoints = 4;
        speed = 1;
        direction = "right";
        isAlive = true;
    }
}
