// Made with help from https://www.youtube.com/watch?v=ugzxCcpoSdE (RyiSnow), edited to fit our game.
package model.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import model.world.Game;

public class GridFactory {

    /** Total number of distinct tile types (0 = open, 1 = solid). */
    private static final int TILE_TYPE_COUNT = 2;

    private final Game game;
    private final GridCell[] gridCell;
    private final int[][] gridCellNum;

    public GridFactory(Game game) {
        this.game        = game;
        this.gridCell    = new GridCell[TILE_TYPE_COUNT];
        this.gridCellNum = new int[game.getCols()][game.getRows()];
        setTiles();
        loadGrid("gameMap1.txt");
    }

    private void setTiles() {
        gridCell[0] = new GridCell();             // passable
        gridCell[1] = new GridCell();
        gridCell[1].setCollision(true);           // solid
    }

    private void loadGrid(String resourcePath) {
        try (InputStream is = getClass().getResourceAsStream(resourcePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            for (int row = 0; row < game.getRows(); row++) {
                String line = br.readLine();
                if (line == null) throw new IOException("Map file ended prematurely at row " + row);
                String[] numbers = line.split(" ");
                for (int col = 0; col < game.getCols(); col++) {
                    gridCellNum[col][row] = Integer.parseInt(numbers[col]);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load model.map '" + resourcePath + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Returns the tile-type index for each grid cell. */
    public int[][] getGridCellNum() {
        return gridCellNum;
    }

    public GridCell[] getGridCell() {
        return gridCell;
    }
}