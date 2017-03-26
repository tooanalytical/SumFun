package sumfun;

// needs work
public class Game {

    private final int numRows;
    private final int numColumns;

    private Queue queue;
    protected Tile[][] tiles;

    public Game(int numRows, int numColumns){
        this.numRows = numRows;
        this.numColumns = numColumns;

        // instantiates two-dimensional Tile array
        tiles = new Tile[numRows][numColumns];
        for(int r = 0; r < numRows; r++){
            for(int c = 0; c < numColumns; c++){
                if(r != 0 && c != 0 && r != numRows - 1 && c != numColumns){
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
