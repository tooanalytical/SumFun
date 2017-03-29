package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.*;

// actionPerformed method needs finished
public class TileListener implements ActionListener {

    private Tile[][] tiles;
    private Queue queue;
    private Score score;
    private MovesLeft movesLeft;

    public TileListener(Tile[][] tiles, Queue queue, Score score, MovesLeft movesLeft){
        this.tiles = tiles;
        this.queue = queue;
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
            // only update model(s) which changed; view should be updated automatically
        }
    }

}
