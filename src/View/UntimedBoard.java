package View;

import Model.*;

// creates UntimedBoard JPanel
public class UntimedBoard extends Board {

    public UntimedBoard(Game game, HiScore score){
        super(game, score);
        game.gameTimer.notTimedGame();
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
        game.movesLeft.addObserver(this);
    }

    public void updateDurationPanel(){
        lblDurationDesc.setText("MOVES LEFT: ");
        lblDuration.setText(Integer.toString(game.movesLeft.getMovesLeft()));
    }

}
