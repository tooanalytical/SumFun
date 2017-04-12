package View;

import Model.*;

// creates TimedBoard JPanel
public class TimedBoard extends Board {

    public TimedBoard(Game game){
        super(game);

        updateDurationPanel();
    }

    public void addObservers(){
        for(Tile[] row : game.tiles){
            for(Tile tile : row){
                tile.addObserver(this);
            }
        }
        game.queue.addObserver(this);
        game.score.addObserver(this);
        //game.timer.addObserver(this);
    }

    public void updateDurationPanel(){
        lblDurationDesc.setText("TIME LEFT: ");
        lblDuration.setText("5:00");
    }

}
