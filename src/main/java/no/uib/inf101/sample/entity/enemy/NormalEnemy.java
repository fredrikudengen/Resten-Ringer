package no.uib.inf101.sample.entity.enemy;

import java.awt.Image;

import no.uib.inf101.sample.Game;
import no.uib.inf101.sample.Inf101Graphics;
import no.uib.inf101.sample.entity.Player;

public class NormalEnemy extends Enemy {

    Game game;
    Player player;

    private Image up1 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_1.png");
    private Image up2 = Inf101Graphics.loadImageFromResources("Up_shotgun_walking_2.png");
    private Image down1 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_1.png");
    private Image down2 = Inf101Graphics.loadImageFromResources("Down_shotgun_walking_2.png");
    private Image left1 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_1.png");
    private Image left2 = Inf101Graphics.loadImageFromResources("Left_shotgun_walking_2.png");
    private Image right1 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_1.png");
    private Image right2 = Inf101Graphics.loadImageFromResources("Right_shotgun_walking_2.png");

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