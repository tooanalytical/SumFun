package Control;

import java.awt.event.*;
import javax.swing.*;
import View.*;
import Model.*;

public class MenuListener implements ActionListener {

    private Game game;

    public MenuListener(Game game){
        this.game = game;
    }

    public void actionPerformed(ActionEvent e){
        JButton btn = (JButton) e.getSource();
        Application app = (Application) btn.getRootPane().getParent();

        if(btn.getText() == "Exit"){
            System.exit(0);
        }

        if(btn.getText() == "New Game" || btn.getText() == "Start"){
            // code for instantiating game type menu panel
            Menu menu = new Menu(Menu.GAME_TYPE_MENU, game);
            app.updateMasterPanel(menu.retrieveMasterPanel());
        }

        if(btn.getText() == "HiScores"){
            // code for instantiating hiscores panel
        }

        if(btn.getText() == "Untimed"){
            // code for instantiating untimed game panel
            Game game = new Game(this.game.getNumRows(), this.game.getNumColumns());
            UntimedBoard untimedBoard = new UntimedBoard(game);
            app.updateMasterPanel(untimedBoard.retrieveMasterPanel());
        }

        if(btn.getText() == "Timed"){
            // code for instantiating timed game panel
            Game game = new Game(this.game.getNumRows(), this.game.getNumColumns());
            TimedBoard timedBoard = new TimedBoard(game);
            app.updateMasterPanel(timedBoard.retrieveMasterPanel());
        }

        if(btn.getText() == "Main Menu"){
            // code for instantiating start panel
            Menu menu = new Menu(Menu.START_MENU, game);
            app.updateMasterPanel(menu.retrieveMasterPanel());
        }
    }
}
