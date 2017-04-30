package View;

import Model.Game;
import Model.HiScore;
import Model.HighScoresModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

// ability to create three different JPanels
// start panel, game type panel, and hiscores
public class Menu {

    public static final int START_MENU = 1;
    public static final int GAME_TYPE_MENU = 2;

    private static final int HEIGHT = 100;
    private static final int WIDTH = 300;

    private JLabel[] untimedNames;
    private JLabel[] untimedScores;
    private JLabel[] timedNames;
    private JLabel[] timedScores;

    private JPanel pnlMaster;
    private Dimension d;

    public Menu(int menuType){
        pnlMaster = new JPanel();
        pnlMaster.setLayout(new GridLayout(4, 1));
        d = new Dimension(WIDTH, HEIGHT);
        untimedNames = new JLabel[10];
        untimedScores = new JLabel[10];
        timedNames = new JLabel[10];
        timedScores = new JLabel[10];

        if(menuType == START_MENU){
            buildStartMenu();
        }

        if(menuType == GAME_TYPE_MENU){
            buildGameTypeMenu();
        }

        /*
        if(menuType == HI_SCORE_MENU){
            buildHiScoreMenu();
        }
        */
    }

    public JPanel retrieveMasterPanel(){
        return pnlMaster;
    }

    private void buildStartMenu(){
        //game title
        JPanel pnlGameTitle = new JPanel();
        pnlGameTitle.setLayout(new GridBagLayout());
        Icon icon = new ImageIcon("SumFunTitle2.gif");
        JLabel label = new JLabel(icon);
        pnlGameTitle.add(label);
        pnlMaster.add(pnlGameTitle);

        // instantiate start button and adds to panel
        JPanel pnlStart = new JPanel();
        pnlStart.setLayout(new GridBagLayout());
        JButton btnStart = new JButton();
        btnStart.setPreferredSize(d);
        btnStart.setText("Start");
        btnStart.setFocusPainted(false);
        btnStart.setBackground(Color.black);
        btnStart.setForeground(new Color(76,150,236));
        btnStart.setFont(new Font("Arial", Font.PLAIN, 20));
        btnStart.setContentAreaFilled(false);
        btnStart.setOpaque(true);
        btnStart.addActionListener(e -> {
            JButton btn = (JButton) e.getSource();
            Application app = (Application) btn.getRootPane().getParent();
            Menu menu = new Menu(Menu.GAME_TYPE_MENU);
            app.updateMasterPanel(menu.retrieveMasterPanel());
        });
        pnlStart.add(btnStart);
        pnlMaster.add(pnlStart);

        // instantiates hiscores button and adds to panel
        JPanel pnlHiScores = new JPanel();
        pnlHiScores.setLayout(new GridBagLayout());
        JButton btnHiScores = new JButton();
        btnHiScores.setPreferredSize(d);
        btnHiScores.setText("HiScores");
        btnHiScores.setFocusPainted(false);
        btnHiScores.setBackground(Color.black);
        btnHiScores.setForeground(new Color(76,150,236));
        btnHiScores.setFont(new Font("Arial", Font.PLAIN, 20));
        btnHiScores.setContentAreaFilled(false);
        btnHiScores.setOpaque(true);
        btnHiScores.addActionListener(e -> {
            HighScoresModel highScores = new HighScoresModel();
            new HighScoresView(highScores, "", "", LocalDateTime.now());
        });
        pnlHiScores.add(btnHiScores);
        pnlMaster.add(pnlHiScores);

        // instantiates exit button and adds to panel
        JPanel pnlExit = new JPanel();
        pnlExit.setLayout(new GridBagLayout());
        JButton btnExit = new JButton();
        btnExit.setPreferredSize(d);
        btnExit.setText("Exit");
        btnExit.setFocusPainted(false);
        btnExit.setBackground(Color.black);
        btnExit.setForeground(new Color(76,150,236));
        btnExit.setFont(new Font("Arial", Font.PLAIN, 20));
        btnExit.setContentAreaFilled(false);
        btnExit.setOpaque(true);
        btnExit.addActionListener(e -> {
            System.exit(0);
        });
        pnlExit.add(btnExit);
        pnlMaster.add(pnlExit);
    }

    private void buildGameTypeMenu(){
        JPanel mode = new JPanel();
        mode.setLayout(new GridBagLayout());
        JLabel selectMode = new JLabel();
        selectMode.setText(" Select Game Mode");
        selectMode.setFont(new Font("Arial", Font.PLAIN, 40));
        mode.add(selectMode);
        pnlMaster.add(mode);
        // instantiates untimed button and adds to panel
        JPanel pnlUntimed = new JPanel();
        pnlUntimed.setLayout(new GridBagLayout());
        JButton btnUntimed = new JButton();
        btnUntimed.setPreferredSize(d);
        btnUntimed.setText("Untimed");
        btnUntimed.setFocusPainted(false);
        btnUntimed.setBackground(Color.black);
        btnUntimed.setForeground(new Color(76,150,236));
        btnUntimed.setFont(new Font("Arial", Font.PLAIN, 20));
        btnUntimed.setContentAreaFilled(false);
        btnUntimed.setOpaque(true);
        btnUntimed.addActionListener(e -> {
            JButton btn = (JButton) e.getSource();
            Application app = (Application) btn.getRootPane().getParent();
            Game game = new Game(Game.UNTIMED);
            UntimedBoard untimedBoard = new UntimedBoard(game);
            app.updateMasterPanel(untimedBoard.retrieveMasterPanel());
        });
        pnlUntimed.add(btnUntimed);
        pnlMaster.add(pnlUntimed);

        // instantiates timed button and adds to panel
        JPanel pnlTimed = new JPanel();
        pnlTimed.setLayout(new GridBagLayout());
        JButton btnTimed = new JButton();
        btnTimed.setPreferredSize(d);
        btnTimed.setText("Timed");
        btnTimed.setFocusPainted(false);
        btnTimed.setBackground(Color.black);
        btnTimed.setForeground(new Color(76,150,236));
        btnTimed.setFont(new Font("Arial", Font.PLAIN, 20));
        btnTimed.setContentAreaFilled(false);
        btnTimed.setOpaque(true);
        btnTimed.addActionListener(e -> {
            JButton btn = (JButton) e.getSource();
            Application app = (Application) btn.getRootPane().getParent();
            Game game = new Game(Game.TIMED);
            TimedBoard timedBoard = new TimedBoard(game);
            app.updateMasterPanel(timedBoard.retrieveMasterPanel());
        });
        pnlTimed.add(btnTimed);
        pnlMaster.add(pnlTimed);

        // instantiates back button and adds to panel
        JPanel pnlBack = new JPanel();
        pnlBack.setLayout(new GridBagLayout());
        JButton btnBack = new JButton();
        btnBack.setPreferredSize(d);
        btnBack.setText("Main Menu");
        btnBack.setFocusPainted(false);
        btnBack.setBackground(Color.black);
        btnBack.setForeground(new Color(76,150,236));
        btnBack.setFont(new Font("Arial", Font.PLAIN, 20));
        btnBack.setContentAreaFilled(false);
        btnBack.setOpaque(true);
        btnBack.addActionListener(e -> {
            JButton btn = (JButton) e.getSource();
            Application app = (Application) btn.getRootPane().getParent();
            Menu menu = new Menu(Menu.START_MENU);
            app.updateMasterPanel(menu.retrieveMasterPanel());
        });
        pnlBack.add(btnBack);
        pnlMaster.add(pnlBack);
    }

    /*
    private void buildHiScoreMenu() {

        //instantiates hi scores for both untimed and timed games
        JPanel pnlTitle = new JPanel();
        pnlTitle.setLayout(new GridBagLayout());
        JLabel title = new JLabel("High Scores");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        pnlTitle.add(title);
        pnlMaster.add(pnlTitle);

        JPanel pnlHiScores = new JPanel();
        pnlHiScores.setLayout(new GridLayout(1,2));

        //Untimed Results
        JPanel pnlUntimedSection = new JPanel();
        pnlUntimedSection.setLayout(new GridLayout(2,1));
        JLabel untimedScoresTitle = new JLabel("Untimed Scores");
        untimedScoresTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlUntimedSection.add(untimedScoresTitle);
        pnlHiScores.add(pnlUntimedSection);

        JPanel pnlUntimedResults = new JPanel();
        pnlUntimedResults.setLayout(new GridLayout(1,2));
        pnlUntimedSection.add(pnlUntimedResults);

        //Populates all Untimed Names in View
        JPanel pnlUntimedName = new JPanel();
        pnlUntimedName.setLayout(new GridLayout(10, 1));
        String[] tempUntimedName = score.getUntimedNames();
        for(int i = 0; i <=9; i++){
            untimedNames[i] = new JLabel();
            untimedNames[i].setText(tempUntimedName[i]);
            pnlUntimedName.add(untimedNames[i]);
        }
        pnlUntimedResults.add(pnlUntimedName);

        //Populates all Untimed Scores in View
        JPanel pnlUntimedScore = new JPanel();
        pnlUntimedScore.setLayout(new GridLayout(10,1));
        String[] tempUntimedScore = score.getUntimedScores();
        for(int i = 0; i <=9; i++){
            untimedScores[i] = new JLabel();
            untimedScores[i].setText(tempUntimedScore[i]);
            pnlUntimedScore.add(untimedScores[i]);
        }
        pnlUntimedResults.add(pnlUntimedScore);


        //Timed Results
        JPanel pnlTimedSection = new JPanel();
        pnlTimedSection.setLayout(new GridLayout(2,1));
        JLabel timedScoresTitle = new JLabel("Timed Scores");
        timedScoresTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlTimedSection.add(timedScoresTitle);
        pnlHiScores.add(pnlTimedSection);

        JPanel pnlTimedResults = new JPanel();
        pnlTimedResults.setLayout(new GridLayout(1,2));
        pnlTimedSection.add(pnlTimedResults);

        JPanel pnlTimedName = new JPanel();
        pnlTimedName.setLayout(new GridLayout(10, 1));
        String[] tempTimedName = score.getTimedNames();
        for(int i = 0; i <=9; i++){
            timedNames[i] = new JLabel();
            timedNames[i].setText(tempTimedName[i]);
            pnlTimedName.add(timedNames[i]);
        }
        pnlTimedResults.add(pnlTimedName);

        JPanel pnlTimedScore = new JPanel();
        pnlTimedScore.setLayout(new GridLayout(10,1));
        String[] tempTimedScore = score.getTimedScores();
        for(int i = 0; i <=9; i++){
            timedScores[i] = new JLabel();
            timedScores[i].setText(tempTimedScore[i]);
            pnlTimedScore.add(timedScores[i]);
        }
        pnlTimedResults.add(pnlTimedScore);


        pnlMaster.add(pnlHiScores);

        // instantiates back button and adds to panel
        JPanel pnlBack = new JPanel();
        pnlBack.setLayout(new GridBagLayout());
        JButton btnBack = new JButton();
        btnBack.setPreferredSize(d);
        btnBack.setText("Main Menu");
        btnBack.setFocusPainted(false);
        btnBack.setBackground(Color.black);
        btnBack.setForeground(new Color(76,150,236));
        btnBack.setFont(new Font("Arial", Font.PLAIN, 20));
        btnBack.setContentAreaFilled(false);
        btnBack.setOpaque(true);
        btnBack.addActionListener(e -> {
            JButton btn = (JButton) e.getSource();
            Application app = (Application) btn.getRootPane().getParent();
            Menu menu = new Menu(Menu.START_MENU, score);
            app.updateMasterPanel(menu.retrieveMasterPanel());
        });
        pnlBack.add(btnBack);
        pnlMaster.add(pnlBack);
    }
    */

}
