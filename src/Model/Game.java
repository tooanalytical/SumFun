package Model;

public class Game {

    private final int NUM_MOVES_LEFT = 50;

    public Tile[][] tiles;
    public Queue queue;
    public Score score;
    public MovesLeft movesLeft;

    private int numRows;
    private int numColumns;

    // constructor
    public Game(int numRows, int numColumns){
        // instantiates numRows & numColumns
        this.numRows = numRows;
        this.numColumns = numColumns;

        // instantiates models
        tiles = new Tile[numRows][numColumns];
        queue = new Queue();
        score = new Score();
        movesLeft = new MovesLeft(NUM_MOVES_LEFT);

        // instantiates two-dimensional tiles array
        for(int r = 0; r < numRows; r++){
            for(int c = 0; c < numColumns; c++){
                if(r == 0 || c == 0 || r == numRows - 1 || c == numColumns - 1){
                    tiles[r][c] = new Tile(true, r, c);
                }
                else{
                    tiles[r][c] = new Tile(false, r, c);
                }
            }
        }
    }

    // accessor method for numRows
    public int getNumRows(){
        return numRows;
    }

    // accessor method for numColumns
    public int getNumColumns(){
        return numColumns;
    }
}
