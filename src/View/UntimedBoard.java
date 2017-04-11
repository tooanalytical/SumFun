package View;

import Model.*;

public class UntimedBoard extends Board {

    public UntimedBoard(int numRows, int numColumns, Game game){
        super(numRows, numColumns, game);
        updateDurationPanel();
    }

    public void updateDurationPanel(){
        lblDurationDesc.setText("MOVES LEFT: ");
        lblDuration.setText(Integer.toString(game.movesLeft.getMovesLeft()));
    }

}
