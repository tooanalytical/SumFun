package Model;

import java.io.Serializable;
import java.util.Observable;

//Test Bailey Comment

public class MovesLeft extends Observable implements Serializable {

    private int movesLeft;

    public MovesLeft(int movesLeft){
        if(movesLeft >= 0){
            this.movesLeft = movesLeft;
        }
        else{
            this.movesLeft = 50;
        }
    }

    public void updateMovesLeft(){
        movesLeft--;

        setChanged();
        notifyObservers();
    }

    public int getMovesLeft(){
        return movesLeft;
    }

}
