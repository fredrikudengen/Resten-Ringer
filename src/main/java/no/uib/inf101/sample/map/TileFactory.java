package no.uib.inf101.sample.map;

import java.awt.Graphics2D;
import java.awt.Toolkit;

import no.uib.inf101.sample.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileFactory {
    Game game;
    Tile[] tile;

    // Stores map data
    int mapTileNum[][];
    
    public TileFactory(Game game) {

        this.game = game;

        tile = new Tile[10];

        mapTileNum = new int[game.cols][game.rows];

        getTileImage();
        loadMap("gameMap1.txt");

    }
    public void getTileImage() {
        tile[0] = new Tile();
        tile[0].image = Toolkit.getDefaultToolkit()
        .getImage(getClass().getResource("grass.png"));

        tile[1] = new Tile();
        tile[1].image = Toolkit.getDefaultToolkit()
        .getImage(getClass().getResource("wall.png"));

        tile[2] = new Tile();
        tile[2].image = Toolkit.getDefaultToolkit()
        .getImage(getClass().getResource("water.png"));
}

    public void loadMap(String mapFile) {
        try{
            // Imports textfile
            InputStream is = getClass().getResourceAsStream(mapFile);
            // Reads textfile
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            // Map text file is the same size as our board
            while (col < game.cols && row < game.rows) {
            // Reads a single line in the map file
            String line = br.readLine();
                // Puts read string in an array
                while (col < game.cols && row < game.rows) {
                    // Changes string in line to integer
                    String numbers[] = line.split(" ");
                    // Stores extracted number
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                    
                } if (col == game.cols) {
                    col = 0;
                    row++;
                }
                } br.close();


        } catch(Exception e) {
        }
    }

    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < game.cols && row < game.rows) {

            int tileNum = mapTileNum[col][row]; // Extracts number for a tile // Works as an index
            g2.drawImage(tile[tileNum].image, x, y, game.tileSize, game.tileSize, null);
            //Draws next tile
            col++;
            x += game.tileSize;

            // Resets cols and x pos when max is hit
            if (col == game.cols) {
                col = 0;
                x = 0;
                // Increases rows and y pos
                row++;
                y += game.tileSize;
            }
            
        }
    }
}
