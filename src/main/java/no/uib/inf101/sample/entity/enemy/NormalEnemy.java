package no.uib.inf101.sample.entity.enemy;

import java.awt.Image;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.Inf101Graphics;
import no.uib.inf101.sample.entity.Player;

public class NormalEnemy extends Enemy {

    Game game;
    Player player;

    public NormalEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        this.movementDirection = "right";
        setDefaultValues();
    }

    public void setDefaultValues() {
        setXAndY();
        healthPoints = 3;
        speed = 2;
        isAlive = true;
    }
}