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

        // updates model
        game.updateTiles(row, column);

        // updates view -- all JButtons must have background color reset
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                tileButtons[r][c].setBackground(null);
            }
        }

        /*
        JButton btn = (JButton) e.getSource();
        //gets the row and column the button resides in
        int row = (int) btn.getClientProperty("row");
        int column = (int) btn.getClientProperty("col");

        // checks if the button is empty and if they still have moves left
        if(btn.getText().equals("") && ((game.movesLeft.getMovesLeft() > 0) || !game.gameTimer.getTimeRemaining().equals("0:00"))){

            // checks if move is hit
            // only update model(s) which are changed; view should be updated automatically

            //get the value at the top of the queue
            int value = game.queue.getTop();
            int addPoints = 0;
            int sum = 0;
            int nonEmptyNeighbors = 0;

            //if the button is empty, we need to see if the tile is empty
            int addToSum = 0;
            for (int i = row - 1; i <= row + 1; i++){
                for (int j = column - 1;j <= column + 1; j++){
                    if(i >= 0 && j >= 0){
                        if(i < 9 && j < 9){
                            System.out.println("Value at: i= " + i + " j=" + j);
                            //if(!game.tiles[i][j].isEmpty()){
                                //we cannot count the tile placed
                                if(i != row || j != column) {
                                    //add the value of the tile (even if it's zero) to the sum
                                    System.out.print("value: " + game.tiles[i][j].getValue());
                                    addToSum += game.tiles[i][j].getValue();
                                    if(!game.tiles[i][j].isEmpty()){
                                        nonEmptyNeighbors++;
                                    }
                                }
                            //}
                        }
                    }
                }

            }
            sum = addToSum;
            System.out.println("sum: " + sum);
            //set the tile to the queue value (will update the displayed value on board)
            game.tiles[row][column].setValue(value);
            int yayWeGotOne = game.tiles[row][column].compare(sum);

            //if the placement will remove tiles, remove them
            if(yayWeGotOne == 1 && nonEmptyNeighbors > 0){
                System.out.println("Number of Neighbors contributing to sum: " + nonEmptyNeighbors);
                if(nonEmptyNeighbors >= 3){

                    //if more than 3 tiles are involved, add points to score
                    addPoints += calculateScore(nonEmptyNeighbors);
                    game.score.updateScore(addPoints);
                }
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = column - 1; j <= column + 1; j++) {
                        if (i >= 0 && j >= 0) {
                            if (i < 9 && j < 9) {
                                if (!game.tiles[i][j].isEmpty()) {
                                    game.tiles[i][j].clear();
                                }
                            }
                        }
                    }

                }


            }

            game.queue.incrementQueue();
            game.movesLeft.updateMovesLeft();


        }*/
    }

    private int calculateScore(int willRemove) {
        int points = 0;

        //willRemove is greater than 2 and does not include placed tile
        points += 10 * (willRemove);

        return points;

    }





}
