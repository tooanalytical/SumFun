package Control;

import Model.Queue;
import Model.Tile;

import java.io.Serializable;

// changeTile method needs work
// additional methods will most likely be necessary
public class Game implements Serializable {

    private final int numRows;
    private final int numColumns;

    public Queue queue;
    public Tile[][] tiles;

    public Game(int numRows, int numColumns){
        this.numRows = numRows;
        this.numColumns = numColumns;

        // instantiates queue
        queue = new Queue();

        // instantiates two-dimensional Tile array
        tiles = new Tile[numRows][numColumns];
        for(int r = 0; r < numRows; r++){
            for(int c = 0; c < numColumns; c++){
                if(r != 0 && c != 0 && r != numRows - 1 && c != numColumns - 1){
                    tiles[r][c] = new Tile(false);
                }
                else{
                    tiles[r][c] = new Tile(true);
                }
            }
        }
    }

    public void changeTile(int rowIndex, int colIndex, int value, boolean useEmptyTile){
        if(useEmptyTile){
            tiles[rowIndex][colIndex].clear();
        }
        else{
            // this should be changed; the value should be gotten some other way
            tiles[rowIndex][colIndex].fill(value);
        }
    }

}
