package Model;

import java.io.Serializable;
import java.util.Observable;

public class Queue extends Observable implements Serializable {

    private int[] queue;
    private int position;                                           // represents top of queue

    //constructor
    public Queue(){
        queue = new int[5];
        position = 0;

        newQueue();
    }

    // updates existing queue w/ new values & returns copy of updated queue
    public int[] newQueue(){
        int[] copy = new int[5];
        position = 0;

        //queue must always have five integers
        for(int i = 0; i < 5; i++){
            queue[i] = (int) (Math.random() * 10);
            copy[i] = queue[i];
        }

        setChanged();
        notifyObservers();

        return copy;
    }

    // returns a copy of the existing queue
    public int[] getQueue(){
        int[] copy = new int[5];
        int temp = position;
        int count = 0;

        // gets first part of queue, starting at position
        while(temp < 5){
            copy[count] = queue[temp];
            temp++;
            count++;
        }

        // gets remainder of queue, starting at index 0
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

