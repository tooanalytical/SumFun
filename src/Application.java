import Model.*;
import View.*;

public class Application {

    public static void main(String[] args){

        int numRows = 9;
        int numColumns = 9;

        // instantiates game model
        Game game = new Game(numRows, numColumns);

        // creates new UntimedBoard
        new UntimedBoard(numRows, numColumns, game);

    }

}
