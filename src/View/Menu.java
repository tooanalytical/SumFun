package View;

import javax.swing.*;
import java.awt.*;

import Model.*;
import Control.*;

// ability to create two different menu JPanels
// one contains the start panel, which is the first panel the user sees
// the other contains the game type panel, which allows the user to select which game type they want to play
public class Menu {

    public static final int START_MENU = 1;
    public static final int GAME_TYPE_MENU = 2;

    private JPanel pnlMaster;

    private Game game;

    public Menu(int menuType, Game game){
        pnlMaster = new JPanel();
        pnlMaster.setLayout(new GridLayout(3, 1));

        this.game = game;

        if(menuType == START_MENU){
            buildStartMenu();
        }

        if(menuType == GAME_TYPE_MENU){
            buildGameTypeMenu();
        }
    }

    public JPanel retrieveMasterPanel(){
        return pnlMaster;
    }

    private void buildStartMenu(){
        // instantiates start button and adds to panel
        JPanel pnlStart = new JPanel();
        pnlStart.setLayout(new BorderLayout());
        JButton btnStart = new JButton();
        btnStart.setText("Start");
        btnStart.setFont(new Font("Arial", Font.PLAIN, 20));
        btnStart.setContentAreaFilled(false);
        btnStart.setOpaque(true);
        btnStart.addActionListener(new MenuListener(game));
        pnlStart.add(btnStart, BorderLayout.CENTER);
        pnlMaster.add(pnlStart);

        // instantiates hiscores button and adds to panel
        JPanel pnlHiScores = new JPanel();
        pnlHiScores.setLayout(new BorderLayout());
        JButton btnHiScores = new JButton();
        btnHiScores.setText("HiScores");
        btnHiScores.setFont(new Font("Arial", Font.PLAIN, 20));
        btnHiScores.setContentAreaFilled(false);
        btnHiScores.setOpaque(true);
        btnHiScores.addActionListener(new MenuListener(game));
        pnlHiScores.add(btnHiScores, BorderLayout.CENTER);
        pnlMaster.add(pnlHiScores);

        // instantiates exit button and adds to panel
        JPanel pnlExit = new JPanel();
        pnlExit.setLayout(new BorderLayout());
        JButton btnExit = new JButton();
        btnExit.setText("Exit");
        btnExit.setFont(new Font("Arial", Font.PLAIN, 20));
        btnExit.setContentAreaFilled(false);
        btnExit.setOpaque(true);
        btnExit.addActionListener(new MenuListener(game));
        pnlExit.add(btnExit, BorderLayout.CENTER);
        pnlMaster.add(pnlExit);
    }

    private void buildGameTypeMenu(){
        // instantiates untimed button and adds to panel
        JPanel pnlUntimed = new JPanel();
        pnlUntimed.setLayout(new BorderLayout());
        JButton btnUntimed = new JButton();
        btnUntimed.setText("Untimed");
        btnUntimed.setFont(new Font("Arial", Font.PLAIN, 20));
        btnUntimed.setContentAreaFilled(false);
        btnUntimed.setOpaque(true);
        btnUntimed.addActionListener(new MenuListener(game));
        pnlUntimed.add(btnUntimed, BorderLayout.CENTER);
        pnlMaster.add(pnlUntimed);

        // instantiates timed button and adds to panel
        JPanel pnlTimed = new JPanel();
        pnlTimed.setLayout(new BorderLayout());
        JButton btnTimed = new JButton();
        btnTimed.setText("Timed");
        btnTimed.setFont(new Font("Arial", Font.PLAIN, 20));
        btnTimed.setContentAreaFilled(false);
        btnTimed.setOpaque(true);
        btnTimed.addActionListener(new MenuListener(game));
        pnlTimed.add(btnTimed, BorderLayout.CENTER);
        pnlMaster.add(pnlTimed);

        // instantiates back button and adds to panel
        JPanel pnlBack = new JPanel();
        pnlBack.setLayout(new BorderLayout());
        JButton btnBack = new JButton();
        btnBack.setText("Back");
        btnBack.setFont(new Font("Arial", Font.PLAIN, 20));
        btnBack.setContentAreaFilled(false);
        btnBack.setOpaque(true);
        btnBack.addActionListener(new MenuListener(game));
        pnlBack.add(btnBack, BorderLayout.CENTER);
        pnlMaster.add(pnlBack);
    }

}
