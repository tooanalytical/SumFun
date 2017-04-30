package Model;

import java.util.Observable;
import javax.swing.Timer;

public class GameTimer extends Observable {
    private String timeRemaining = "5:00";
    private int startTime;
    private Timer timer = new Timer(1000, e ->   updateTimeLeft());

    public GameTimer(){
        startTime = 300;
        stopTimer();
    }



    private void updateTimeLeft() {
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

    public String getTimeElapsed(){
        String m = "" + timeRemaining.charAt(0);
        String s = timeRemaining.substring(2);
        int min = Integer.parseInt(m);
        int sec = Integer.parseInt(s);

        String mins = Integer.toString(4 - min);
        String secs;
        if(sec > 50){
            secs = "0" + Integer.toString(60 - sec);
        } else if(sec > 0){
            secs = Integer.toString(60 - sec);
        } else {
            mins = Integer.toString(4 - (min - 1));
            secs = "00";
        }

        return mins + ":" + secs;
    }

    public void stopTimer(){
        timer.stop();
    }

    public void startTimer(){
        timer.start();
    }
}


