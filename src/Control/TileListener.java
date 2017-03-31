package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.*;

// actionPerformed & isHit methods need finished
// need method for determining neighbors
public class TileListener implements ActionListener {

    private Tile[][] tiles;
    private Queue queue;
    private Score score;
    private MovesLeft movesLeft;

    public TileListener(Tile[][] tiles, Queue queue, Score score, MovesLeft movesLeft){
        this.tiles = tiles;
        this.queue = queue;
        this.score = score;
        this.movesLeft = movesLeft;
    }

    public void actionPerformed(ActionEvent e){
        JButton btn = (JButton) e.getSource();
        Tile selectedTile= new Tile(true);
        //gets the row and column the button resides in
        int row = (int) btn.getClientProperty("row");
        int column = (int) btn.getClientProperty("col");

        // checks if the button is empty and if they still have moves left
        if(btn.getText().equals("") && movesLeft.getMovesLeft() > 0){
            // checks if move is hit
            // only update model(s) which changed; view should be updated automatically

            //get the value at the top of the queue
            int value=queue.getTop();
            int addPoints=0;

            //if the button is empty, we need to see if the tile is empty
            int willRemove;
            willRemove=isHit(selectedTile, row, column, value);
            //set the button to the queue value (will update the displayed value on board)
            //btn.setText(""+value);
            tiles[row][column].setValue(value);

            //if the placement will remove tiles, remove them
            if(willRemove>0){

                if(willRemove>=3){
                addPoints+=calculateScore(willRemove);
                score.updateScore(addPoints);
                }
                //set the tile to the queue value
                removeTiles(row, column);

            }

            queue.incrementQueue();
            movesLeft.updateMovesLeft();


        }
    }

    private int calculateScore(int willRemove) {
        int points=0;

        //since willRemove is only the count of the neighbors, this works
        points+=10 * (willRemove);

        return points;

    }
        //attempt 2 to commit
    private void removeTiles(int row, int column) {
        int prevRow=row-1, nextRow=row+1, prevColumn=column-1, nextColumn=column+1;

        if(nextRow<9 && (!tiles[nextRow][column].isEmpty())){
           tiles[nextRow][column].clear();
        }

        if (prevRow>0 && (!tiles[prevRow][column].isEmpty())){
            tiles[prevRow][column].clear();
        }

        if (nextColumn<9 && (!tiles[row][nextColumn].isEmpty())){
            tiles[row][nextColumn].clear();
        }

        if (prevColumn>0 && (!tiles[row][prevColumn].isEmpty())){
            tiles[row][prevColumn].clear();
        }

        if ((nextRow<9 && prevColumn>0) && (!tiles[nextRow][prevColumn].isEmpty())){
            tiles[nextRow][prevColumn].clear();
        }

        if ((prevRow>0 && nextColumn<9) && (!tiles[prevRow][nextColumn].isEmpty())){
            tiles[prevRow][nextColumn].clear();
        }

        if ((prevRow>0 && prevColumn>0) && (!tiles[prevRow][prevColumn].isEmpty())){
            tiles[prevRow][prevColumn].clear();
        }

        if ((nextRow<9 && nextColumn<9) && (!tiles[nextRow][nextColumn].isEmpty())){
            tiles[nextRow][nextColumn].clear();
        }
        tiles[row][column].clear();
    }


    // determines if move results in eliminated tiles/is a hit
    private int isHit(Tile selectedTile, int row, int column, int value){

        // determines if selectedTile is hit
        // updates flag

        int removeTiles=0;

        if (selectedTile.isEmpty()){
            //tile placement is valid

            //check how many neighbors it has

            //find the location of the selectedTile in the array


           /*
            arrayTile[i+1][j] exists (right neighbor)
            arrayTile[i-1][j] exists (left neighbor)
            arraytile[i][j+1] exists (bottom neighbor)
            arrayTile[i][j-1] exists (top neighbor)
            arrayTile[i-1][j-1] exists (top left neighbor)
            arrayTile[i+1][j-1] exists (top right neighbor)
            arrayTile[i-1][j+1] exists (bottom left neighbor)
            arrayTile[i+1][j+1] exists (bottom right neighbor)*/

           int sum=0, neighborCount=0;
            int prevRow=row-1, nextRow=row+1, prevColumn=column-1, nextColumn=column+1;

           if(nextRow<9 && (!tiles[nextRow][column].isEmpty())){
               sum+= tiles[nextRow][column].getValue();
               neighborCount++;
           }

           if (prevRow>0 && (!tiles[prevRow][column].isEmpty())){
               sum+= tiles[prevRow][column].getValue();
               neighborCount++;
           }

           if (nextColumn<9 && (!tiles[row][nextColumn].isEmpty())){
               sum+= tiles[row][nextColumn].getValue();
               neighborCount++;
           }

           if (prevColumn>0 && (!tiles[row][prevColumn].isEmpty())){
               sum+= tiles[row][prevColumn].getValue();
               neighborCount++;
           }

           if ((nextRow<9 && prevColumn>0) && (!tiles[nextRow][prevColumn].isEmpty())){
               sum+= tiles[nextRow][prevColumn].getValue();
               neighborCount++;
           }

           if ((prevRow>0 && nextColumn<9) && (!tiles[prevRow][nextColumn].isEmpty())){
               sum+= tiles[prevRow][nextColumn].getValue();
               neighborCount++;
           }

           if ((prevRow>0 && prevColumn>0) && (!tiles[prevRow][prevColumn].isEmpty())){
               sum+= tiles[prevRow][prevColumn].getValue();
               neighborCount++;
           }

           if ((nextRow<9 && nextColumn<9) && (!tiles[nextRow][nextColumn].isEmpty())){
               sum+= tiles[nextRow][nextColumn].getValue();
               neighborCount++;
           }

           int mod=sum%10;

           if (value==mod){
               removeTiles=neighborCount;
           }
        }


        return removeTiles;
    }



}
