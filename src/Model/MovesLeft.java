package Model;

import java.io.Serializable;
import java.util.Observable;

public class MovesLeft extends Observable implements Serializable {

    private static int movesLeft;

    //determines # of moves left
    public MovesLeft(int movesLeft){
        if(movesLeft >= 0){
            this.movesLeft = movesLeft;
        }
        else{
            this.movesLeft = 50;
        }
    }

    // mutator method for movesLeft
    public void updateMovesLeft(){
        movesLeft--;

        setChanged();
        notifyObservers();
    }

    // accessor method for movesLeft
    public static int getMovesLeft(){
        return movesLeft;
    }

}
