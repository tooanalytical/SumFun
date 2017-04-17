package Model;

import View.Menu;

import java.io.*;

public class HiScore {

    private String[] untimedNames = new String[10];
    private String[] timedNames = new String[10];
    private String[] untimedScores = new String[10];
    private String[] timedScores = new String[10];

    public void loadScores() {

        String fileName = "High Scores.txt";


        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for(int i = 0; i <= 9; i++){
                untimedNames[i] = bufferedReader.readLine();
            }
            for(int i = 0; i <=9; i++){
                timedNames[i] = bufferedReader.readLine();
            }
            for(int i = 0; i <=9; i++){
                untimedScores[i] = bufferedReader.readLine();
            }
            for(int i = 0; i <=9; i++){
                timedScores[i] = bufferedReader.readLine();
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
    }

    public void saveScores() {

        String fileName = "High Scores.txt";

        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            while (bufferedReader.readLine() != null){
                bufferedWriter.write("");
            }

            bufferedReader.close();

            for (int i =0; i <=9; i++){
                bufferedWriter.write(untimedNames[i]);
                bufferedWriter.newLine();
            }

            for (int i =0; i <=9; i++){
                bufferedWriter.write(timedNames[i]);
                bufferedWriter.newLine();
            }

            for (int i =0; i <=9; i++){
                bufferedWriter.write(untimedScores[i]);
                bufferedWriter.newLine();
            }

            for (int i =0; i <=9; i++){
                bufferedWriter.write(timedScores[i]);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
        }
    }

    public void updateScore() {

    }

    public String[] getUntimedNames() {
        return untimedNames;
    }

    public String[] getTimedNames() {
        return timedNames;
    }

    public String[] getUntimedScores() {
        return untimedScores;
    }

    public String[] getTimedScores() {
        return timedScores;
    }
}
