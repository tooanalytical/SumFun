package Model;

import java.io.Serializable;
import java.util.Observable;

public class Queue extends Observable implements Serializable {

    private static int[] queue;
    private static int position;

    public Queue(){
        queue = new int[5];
        position = 0;

        newQueue();
    }

    // updates existing queue w/ new values & returns copy of updated queue
    public int[] newQueue(){
        int[] copy = new int[5];
        position = 0;

        for(int i = 0; i < 5; i++){
            queue[i] = (int) (Math.random() * 10);
            copy[i] = queue[i];
        }

        setChanged();
        notifyObservers();

        return copy;
    }

    // returns a copy of the existing queue
    public static int[] getQueue(){
        int[] copy = new int[5];
        int temp = position;
        int count = 0;

        while(temp < 5){
            copy[count] = queue[temp];
            temp++;
            count++;
        }

        temp = 0;
        while(count < 5){
            copy[count] = queue[temp];
            temp++;
            count++;
        }

        return copy;
    }

    // adds new value to queue, updates position
    public void incrementQueue(){
        queue[position] = (int) (Math.random() * 10);

        if(position >= 4){
            position = 0;
        }
        else{
            position++;
        }

        setChanged();
        notifyObservers();

    }

    public int getTop(){

        return queue[position];

    }


}

