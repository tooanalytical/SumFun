package View;

import Model.*;
import javax.swing.*;

// builds the main JFrame which contains a single panel representing the window
// also contains the main method, which creates the game model and initial view (application)
public class Application extends JFrame {

    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 800;

    private JPanel pnlMaster;

    public Application(JPanel initialPanel){
        super();
        pnlMaster = initialPanel;
        setTitle("Sum Fun!");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(pnlMaster);

        setVisible(true);
    }

    public void updateMasterPanel(JPanel newMasterPanel){
        // removes previous master panel
        remove(pnlMaster);

        // updates master panel
        pnlMaster = newMasterPanel;

        // adds updated master panel to frame
        add(pnlMaster);

        // revalidates frame
        revalidate();
    }

    public static void main(String[] args){

        int numRows = 9;
        int numColumns = 9;

        // instantiates game model
        Game game = new Game(numRows, numColumns);

        HiScore score = new HiScore();
        score.loadScores();

        // creates new menu panel & passes to app jframe
        Menu menu = new Menu(Menu.START_MENU, game, score);

        // creates new Application
        new Application(menu.retrieveMasterPanel());



    }

}
