package Model;

import java.io.Serializable;
import java.util.Observable;

public class Score extends Observable implements Serializable {

    private int score;

    public Score(){
        score = 0;
    }

    // mutator method for score
    public void updateScore(int addition){
        score += addition;

        setChanged();
        notifyObservers();
    }

    // accessor method for score
    public int getScore(){
        return score;
    }
}
