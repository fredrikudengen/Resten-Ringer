package no.uib.inf101.sample.entity.enemy;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.entity.Player;

public class NormalEnemy extends Enemy {

    public NormalEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        this.movementDirection = "right";
        setDefaultValues();
        getNormalEnemyImage();
    }

    @Override
    public void setDefaultValues() {
        setSpawn();
        healthPoints = 3;
        speed = 2;
        isAlive = true;
    }
}