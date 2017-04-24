package View;

import Model.Game;
import Model.GameTimer;
import Model.HiScore;
import Model.Tile;

// creates TimedBoard JPanel
public class TimedBoard extends Board {

    private GameTimer gameTimer = new GameTimer();

    public TimedBoard(Game game, HiScore score){
        super(game, score);
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
        game.gameTimer.addObserver(this);
        game.addObserver(this);
    }

    public void updateDurationPanel() {
        lblDurationDesc.setText("TIME LEFT: ");
        lblDuration.setText(gameTimer.getTimeRemaining());
        game.gameTimer.startTimer();
    }

}
