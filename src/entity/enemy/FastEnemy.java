package entity.enemy;

import java.io.IOException;
import javax.imageio.ImageIO;

import model.Game;
import entity.Player;

public class FastEnemy extends Enemy {

    public FastEnemy(Game game, Player player) throws IOException {
        super(game, player);
        this.game = game;
        this.player = player;
        setDefaultValues();
        getImage();
    }

    @Override
    protected void setDefaultValues() {
        setSpawn();
        healthPoints = 2;
        speed = 3;
        isAlive = true;
        movementDirection = "right";

    }

    @Override
    protected void getImage() throws IOException {
        fast_enemy_up_1 = ImageIO.read(getClass().getResourceAsStream("/resources/Up_police_1.png"));
        fast_enemy_up_2 = ImageIO.read(getClass().getResourceAsStream("/resources/Up_police_2.png"));
        fast_enemy_left_1 = ImageIO.read(getClass().getResourceAsStream("/resources/Left_police_1.png"));
        fast_enemy_left_2 = ImageIO.read(getClass().getResourceAsStream("/resources/Left_police_2.png"));
        fast_enemy_right_1 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_police_1.png"));
        fast_enemy_right_2 = ImageIO.read(getClass().getResourceAsStream("/resources/Right_police_2.png"));
    }
}