package View;

import Model.*;

import javax.swing.*;

// creates TimedBoard JPanel
public class TimedBoard extends Board {

    private GameTimer gameTimer = new GameTimer();

    public TimedBoard(Game game){
        super(game);
        game.movesLeft.notUntimedGame();
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
    }

    public void updateDurationPanel() {
        lblDurationDesc.setText("TIME LEFT: ");
        lblDuration.setText(gameTimer.getTimeRemaining());
        Timer timer = new Timer(1000, e ->   game.gameTimer.updateTimeLeft());
        timer.start();
    }

}
