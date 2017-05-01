package Model;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class Game_Testing {

    ////////////////////////////
    // START GLASS BOX TESTING//
    ////////////////////////////

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

        //creates a new game
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
    public final void testHitStatusNotNegOne() {

        //create a new game
        Game testGame = new Game(0);

        testGame.tiles[0][1].setValue(0);
        testGame.tiles[1][1].setValue(0);


        testGame.queue.setTop(0);

        //try to update the tile with another tile
        int testResult = testGame.isHit(0,0);
        int actualResult = 2;

        //check result
        assertEquals(actualResult, testResult);

    }

    @Test
    public final void testHitStatusNegOne() {

        //create a new game
        Game testGame = new Game(0);

        int randomRow = ThreadLocalRandom.current().nextInt(0, 8 + 1);
        int randomCol = ThreadLocalRandom.current().nextInt(0, 8 + 1);

        testGame.queue.setTop(0);

        //try to update the tile with another tile
        int testResult = testGame.isHit(randomRow, randomCol);
        int actualResult = -1;

        //check result
        assertEquals(actualResult, testResult);

    }
    @Test
    public final void testHitStatusGreaterEqualToThree() {

        //create a new game
        Game testGame = new Game(0);

        testGame.tiles[0][1].setValue(0);
        testGame.tiles[1][1].setValue(0);
        testGame.tiles[1][0].setValue(0);

        testGame.queue.setTop(0);

        //try to update the tile with another tile
        int testResult = testGame.isHit(0,0);
        int actualResult = 3;

        //check result
        assertEquals(actualResult, testResult);

    }

    @Test
    public final void testHitStatusNotGreaterEqualThree() {

        //create a new game
        Game testGame = new Game(0);

        testGame.tiles[1][1].setValue(0);

        testGame.queue.setTop(0);

        //try to update the tile with another tile
        int testResult = testGame.isHit(0,0);
        int actualResult = 1;

        //check result
        assertEquals(actualResult, testResult);

    }

    /*Tests for clicking on an empty tiles*/
    @Test
    public final void testTimedNumEmptyTilesEqualZero() {

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

                for(int i = 0; i < 9; i++) {
                    for(int j = 0; j < 9; j++) {
                        testGame.updateTiles(i,j);
                    }
                }

                boolean testResult = testGame.updateTiles(0, 0);
                boolean actualResult = false;

                //check result
                assertEquals(actualResult, testResult);
            }
        }
    }


    @Test
    public final void testUntimedNumEmptyTilesEqualZero() {

        //create a new game
        Game testGame = new Game(1);

        testGame.isUntimed=false;
        if(testGame.isUntimed==true){
            //GameTimer gameTimer= new GameTimer();

            //System.out.println("OUt");

                for(int i = 0; i < 9; i++) {
                    for(int j = 0; j < 9; j++) {
                        testGame.updateTiles(i,j);
                    }
                }

                boolean testResult = testGame.updateTiles(0, 0);
                boolean actualResult = false;

                //check result
                assertEquals(actualResult, testResult);
            }
        }
    @Test
    public final void testUntimedNumEmptyTilesEqualEightyOne() {

        //create a new game
        Game testGame = new Game(1);

        testGame.isUntimed=false;
        if(testGame.isUntimed==true){
            //GameTimer gameTimer= new GameTimer();

            //System.out.println("OUt");

            for(int i = 0; i < 9; i++) {
                for(int j = 0; j < 9; j++) {
                    testGame.tiles[i][j].clear();
                }
            }

            testGame.tiles[0][0].setValue(3);

            testGame.queue.setTop(3);

            boolean testResult = testGame.updateTiles(0,1);
            boolean actualResult = true;

            //check result
            assertEquals(actualResult, testResult);
        }
    }

    @Test
    public final void testUntimedNumEmptyTilesNotEqualEightyOne() {

        //create a new game
        Game testGame = new Game(1);

        testGame.isUntimed=false;
        if(testGame.isUntimed==true){
            //GameTimer gameTimer= new GameTimer();

            //System.out.println("OUt");

            boolean testResult = testGame.updateTiles(1,1);
            boolean actualResult = true;

            //check result
            assertEquals(actualResult, testResult);
        }
    }

    //////////////////////////
    // END GLASS BOX TESTING//
    //////////////////////////


    ////////////////////////////
    // START BLACK BOX TESTING//
    ////////////////////////////

    //This should pass as it is just inside the outer limits of the bounderies
    @Test
    public final void testUpdateTilesBlackBoxPass() {
        //create a new game
        Game testGame = new Game(1);

        boolean testResult = testGame.updateTiles(8,8);
        boolean actualResult = false;

        //check result
        assertEquals(actualResult, testResult);

    }


    //This should fail as it is outside the bounderies
    @Test
    public final void testUpdateTilesBlackBoxFail() {
        //create a new game
        Game testGame = new Game(1);

        boolean testResult = testGame.updateTiles(9,9);
        boolean actualResult = false;

        //check result
        assertEquals(actualResult, testResult);

    }

}
