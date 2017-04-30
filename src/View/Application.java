package View;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

// builds the main JFrame which contains a single panel representing the window
// also contains the main method, which creates model and initial view
public class Application extends JFrame {

    private final int Window_Width = 1300;
    private final int Window_Height = 700;

    private JPanel pnlMaster;

    public Application(JPanel initialPanel){
        super();
        pnlMaster = initialPanel;
        setTitle("Sum Fun!");
        ImageIcon logo = new ImageIcon("SumFunIcon.png");
        setIconImage(logo.getImage());
        setSize(Window_Width, Window_Height);
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

        // TESTING HIGHSCORES MODEL
        /*
        HighScoresModel hs = new HighScoresModel();
        for(String[] arr : hs.mostPoints){
            for(String s : arr){
                System.out.println(s);
            }
        }
        */


        // instantiates game model
        //Game game = new Game(numRows, numColumns);
        //TileController controller = new TileController(game);

        //new HighScoresView(false, false);

        // creates new menu panel & passes to app jframe
        Menu menu = new Menu(Menu.START_MENU);

        // creates new Application
        new Application(menu.retrieveMasterPanel());



    }

}
