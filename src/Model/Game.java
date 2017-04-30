package Model;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

// constructor should check which type of game is being created: timed or untimed
// only movesLeft OR gameTimer should be instantiated depending on game type
public class Game extends Observable implements Serializable {

    public final static int NUM_ROWS = 9;
    public final static int NUM_COLUMNS = 9;
    private final int NUM_MOVES_LEFT = 50;
    public final static int UNTIMED = 1;
    public final static int TIMED = 2;

    private int numEmptyTiles;

    public Tile[][] tiles;
    public Queue queue;
    public Score score;
    public MovesLeft movesLeft;
    public GameTimer gameTimer;
    public HighScoresModel highScores;

    public boolean gameOver = false;
    public boolean isUntimed = true;
    public int isMagicTrick=0;

    // constructor
    public Game(int gameType){
        numEmptyTiles = 32;

        // instantiates models
        tiles = new Tile[NUM_ROWS][NUM_COLUMNS];
        queue = new Queue();
        score = new Score();
        if(gameType == UNTIMED){
            movesLeft = new MovesLeft(NUM_MOVES_LEFT);
        } else if(gameType == TIMED){
            gameTimer = new GameTimer();
            isUntimed = false;
        }
        highScores = new HighScoresModel();

        // instantiates two-dimensional tiles array
        for(int r = 0; r < NUM_ROWS; r++){
            for(int c = 0; c < NUM_COLUMNS; c++){
                if(r == 0 || c == 0 || r == NUM_ROWS - 1 || c == NUM_COLUMNS - 1){
                    tiles[r][c] = new Tile(true, r, c);
                } else {
                    tiles[r][c] = new Tile(false, r, c);
                }
            }
        }
    }

    // private helper method, used in updateTiles method
    // checks if sum of tile neighbors modulus 10 is equal to top of queue
    // if placement is a hit, the number of tiles removed is returned
    // if not a hit, -1 is returned
    private int isHit(int r, int c){
        boolean isHit = false;
        int sum = 0;
        int counter = 0;
        ArrayList<int[]> validNeighbors = getNeighbors(r, c);

        // loops through valid neighbors, checking if empty
        // if not empty, value of neighbor tile is added to sum
        for(int[] valid : validNeighbors){
            int row = valid[0];
            int col = valid[1];
            if(!tiles[row][col].isEmpty()){
                // adjusts sum & counter for each valid neighbor
                sum += tiles[row][col].getValue();
                counter++;
            }
        }

        // compares sum modulus 10 to top of queue; updates isHit boolean
        if(sum % 10 == queue.getTop()){
            isHit = true;
        }

        // returns number of neighbors removed (counter) if isHit == true, else returns -1
        if(isHit){
            return counter;
        } else {
            return -1;
        }

    }

    // helper method which returns arraylist of int arrays which contains coords of all valid neighbors
    private ArrayList<int[]> getNeighbors(int r, int c){
        int[][] neighbors = {{r - 1, c - 1}, {r - 1, c}, {r - 1, c + 1}, {r, c - 1}, {r, c + 1}, {r + 1, c - 1},
                {r + 1, c}, {r + 1, c + 1}};
        ArrayList<int[]> validNeighbors = new ArrayList<>();

        // loops through neighbors, checking if valid or not
        for(int i = 0; i < 8; i++){
            // checks if row & column of neighbor is valid
            int nr = neighbors[i][0];
            int nc = neighbors[i][1];
            if ((nr >= 0 && nr <= 8) && (nc >= 0 && nc <= 8)) {
                // adds valid neighbor coords to validNeighbors arraylist
                int[] valid = {nr, nc};
                validNeighbors.add(valid);
            }
        }

        return validNeighbors;
    }

    // updates tiles 2-d array depending on hit status
    // if hitStatus == -1, hit is false; else hitStatus == number of tiles removed, and hit is true
    // returns true if game is over, false if not
    public boolean updateTiles(int r, int c){
        // checks if able to update tiles; if not, returns
        if(!tiles[r][c].isEmpty()){
            return false;
        } else {
            if(isUntimed){
                if(movesLeft.getMovesLeft() <= 0){
                    return false;
                }
            } else {
                if(gameTimer.getTimeRemaining() == "0:00"){
                    return false;
                }
            }
        }

        int hitStatus = isHit(r, c);
        if(hitStatus == -1){
            // tile at position r,c updated w/ value from top of queue
            tiles[r][c].setValue(queue.getTop());
            numEmptyTiles--;
        } else {
            // gets valid neighbors
            tiles[r][c].setValue(queue.getTop());
            ArrayList<int[]> validNeighbors = getNeighbors(r, c);
            Timer timer = new Timer(200, null);
            timer.addActionListener(actionEvent -> {
                tiles[r][c].clear();
                for (int[] valid : validNeighbors) {
                    int row = valid[0];
                    int col = valid[1];

                    // clears neighbors & updates model
                    tiles[row][col].clear();
                }
                timer.stop();
            });
            timer.start();
            numEmptyTiles += hitStatus;
        }

        // updates moves left
        if(isUntimed){
            movesLeft.updateMovesLeft();
        }

        // updates score
        if(hitStatus >= 3){
            score.updateScore(hitStatus * 10);
            playSound();
        }else{
            score.updateScore(0);
        }

        // updates queue
        queue.incrementQueue();

        // checks if game is won or lost
        if(numEmptyTiles == 0){
            // game is lost
            gameOver = true;
            return true;
        } else if(numEmptyTiles == 81){
            // game is won
            return true;
        }

        return false;

    }

    // sets hints field
    // the coords returned only represent hints which return max points (and must have three or more tiles removed)
    /*
    public void updateHints(){
        // contains coords with hits that give most points
        ArrayList<int[]> hints = new ArrayList<>();

        // current most num of neighbors
        int count = 3;
        //int count = 0;
        // loops through each empty tile and adds coords of max hints to arraylist
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                if(tiles[r][c].isEmpty()){
                    int isHit = isHit(r, c);
                    int[] coords = {r, c};
                    if(count == isHit){
                        hints.add(coords);
                    } else if(count < isHit) {
                        hints.clear();
                        count = isHit;
                        hints.add(coords);
                    }
                }
            }
        }

        this.hints = hints;

        setChanged();
        notifyObservers(hints.size());
    }

    // accessor method for hints array list
    public ArrayList<int[]> getHints(){
        return hints;
    }

    //allows user to use magicTrick
    public void startMagicTrick(){
        isMagicTrick++;
    }*/

    //plays an audio clip when points are scored.
    public void playSound(){
        String coinSound = "coinSound.wav";
        try {
            InputStream in = new FileInputStream(coinSound);
            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(in);

            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public boolean checkForMagicTrick(int row, int column){
        int magicNumber;
        //make sure the magic trick has been started and the tile placed on is not empty.
        if(isMagicTrick==1 && !tiles[row][column].isEmpty()) {
            magicNumber=tiles[row][column].getValue();
            System.out.println("MADE IT, magicNumber="+magicNumber);
            for(int i=0;i<=8;i++){
                for(int j=0; j<=8;j++){
                    if (!tiles[i][j].isEmpty() && tiles[i][j].getValue()==magicNumber) {
                        tiles[i][j].clear();
                    }

                }
            }
            isMagicTrick=-1;
            return false;
            //if they placed the magic trick on an empty tile, give the trick back
        } else {
            //give the magic trick back
            isMagicTrick=0;

            System.out.println("Not a magic trick or is empty: "+isMagicTrick);
            return true;
            //if the magic trick is not in play, then update the tile as normal. if (isMagicTrick==1 && tiles[row][column].isEmpty())
        }


    }
}
