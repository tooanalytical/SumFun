package Control;

import Model.Game;
import View.Application;
import View.Board;
import View.HighScoresView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JOptionPane;

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

            //determine what tiles should be enabled for clicking
            // disable all buttons that are empty when clicked
            //re-enable the buttons after the magic trick has been completed.

            //yes they did, now make them work

                magicTrickStarted=game.checkForMagicTrick(row, column);
               if(magicTrickStarted==false){
                for(int i=0;i<9;i++){
                    for(int j=0; j<9;j++){
                        tileButtons[i][j].setEnabled(true);
                    }
                }
               }

        } else {
            // updates model & checks if game is over
            if(game.updateTiles(row, column)) {
                if (game.gameOver) {
                    game.playLosingSound();
                    JOptionPane.showMessageDialog(null,
                            "You lose! Better luck next time!",
                            "Sum Fun Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (game.isUntimed) {
                        game.playWinningSound();
                        new HighScoresView(game.highScores,
                                Integer.toString(game.score.getScore()),
                                "", LocalDateTime.now());
                    } else {
                        game.gameTimer.stopTimer();
                        game.playWinningSound();
                        new HighScoresView(game.highScores,
                                Integer.toString(game.score.getScore()),
                                game.gameTimer.getTimeElapsed(),
                                LocalDateTime.now());
                    }
                }
                board.btnNewGame.doClick();
            }

            // disables hint button if no hints
            game.getHints(true);
        }

    }

}
