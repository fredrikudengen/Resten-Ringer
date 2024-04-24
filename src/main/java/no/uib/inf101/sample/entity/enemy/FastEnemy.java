package no.uib.inf101.sample.entity.enemy;

import java.awt.Image;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.Inf101Graphics;
import no.uib.inf101.sample.entity.Player;

public class FastEnemy extends Enemy {

    Game game;
    Player player;

    private Image Leila1 = Inf101Graphics.loadImageFromResources("Leila_sprite_1.png");
    private Image Leila2 = Inf101Graphics.loadImageFromResources("Leila_sprite_2.png");

    public FastEnemy(Game game, Player player) {
        super(game, player);
        this.game = game;
        this.player = player;
        this.movementDirection = "right";

        setDefaultValues();
    }

    public void setDefaultValues() {
        setXAndY();
        healthPoints = 2;
        speed = 3;
        isAlive = true;
    }
}
