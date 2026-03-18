package model;

import java.awt.Graphics2D;

public interface DrawAndUpdate {

    /**
     * Updates object every frame. Called by UpdateGame.
     */
    void update();

    /**
     * Draws object every frame. Called by DrawGame.
     *
     * @param g2 Graphics2D context
     */
    void draw(Graphics2D g2);
}