package Model;

import java.io.Serializable;
import java.util.Observable;

public class Score extends Observable implements Serializable {

    private int score;
    private int addition;

    // constructor
    public Score(){
        score = 0;
        addition = 0;
    }

    // mutator method for score
    public void updateScore(int addition){
        // addition is the points awarded for that tile placement
        this.addition = addition;
        score += addition;

        setChanged();
        notifyObservers();
    }

    // accessor method for score
    public int getScore(){
        return score;
    }
    public int getAddition(){ return addition; }
}
