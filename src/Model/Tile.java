package Model;

import java.io.Serializable;
import java.util.Observable;

public class Tile extends Observable implements Serializable {

    private int value;
    private boolean isEmpty;
    private int[] coordinates = {0, 0};
    // index 0 contains row coord, 1 contains column

    // first constructor
    public Tile(boolean isEmpty){
        this.isEmpty = isEmpty;
        if(isEmpty){
            value = 0;
        } else {
            value = (int) (Math.random() * 10);
        }
    }

    // constructor which takes coordinates of tile
    public Tile(boolean isEmpty, int row, int column){
        this.isEmpty = isEmpty;
        coordinates[0] = row;
        coordinates[1] = column;
        if(isEmpty){
            value = 0;
        } else {
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
    public int getValue(){
        return value;
    }

    // mutator method for value
    public void setValue(int value){
        this.value = value;
        isEmpty = false;

        setChanged();
        notifyObservers();
    }

    // accessor method for coordinates
    public int[] getCoordinates(){
        return coordinates;
    }

}
