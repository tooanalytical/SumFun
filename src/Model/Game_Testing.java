package Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by bmwhi on 4/30/2017.
 */
public class Game_Testing {



    /*Tests for clicking on a non-empty tile*/
    @Test
    public final void testUntimedNotEmptyTile() {

        //create a new game
        Game testGame = new Game(1);

        //place a tile to set up the board for test
        testGame.tiles[0][0].setValue(5);

        //try to update the tile with another tile
        boolean testResult=testGame.updateTiles(0,0);
        boolean actualResult=false;

        //check result
        assertEquals(actualResult, testResult);

    }

    /*Tests for clicking on an empty tiles*/
    @Test
    public final void testTileUntimedEmptyTileWithNoMoves() {

        //create a new game
        Game testGame = new Game(1);

        while(testGame.movesLeft.getMovesLeft()>=0) {
            testGame.movesLeft.updateMovesLeft();
        }

        //try to update the tile with another tile
        boolean testResult=testGame.updateTiles(0,0);
        boolean actualResult=true;

        //check result
        assertEquals(actualResult, testResult);



    }
    @Test
    public final void testUntimedEmptyMovesLeft() {

        //create a new game
        Game testGame = new Game(1);

        while(testGame.movesLeft.getMovesLeft()>=15) {
            testGame.movesLeft.updateMovesLeft();
        }

        //try to update the tile with another tile
        boolean testResult=testGame.updateTiles(0,0);
        boolean actualResult=false;

        //check result
        assertEquals(actualResult, testResult);


    }

    /*Tests for clicking on a non-empty tile*/
    @Test
    public final void testTileIsNotEmptyCornerTimed() {

        //create a new game
        Game testGame = new Game(0);

        //place a tile to set up the board for test
        testGame.tiles[0][0].setValue(5);

        //try to update the tile with another tile
        boolean testResult=testGame.updateTiles(0,0);
        boolean actualResult=false;

        //check result
        assertEquals(actualResult, testResult);

    }

    /*Tests for clicking on an empty tiles*/
    @Test
    public final void testTileIsEmptyCornerTimed() {

        //create a new game
        Game testGame = new Game(2);

        testGame.isUntimed=false;
        if(testGame.isUntimed==false){
            //GameTimer gameTimer= new GameTimer();

                //System.out.println("OUt");

                //try to update the tile with another tile
                testGame.gameTimer.timeRemaining="4:30";

                //****currently says that the timer has a null exception error
            if(testGame.gameTimer.timeRemaining!="0:00") {
                boolean testResult = testGame.updateTiles(0, 0);
                boolean actualResult = false;

                //check result
                assertEquals(actualResult, testResult);
            }
        }
    }


    @Test
    public final void testNoMoreMovesLeft(){
        //Create a Game
        Game testGame = new Game(1);


        // must be a untimed game
        testGame.isUntimed=true;

        // zero moves left
        while(testGame.movesLeft.getMovesLeft()>0) {
            testGame.movesLeft.updateMovesLeft();
        }
        //try to update the tile with another tile
        boolean testResult=testGame.updateTiles(0,0);
        boolean actualResult=true;

        //

        assertEquals(actualResult, testResult);
    }

    @Test
    public final void test50MoreMovesLeftCorner(){
        //Create a Game
        Game testGame = new Game(1);

        // must be a untimed game
        testGame.isUntimed=true;

        //try to update the tile with another tile
        boolean testResult=testGame.updateTiles(0,0);
        boolean actualResult=false;

        assertEquals(actualResult, testResult);
    }

    @Test
    public final void testHitStatusUntimedNeg1Corner(){
        //Create a Game
        Game testGame = new Game(1);

        // must be a untimed game
        testGame.isUntimed=true;

        //not a hit
       int testResult= testGame.isHit(0,  0);
       int actualResult=-1;

       assertEquals(actualResult, testResult);

    }

    @Test
    public final void testHitStatusUntimed1Corner1Neighbor(){
        //Create a Game
        Game testGame = new Game(1);

        testGame.queue.setTop(6);


        //testGame.tiles[0][1].setValue(1);
        //testGame.tiles[1][0].setValue(2);
        testGame.tiles[1][1].setValue(6);

        int testQueueValue=testGame.queue.getTop();


        System.out.println(testQueueValue+" "+ testGame.tiles[0][1].getValue());
        System.out.println(testGame.tiles[1][0].getValue()+" "+ testGame.tiles[1][1].getValue());

        // must be a untimed game
        testGame.isUntimed=true;

        //not a hit
        int testResult= testGame.isHit(0,  0);
        int actualResult=1;

        assertEquals(actualResult, testResult);
    }

    @Test
    public final void testHitStatusUntimed1Corner3Neighbor(){
        //Creates a Game
        Game testGame = new Game(1);

        testGame.queue.setTop(6);


        testGame.tiles[0][1].setValue(1);
        testGame.tiles[1][0].setValue(2);
        testGame.tiles[1][1].setValue(3);

        int testQueueValue=testGame.queue.getTop();


        System.out.println(testQueueValue+" "+ testGame.tiles[0][1].getValue());
        System.out.println(testGame.tiles[1][0].getValue()+" "+ testGame.tiles[1][1].getValue());

        // must be a untimed game
        testGame.isUntimed=true;

        //not a hit
        int testResult= testGame.isHit(0,  0);
        int actualResult=3;

        assertEquals(actualResult, testResult);
    }

    @Test
    public final void testUntimedSetTileValue(){
        //Creates a Game
        Game testGame = new Game(1);

        testGame.queue.setTop(6);
        
        // must be a untimed game
        testGame.isUntimed=true;

        //not a hit
        int testIsHit= testGame.isHit(0,  0);

        if(testIsHit==-1){
            testGame.tiles[0][0].setValue(6);
        }

        int testResult=testGame.tiles[0][0].getValue();
        int actualResult=6;

        assertEquals(actualResult, testResult);

    }

    @Test
    public final void testUntimedAddNumberOfNeighbors(){
        //Creates a Game
        Game testGame = new Game(1);

        testGame.queue.setTop(6);


        testGame.tiles[0][1].setValue(1);
        testGame.tiles[1][0].setValue(2);
        testGame.tiles[1][1].setValue(3);


        // must be a untimed game
        testGame.isUntimed=true;

        //not a hit
        int testIsHit= testGame.isHit(0,  0);

        if(testIsHit==-1){
            testGame.tiles[0][0].setValue(6);

        }
    }
}
