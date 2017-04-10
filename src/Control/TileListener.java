package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.*;

// actionPerformed & isHit methods need finished
// need method for determining neighbors
public class TileListener implements ActionListener {

    private Game game;

    public TileListener(Game game){
        this.game = game;
    }

    public void actionPerformed(ActionEvent e){
        JButton btn = (JButton) e.getSource();
        //gets the row and column the button resides in
        int row = (int) btn.getClientProperty("row");
        int column = (int) btn.getClientProperty("col");

        // checks if the button is empty and if they still have moves left
        if(btn.getText().equals("") && game.movesLeft.getMovesLeft() > 0){

            // checks if move is hit
            // only update model(s) which are changed; view should be updated automatically

            //get the value at the top of the queue
            int value=game.queue.getTop();
            int addPoints=0;

            //if the button is empty, we need to see if the tile is empty
            int willRemove;
            willRemove=isHit(row, column, value);

            //set the tile to the queue value (will update the displayed value on board)
            game.tiles[row][column].setValue(value);

            //if the placement will remove tiles, remove them
            if(willRemove>0){

                if(willRemove>=3){

                    //if more than 3 tiles are involved, add points to score
                    addPoints+=calculateScore(willRemove);
                    game.score.updateScore(addPoints);
                }
                //set the tile to the queue value
                removeTiles(row, column);

            }

            game.queue.incrementQueue();
            game.movesLeft.updateMovesLeft();


        }
    }

    private int calculateScore(int willRemove) {
        int points=0;

        //willRemove is greater than 2 and does not include placed tile
        points+=10 * (willRemove);

        return points;

    }

    //clear neighbors tiles if placed tile will remove tile
    private void removeTiles(int row, int column) {
        int prevRow=row-1, nextRow=row+1, prevColumn=column-1, nextColumn=column+1;

        /*
            top left corner: check right, bottom right, bottom neighbors
            top right corner: check left bottom left, bottom neighbors
            bottom right corner: check left, top left, top neighbors
            bottom left corner: check top, top right, right neighbors
         */

        //bottom neighbor
        if(nextRow<9 && (!game.tiles[nextRow][column].isEmpty())){
            game.tiles[nextRow][column].clear();
        }

        //top neighbor
        if (prevRow>=0 && (!game.tiles[prevRow][column].isEmpty())){
            game.tiles[prevRow][column].clear();
        }

        //right neighbor
        if (nextColumn<9 && (!game.tiles[row][nextColumn].isEmpty())){
            game.tiles[row][nextColumn].clear();
        }

        //left neighbor
        if (prevColumn>=0 && (!game.tiles[row][prevColumn].isEmpty())){
            game.tiles[row][prevColumn].clear();
        }


        //bottom left neighbor
        if ((nextRow<9 && prevColumn>=0) && (!game.tiles[nextRow][prevColumn].isEmpty())){
            game.tiles[nextRow][prevColumn].clear();
        }

        //top right neighbor
        if ((prevRow>=0 && nextColumn<9) && (!game.tiles[prevRow][nextColumn].isEmpty())){
            game.tiles[prevRow][nextColumn].clear();
        }

        //top left neighbor
        if ((prevRow>=0 && prevColumn>=0) && (!game.tiles[prevRow][prevColumn].isEmpty())){
            game.tiles[prevRow][prevColumn].clear();
        }

        //bottom right neighbor
        if ((nextRow<9 && nextColumn<9) && (!game.tiles[nextRow][nextColumn].isEmpty())){
            game.tiles[nextRow][nextColumn].clear();
        }
        game.tiles[row][column].clear();
    }


    // determines if move results in eliminated tiles/is a hit
    private int isHit(int row, int column, int value){

        // determines if selectedTile is hit
        // updates flag

        int removeTiles=0;

        //check how many neighbors it has

        //find the location of the selectedTile in the array


       /*
            top left corner: check right, bottom right, bottom neighbors
            top right corner: check left bottom left, bottom neighbors
            bottom right corner: check left, top left, top neighbors
            bottom left corner: check top, top right, right neighbors
         */

        int sum=0, neighborCount=0;
        int prevRow=row-1, nextRow=row+1, prevColumn=column-1, nextColumn=column+1;

        //bottom neighbor
        if(nextRow<9 && (!game.tiles[nextRow][column].isEmpty())){
            sum+= game.tiles[nextRow][column].getValue();
            neighborCount++;
        }

        //top neighbor
        if (prevRow>=0 && (!game.tiles[prevRow][column].isEmpty())){
            sum+= game.tiles[prevRow][column].getValue();
            neighborCount++;
        }

        //right neighbor
        if (nextColumn<9 && (!game.tiles[row][nextColumn].isEmpty())){
            sum+= game.tiles[row][nextColumn].getValue();
            neighborCount++;
        }

        //left neighbor
        if (prevColumn>=0 && (!game.tiles[row][prevColumn].isEmpty())){
            sum+= game.tiles[row][prevColumn].getValue();
            neighborCount++;
        }

        //bottom left neighbor
        if ((nextRow<9 && prevColumn>=0) && (!game.tiles[nextRow][prevColumn].isEmpty())){
            sum+= game.tiles[nextRow][prevColumn].getValue();
            neighborCount++;
        }

        //top right neighbor
        if ((prevRow>=0 && nextColumn<9) && (!game.tiles[prevRow][nextColumn].isEmpty())){
            sum+= game.tiles[prevRow][nextColumn].getValue();
            neighborCount++;
        }

        //top left neighbor
        if ((prevRow>=0 && prevColumn>=0) && (!game.tiles[prevRow][prevColumn].isEmpty())){
            sum+= game.tiles[prevRow][prevColumn].getValue();
            neighborCount++;
        }

        //bottom right neighbor
        if ((nextRow<9 && nextColumn<9) && (!game.tiles[nextRow][nextColumn].isEmpty())){
            sum+= game.tiles[nextRow][nextColumn].getValue();
            neighborCount++;
        }

        //perform mod calculation
        int mod=sum%10;

        //if the mod value equals the value of the placed tile, remove tiles
        if (value==mod){
            removeTiles=neighborCount;
        }


        return removeTiles;
    }
}
