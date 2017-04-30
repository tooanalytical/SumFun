package Control;

import Model.Game;
import View.Application;
import View.Board;
import View.HighScoresView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.*;

// actionPerformed & isHit methods need finished
// need method for determining neighbors
public class TileController implements ActionListener {

    private Game game;
    private JButton[][] tileButtons;
    private Board board;

    public TileController(Game game, JButton[][] tileButtons, Board board){
        this.game = game;
        this.tileButtons = tileButtons;
        this.board = board;
    }

    public void actionPerformed(ActionEvent e){
        JButton btn = (JButton) e.getSource();
        Application app = (Application) btn.getRootPane().getParent();
        int row = (int) btn.getClientProperty("row");
        int column = (int) btn.getClientProperty("col");

        boolean magicTrickStarted=false;
        if(game.isMagicTrick==1){
            magicTrickStarted=true;
        }

        //Did user click the magic trick?
        if(magicTrickStarted) {

            //determine what tiles should be enabled for clicking when the magic trick is triggered
            //disable all buttons that are empty when the magic trick is clicked
            //re-enable the buttons after the magic trick has been completed.

            //yes they did, now make them work

            game.checkForMagicTrick(row, column);
            for(int i=0;i<9;i++){
                for(int j=0; j<9;j++){
                    tileButtons[i][j].addNotify();
                }
            }

        } else {
            // updates model & checks if game is over
            if(game.updateTiles(row, column)) {
                if (game.gameOver) {
                    JOptionPane.showMessageDialog(null, "You lose! Better luck next time!", "Sum Fun Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (game.isUntimed) {
                        new HighScoresView(game.highScores, Integer.toString(game.score.getScore()), "", LocalDateTime.now());
                    } else {
                        game.gameTimer.stopTimer();
                        new HighScoresView(game.highScores, Integer.toString(game.score.getScore()), game.gameTimer.getTimeElapsed(), LocalDateTime.now());
                    }
                }
                board.btnNewGame.doClick();
            }
        }

    }

}
