package entity.enemy;


import model.Game;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;

public class SlowEnemy extends Enemy {

    public SlowEnemy(Game game, Player player) throws IOException {
        super(game, player);
        this.game = game;
        this.player = player;
        setDefaultValues();
        getImage();
    }
    
    @Override
    protected void setDefaultValues() {
        setSpawn();
        healthPoints = 4;
        speed = 1;
        isAlive = true;
        movementDirection = "right";

    }

    @Override
    protected void getImage() throws IOException {
        slow_enemy_left_1 = ImageIO.read(new File("resources/Left_slow_enemy_1.png"));
        slow_enemy_left_2 = ImageIO.read(new File("resources/Left_slow_enemy_2.png"));
        slow_enemy_right_1 = ImageIO.read(new File("resources/Right_slow_enemy_1.png"));
        slow_enemy_right_2 = ImageIO.read(new File("resources/Right_slow_enemy_2.png"));
        slow_enemy_up_1 = ImageIO.read(new File("resources/Up_slow_enemy_1.png"));
        slow_enemy_up_2 = ImageIO.read(new File("resources/Up_slow_enemy_2.png"));
    }
}
