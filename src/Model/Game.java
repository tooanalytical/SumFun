package Model;

import View.HighScoresView;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.Timer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

// constructor should check which type of game is being created
// timed or untimed
// only movesLeft OR gameTimer should be instantiated depending on game type
public class Game extends Observable implements Serializable {

    public static final int NUM_ROWS = 9;
    public static final int NUM_COLUMNS = 9;
    private final int Num_Moves_Left = 50;
    public static final int UNTIMED = 1;
    public static final int TIMED = 2;

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
    public int numHintsLeft = 3000;

    // constructor
    public Game(int gameType){
        numEmptyTiles = 32;

        // instantiates models
        tiles = new Tile[NUM_ROWS][NUM_COLUMNS];
        queue = new Queue();
        score = new Score();
        if(gameType == UNTIMED){
            movesLeft = new MovesLeft(Num_Moves_Left);
        } else if(gameType == TIMED){
            gameTimer = new GameTimer();
            isUntimed = false;
        }
        highScores = new HighScoresModel();

        // instantiates two-dimensional tiles array
        for(int r = 0; r < NUM_ROWS; r++){
            for(int c = 0; c < NUM_COLUMNS; c++){
                if(r == 0 || c == 0 || r == NUM_ROWS - 1
                        || c == NUM_COLUMNS - 1){
                    tiles[r][c] = new Tile(true, r, c);
                } else {
                    tiles[r][c] = new Tile(false, r, c);
                }
            }
        }
    }

    // private helper method, used in updateTiles method and getHints method
    // checks if sum of tile neighbors modulus 10 is equal to top of queue
    // if placement is a hit, the number of tiles removed is returned
    // if not a hit, -1 is returned
    public int isHit(int r, int c){
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

        // returns number of neighbors removed (counter)
        // if isHit == true, else returns -1
        if(isHit){
            return counter;
        } else {
            return -1;
        }

    }

    // helper method which returns arraylist of int arrays
    // which contains coords of all valid neighbors
    private ArrayList<int[]> getNeighbors(int r, int c){
        int[][] neighbors = {{r - 1, c - 1}, {r - 1, c},
                {r - 1, c + 1}, {r, c - 1}, {r, c + 1}, {r + 1, c - 1},
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
    // if hitStatus == -1, hit is false;
    // else hitStatus == number of tiles removed, and hit is true
    // returns true if game is over, false if not
    public boolean updateTiles(int r, int c){
        // checks if able to update tiles; if not, returns
        if(!tiles[r][c].isEmpty()){
            return false;
        } else {
            if(isUntimed){
                movesLeft.updateMovesLeft();
                if(movesLeft.getMovesLeft() <= 0){
                    gameOver = true;
                    return true;
                }
            }
        }

        int hitStatus = isHit(r, c);
        // tile at position r,c updated w/ value from top of queue
        tiles[r][c].setValue(queue.getTop());
        if(hitStatus == -1){
            numEmptyTiles--;
        } else {
            // gets valid neighbors
            ArrayList<int[]> validNeighbors = getNeighbors(r, c);
            Timer delayTimer = new Timer(200, null);
            delayTimer.addActionListener(actionEvent -> {
                tiles[r][c].clear();
                for (int[] valid : validNeighbors) {
                    int row = valid[0];
                    int col = valid[1];

                    // clears neighbors & updates model
                    tiles[row][col].clear();
                }
                delayTimer.stop();
            });
            delayTimer.start();
            numEmptyTiles += hitStatus;
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
            if(!isUntimed){
                gameTimer.stopTimer();
            }
            gameOver = true;
            return true;
        } else if(numEmptyTiles == 81){
            // game is won
            return true;
        }

        return false;

    }

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

    public boolean checkForMagicTrick(int row, int column) {
        int magicNumber;
        //make sure the magic trick has been started
        // and the tile placed on is not empty.
        if (isMagicTrick == 1 && !tiles[row][column].isEmpty()) {
            magicNumber = tiles[row][column].getValue();
            for (int i = 0; i <= 8; i++) {
                for (int j = 0; j <= 8; j++) {
                    if (!tiles[i][j].isEmpty()
                            && tiles[i][j].getValue() == magicNumber) {
                        tiles[i][j].clear();
                        numEmptyTiles++;
                       if(numEmptyTiles == 81){
                           checkHighscores();
                       }
                    }

                }
            }
            isMagicTrick = -1;
            return false;
            //if placed the magic trick on an empty tile, give the trick back
        } else {
            //give the magic trick back
            isMagicTrick = 0;
            return true;
            //if the magic trick is not in play, update the tile as normal
            // if (isMagicTrick==1 && tiles[row][column].isEmpty())
        }
    }

    public void checkHighscores(){
        if (isUntimed) {
            playWinningSound();
            new HighScoresView(highScores, Integer.toString(score.getScore()),
                    "", LocalDateTime.now());
        } else {
            gameTimer.stopTimer();
            playWinningSound();
            new HighScoresView(highScores, Integer.toString(score.getScore()),
                    gameTimer.getTimeElapsed(), LocalDateTime.now());
        }
    }

    //sets global hintCoords field
    //return ArrayList containing coordinates of all possible hints
    // only hints that allow user to score the most points
    //ignores hints which don't allow user to score points (2 or less)
    public ArrayList<int[]> getHints(boolean notify){
        //contains coordinates of each hint
        ArrayList<int[]> hintCoords = new ArrayList<>();

        // contains value of current max number of tiles removed
        // starts at 3
        // only hints which remove 3 or more tiles should be considered
        int top = 0;

        // loops through each tile and updates hintCoords as necessary
         for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                // continues if tile is not empty
                if(!tiles[r][c].isEmpty()){
                    continue;
                }
                int hitVal = isHit(r, c);
                int[] coords = {r, c};

                // if hitVal is > than top, create new hintCoords & add to it
                // else if hitVal is > than top,& coords of hitVal to hintCoords
                if(hitVal > top){
                    hintCoords = new ArrayList<>();
                    hintCoords.add(coords);
                    top = hitVal;
                } else if(hitVal == top){
                    hintCoords.add(coords);
                }
            }
        }

        if(notify){
            setChanged();
            notifyObservers(hintCoords.isEmpty());
        }
        return hintCoords;
    }

    public void playLosingSound(){
        String loseSound = "LosingGameSound.wav";
        try {
            InputStream in = new FileInputStream(loseSound);
            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(in);

            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playWinningSound(){
        String winSound = "WinningGameSound.wav";
        try {
            InputStream in = new FileInputStream(winSound);
            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(in);

            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
