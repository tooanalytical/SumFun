package View;

import Model.*;

public class TimedBoard extends Board {

    public TimedBoard(int numRows, int numColumns, Game game){
        super(numRows, numColumns, game);
    }

    public void updateDurationPanel(){
        lblDurationDesc.setText("TIME LEFT: ");
        lblDuration.setText("use timer class method to get time left");
    }

}
