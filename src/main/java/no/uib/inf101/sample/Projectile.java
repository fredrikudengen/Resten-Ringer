package no.uib.inf101.sample;

public class Projectile {

    GameController gameController;
    Game game;

    int projectileSpeed;
    int projectileX;
    int projectileY;

    boolean north, northEast, northWest, southEast, south, southWest, west, east;

    public Projectile() {
        // Create projectile moving towards direction north/east/etc. 
        // northEast = projectileX += projectileSpeed and projectileY -= projectileSpeed

    }

    public void handleMouseClick(int x, int y) {
        // Handle mouse click logic here
        // For example, you can determine which tile was clicked and perform actions accordingly
        int clickedTileX = x / game.tileSize;
        int clickedTileY = y / game.tileSize;
        
        System.out.println("Clicked on tile at: (" + clickedTileX + ", " + clickedTileY + ")");


    }


}


