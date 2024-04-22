package no.uib.inf101.sample.entity;

import no.uib.inf101.sample.Game;

public class NormalEnemy extends Enemy {

    Game game;
    Player player;

    public NormalEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        setDefaultValues();
    }

    public void setDefaultValues() {
        setXAndY();
        healthPoints = 3;
        speed = 2;
        direction = "right";
        isAlive = true;
    }
}
