package entity;

import java.awt.Graphics2D;

public interface DrawAndUpdate {

    /**
     * Updates object every frame. This method is called in UpdateGame.
     */
    public void update();

    /**
     * Draws object every frame. This method is called in DrawGame.
     * 
     * @param g2
     */
    public void draw(Graphics2D g2);
}
