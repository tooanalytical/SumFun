package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.*;

public class TileListener implements ActionListener {

    Score score;
    MovesLeft movesLeft;

    public TileListener(Score score, MovesLeft movesLeft){
        this.score = score;
        this.movesLeft = movesLeft;
    }

    public void actionPerformed(ActionEvent e){
        JButton btn = (JButton) e.getSource();
        int row = (int) btn.getClientProperty("row");
        int column = (int) btn.getClientProperty("col");

        // checks if selected tile is empty & there are moves left
        if(btn.getText().equals("") && movesLeft.getMovesLeft() > 0){
            // checks if move is hit
            // updates selected JButton and accompanying tile
        }
    }

}
