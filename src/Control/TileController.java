package Control;

import Model.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

// actionPerformed & isHit methods need finished
// need method for determining neighbors
public class TileController implements ActionListener {

    private Game game;
    private JButton[][] tileButtons;

    public TileController(Game game, JButton[][] tileButtons){
        this.game = game;
        this.tileButtons = tileButtons;
    }

    public void actionPerformed(ActionEvent e){
        JButton btn = (JButton) e.getSource();
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

                magicTrickStarted=game.checkForMagicTrick(row, column);
                for(int i=0;i<9;i++){
                    for(int j=0; j<9;j++){
                        tileButtons[i][j].addNotify();
                    }
                }

        }else{
            // updates model
            game.updateTiles(row, column);

        }


    }

    private int calculateScore(int willRemove) {
        int points = 0;

        //willRemove is greater than 2 and does not include placed tile
        points += 10 * (willRemove);

        return points;

    }





}
