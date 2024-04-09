package no.uib.inf101.sample.entity;

public class Entity {

    public int x;
    public int y;
    public int speed;
    public Projectile projectile;
    public Player player;
    
    public void update() {
        player.update();
        projectile.update();
    }

}
