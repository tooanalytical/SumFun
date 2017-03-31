import Model.*;
import View.*;

public class Application {

    public static void main(String[] args){
//THis is a test from Nailey
        int numRows = 9;
        int numColumns = 9;

        Tile[][] tiles = new Tile[numRows][numColumns];
        Queue queue = new Queue();
        Score score = new Score();
        MovesLeft movesLeft = new MovesLeft(50);

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

        // creates new UntimedBoard
        new UntimedBoard(numRows, numColumns, tiles, queue, score, movesLeft);

    }

}
