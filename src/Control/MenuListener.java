package Control;

import java.awt.event.*;
import javax.swing.*;
import View.*;
import Model.*;

public class MenuListener implements ActionListener {

    private Game game;
    private HiScore score;


    public MenuListener(Game game, HiScore score){
        this.game = game;
        this.score = score;
    }

    public void actionPerformed(ActionEvent e){
        JButton btn = (JButton) e.getSource();
        Application app = (Application) btn.getRootPane().getParent();

        if(btn.getText().equals("Exit")){
            score.saveScores();
            System.exit(0);
        }

        if(btn.getText().equals("New Game") || btn.getText().equals("Start")){
            // code for instantiating game type menu panel
            Menu menu = new Menu(Menu.GAME_TYPE_MENU, game, score);
            app.updateMasterPanel(menu.retrieveMasterPanel());
        }

        if(btn.getText().equals("HiScores")){
            // code for instantiating hiscores panel
            Menu menu = new Menu(Menu.HI_SCORE_MENU, game, score);
            app.updateMasterPanel(menu.retrieveMasterPanel());
        }

        if(btn.getText().equals("Untimed")){
            // code for instantiating untimed game panel
            Game game = new Game(this.game.getNumRows(), this.game.getNumColumns());
            UntimedBoard untimedBoard = new UntimedBoard(game, score);
            app.updateMasterPanel(untimedBoard.retrieveMasterPanel());
        }

        if(btn.getText().equals("Timed")){
            // code for instantiating timed game panel
            TimedBoard timedBoard = new TimedBoard(game, score);
            app.updateMasterPanel(timedBoard.retrieveMasterPanel());
        }

        if(btn.getText().equals("Back")){
            // code for instantiating start panel
            Menu menu = new Menu(Menu.START_MENU, game, score);
            app.updateMasterPanel(menu.retrieveMasterPanel());
        }
    }
}
