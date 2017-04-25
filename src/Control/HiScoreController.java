package Control;

import View.*;
import Model.*;

public class HiScoreController {

    private HiScore hiScore;
    private Menu menu;
    private Game game;
    private Score score;

    String[] tempUntimedName;
    String[] tempUntimedScore;
    String[] tempTimedName;
    String[] tempTimedScore;

    private int finalScore;

    public HiScoreController(HiScore hiScore, Menu menu, Game game, Score score) {
        this.hiScore = hiScore;
        this.menu = menu;
        this.game = game;
        this.score = score;
    }

    private void hiScoreUpdate(){

        int namePosition = 0;
        int scorePosition = 0;
        //Checks to see if an untimed game has ended
        if (game.gameTimer.getTimeRemaining() == "" && game.movesLeft.getMovesLeft() == 0){
            //sets local variable as the current score
            finalScore = score.getScore();

            tempUntimedName = hiScore.getUntimedNames();
            tempUntimedScore = hiScore.getUntimedScores();

            for(int i = 0; i < tempUntimedScore.length; i++){
                int temp = Integer.parseInt(tempUntimedScore[i]);

                if(temp < finalScore){
                    for(int j = 0; j < tempUntimedScore.length; j++){

                    }
                }

            }







        }

        //Checks to see if a timed game has ended
        if(game.gameTimer.getTimeRemaining().equals("00:00") && game.movesLeft.getMovesLeft() > 0){



            tempTimedName = hiScore.getTimedNames();
            tempTimedScore = hiScore.getTimedScores();


        }

    }


}
