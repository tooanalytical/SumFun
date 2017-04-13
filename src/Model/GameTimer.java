package Model;

import java.util.Observable;


public class GameTimer extends Observable {
    String timeRemaining = "5:00";
    private int startTime;

    public GameTimer(){
        startTime = 300;
    }
    public void updateTimeLeft() {
        if(startTime > 0) {
            startTime--;
            int minutes = startTime / 60;
            int sec = startTime % 60;
            String seconds = Integer.toString(startTime % 60);
            if(sec >= 0 && sec <= 9) {
                seconds = "0".concat(seconds);
            }
            timeRemaining = minutes + ":" + seconds;
            setChanged();
            notifyObservers();
        }
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void notTimedGame(){
        timeRemaining = "0:00";

    }
}


