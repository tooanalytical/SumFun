package sumfun;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Tile extends JButton implements Serializable {

    private int value;                                                          // must contain value, between 0 - 9
    private boolean isEmpty;                                                    // true if tile is empty

    public Tile(boolean isEmpty) {
        setFont(new Font("Arial", Font.PLAIN, 24));
        setContentAreaFilled(false);
        setOpaque(true);
        // sets action listener
        this.isEmpty = isEmpty;
        if(isEmpty){
            value = 0;
            setText("");
        }
        else{
            value = (int) (Math.random() * 10);
            setText(Integer.toString(value));
        }
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    public int value(){
        return value;
    }

    // fills tile w/ supplied value & sets empty status to false
    public void fill(int value){
        this.value = value;
        isEmpty = false;
        setText(Integer.toString(value));
    }

    // clears tile value & sets empty status to true
    public void clear(){
        value = 0;
        isEmpty = true;
        setText("");
    }

}
