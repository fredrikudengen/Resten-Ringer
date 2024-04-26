package no.uib.inf101.sample.map;

import no.uib.inf101.sample.Game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GridFactory {
    private Game game;
    private GridCell[] gridCell;

    // Stores map data
    private int gridCellNum[][];

    public GridFactory(Game game) {

        this.game = game;

        gridCell = new GridCell[2];

        gridCellNum = new int[game.getCols()][game.getRows()];

        setTiles();
        loadGrid("gameMap1.txt");
    }

    private void setTiles() {
        gridCell[0] = new GridCell();
        gridCell[1] = new GridCell();
        gridCell[1].collision = true;
    }

    private void loadGrid(String gridFile) {
        try {
            // Imports textfile
            InputStream is = getClass().getResourceAsStream(gridFile);
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
                    gridCellNum[col][row] = num;
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

    public int[][] getGridCellNUm() {
        return gridCellNum;
    }
    public GridCell[] getGridCell() {
        return gridCell;
    }
}