package View;

import Control.TileController;
import Model.Game;
import Model.HiScore;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

// builds the main JFrame which contains a single panel representing the window
// also contains the main method, which creates model and initial view
public class Application extends JFrame {

    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 800;

    private JPanel pnlMaster;

    public Application(JPanel initialPanel){
        super();
        pnlMaster = initialPanel;
        setTitle("Sum Fun!");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        // instantiates game model
        //Game game = new Game(numRows, numColumns);
        //TileController controller = new TileController(game);

        HiScore score = new HiScore();
        score.loadScores();

        // creates new menu panel & passes to app jframe
        Menu menu = new Menu(Menu.START_MENU, score);

        // creates new Application
        new Application(menu.retrieveMasterPanel());



    }

}
