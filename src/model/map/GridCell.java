package model.map;

public class GridCell {

    private boolean collision = false;

    public boolean isCollision()            { return collision; }
    public void setCollision(boolean value) { this.collision = value; }
}