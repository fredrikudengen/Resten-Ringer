package entity.enemy;

import model.Game;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;

public class NormalEnemy extends Enemy {

    public NormalEnemy(Game game, Player player) throws IOException {
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
    protected void getImage() throws IOException {
        normal_enemy_up_1 = ImageIO.read(new File("resources/Up_normal_enemy_1.png"));
        normal_enemy_up_2 = ImageIO.read(new File("resources/Up_normal_enemy_2.png"));
        normal_enemy_down_1 = ImageIO.read(new File("resources/Down_normal_enemy_1.png"));
        normal_enemy_down_2 = ImageIO.read(new File("resources/Down_normal_enemy_2.png"));
    }
}