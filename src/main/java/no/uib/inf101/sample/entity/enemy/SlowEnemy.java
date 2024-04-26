package no.uib.inf101.sample.entity.enemy;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.entity.Player;

public class SlowEnemy extends Enemy {

    public SlowEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        this.movementDirection = "right";
        setDefaultValues();
        getSlowEnemyImage();
    }

    protected void setDefaultValues() {
        setXAndY();
        healthPoints = 4;
        speed = 1;
        isAlive = true;
    }
}
