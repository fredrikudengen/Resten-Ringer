package no.uib.inf101.sample.entity.enemy;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.entity.Player;

public class FastEnemy extends Enemy {

    public FastEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        this.movementDirection = "right";
        setDefaultValues();
        getFastEnemyImage();
    }

    protected void setDefaultValues() {
        setXAndY();
        healthPoints = 2;
        speed = 3;
        isAlive = true;
    }
}
