package no.uib.inf101.sample.map;

import no.uib.inf101.sample.Game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileFactory {
    Game game;
    private Tile[] tile;

    // Stores map data
    private int mapTileNum[][];

    public TileFactory(Game game) {

        this.game = game;

        tile = new Tile[2];

        mapTileNum = new int[game.getCols()][game.getRows()];

        setTiles();
        loadMap("gameMap1.txt");

    }

    private void setTiles() {
        tile[0] = new Tile();
        tile[1] = new Tile();
        tile[1].collision = true;
    }

    private void loadMap(String mapFile) {
        try {
            // Imports textfile
            InputStream is = getClass().getResourceAsStream(mapFile);
            // Reads textfile
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            // Map text file is the same size as our board
            while (col < game.getCols() && row < game.getRows()) {
                // Reads a single line in the map file
                String line = br.readLine();
                // Puts read string in an array
                while (col < game.getCols() && row < game.getRows()) {
                    // Changes string in line to integer
                    String numbers[] = line.split(" ");
                    // Stores extracted number
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;

                }
                if (col == game.getCols()) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
        }
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }
    public Tile[] getTile() {
        return tile;
    }
}
