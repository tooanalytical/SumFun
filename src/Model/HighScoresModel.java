package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class HighScoresModel extends Observable implements Serializable {

    private ArrayList<String[]> mostPoints;
    private ArrayList<String[]> leastTime;
    private File file;

    private int pIndex = -1;
    private int tIndex = -1;

    public HighScoresModel(){
        mostPoints = new ArrayList<>();
        leastTime = new ArrayList<>();

        file = new File("highscores.txt");
        updateArrayLists();
    }

    // private helper method
    // updates arraylists w/ lines from file
    private void updateArrayLists(){
        mostPoints.clear();
        leastTime.clear();

        try{
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine()){
                String[] values = reader.nextLine().split(",");
                if(values[0].equals("p")){
                    mostPoints.add(values);
                } else if(values[0].equals("t")){
                    leastTime.add(values);
                }
            }

            reader.close();

        } catch(FileNotFoundException ex) {
            System.out.println("ERROR: Could not read highscores file.");
        }
    }

    // updates file
    public void updateHighScores(String type, String date,
                                 String name, String score){
        // updates arraylists
        String[] newHs = {type, date, name, score};
        if(type.equals("p")){
            if(mostPoints.isEmpty()){
                mostPoints.add(newHs);
            } else {
                mostPoints.add(pIndex, newHs);
            }
        }
        if(type.equals("t")){
            if(leastTime.isEmpty()){
                leastTime.add(newHs);
            } else {
                leastTime.add(tIndex, newHs);
            }
        }

        // updates file
        int psize = mostPoints.size();
        if(psize > 10){
            psize = 10;
        }
        int tsize = leastTime.size();
        if(tsize > 10){
            tsize = 10;
        }
        try{
            FileWriter writer = new FileWriter(file);
            for(int i = 0; i < psize; i++){
                String[] arr = mostPoints.get(i);
                writer.write(arr[0] + "," + arr[1] + ","
                        + arr[2] + "," + arr[3] + "\n");
            }
            for(int j = 0; j < tsize; j++){
                String[] arr = leastTime.get(j);
                writer.write(arr[0] + "," + arr[1] + ","
                        + arr[2] + "," + arr[3] + "\n");
            }

            writer.flush();
            writer.close();

        } catch(Exception ex) {
            System.out.println("ERROR: Could not write to highscores file.");
        }

        updateArrayLists();
        setChanged();
        notifyObservers(type);
    }

    // returns a copy of most points arraylist
    // removes "p" from first index of each String[] in arraylist
    public ArrayList<String[]> getMostPoints(){
        ArrayList<String[]> mostPoints = new ArrayList<>();

        for(String[] arr : this.mostPoints){
            String[] newArr = new String[3];
            for(int i = 1; i < 4; i++){
                newArr[i - 1] = arr[i];
            }
            mostPoints.add(newArr);
        }

        return mostPoints;
    }

    // returns a copy of least time arraylist
    // removes "t" from first index of each String[] in arraylist
    public ArrayList<String[]> getLeastTime(){
        ArrayList<String[]> leastTime = new ArrayList<>();

        for(String[] arr : this.leastTime){
            String[] newArr = new String[3];
            for(int i = 1; i < 4; i++){
                newArr[i - 1] = arr[i];
            }
            leastTime.add(newArr);
        }

        return leastTime;
    }

    // returns false if given score is not a high score, else returns true
    // sets pIndex/tIndex w/ index of score
    public boolean checkHighScore(String type, String score){
        if(score.equals("")){
            return false;
        }
        if(type.equals("p")){
            if(mostPoints.isEmpty()){
                return true;
            }
            int intScore = Integer.parseInt(score);
            int size = mostPoints.size();
            for(int i = 0; i < size; i++){
                int highScore = Integer.parseInt(mostPoints.get(i)[3]);
                if(intScore > highScore){
                    pIndex = i;
                    return true;
                }
            }
            if(size < 10){
                pIndex = size;
                return true;
            }
        }
        if(type.equals("t")){
            if(leastTime.isEmpty()){
                return true;
            }
            int intScore = convertToSeconds(score);
            int size = leastTime.size();
            for(int i = 0; i < size; i++){
                int highScore = convertToSeconds(leastTime.get(i)[3]);
                if(highScore > intScore){
                    tIndex = i;
                    return true;
                }
            }
            if(size < 10){
                tIndex = size;
                return true;
            }
        }

        return false;
    }

    // private helper method which converts M:SS string time to int seconds
    private int convertToSeconds(String time){
        int m = Integer.parseInt(("" + time.charAt(0)));
        int s = Integer.parseInt(time.substring(2));

        return m * 60 + s;
    }

}
