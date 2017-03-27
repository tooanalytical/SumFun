package Model;

import java.io.Serializable;
import java.util.Observable;

public class Tile extends Observable implements Serializable {

    private int value;
    private boolean isEmpty;

    public Tile(boolean isEmpty){
        this.isEmpty = isEmpty;
        if(isEmpty){
            value = 0;
        }
        else{
            value = (int) (Math.random() * 10);
        }
    }

    // accessor method for isEmpty
    public boolean isEmpty(){
        return isEmpty;
    }

    // mutator method for isEmpty
    public void clear(){
        value = 0;
        isEmpty = true;

        setChanged();
        notifyObservers();
    }

    // accessor method for value
    public int value(){
        return value;
    }

    // mutator method for value
    public void fill(int value){
        this.value = value;
        isEmpty = false;

        setChanged();
        notifyObservers();
    }

}
