package View;

import Control.TileController;
import Model.Game;
import Model.GameTimer;
import Model.MovesLeft;
import Model.Queue;
import Model.Score;
import Model.Tile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

//Bailey: I needed a change to commit it.

// creates JPanel, pnlMaster, which represents either UntimedBoard or TimedBoard
public abstract class Board implements Observer{

    // important components contained in master panel
    private JButton btnHint;
    private JPanel pnlMaster;
    private JPanel pnlGame;
    private JPanel pnlInfo;
    private JPanel pnlQueue;
    private JPanel pnlControlInfo;
    private JPanel pnlGameData;
    private JPanel pnlScore;
    private JPanel pnlDuration;
    private JPanel pnlButtons;
    private JButton[][] tileButtons;
    private JLabel[] queueLabels;
    private JLabel lblScore;
    private JLabel ptsEarned;
    protected JLabel lblDurationDesc;
    protected JLabel lblDuration;
    public JButton btnNewGame;

    protected Game game;

    public Board(Game game){
        this.game = game;
        addObservers();

        buildMasterPanel();
    }

    public JPanel retrieveMasterPanel(){
        return pnlMaster;
    }

    public abstract void addObservers();

    private void buildMasterPanel(){
        buildGamePanel();
        buildInfoPanel();

        pnlMaster = new JPanel();
        pnlMaster.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.gridheight = 5;
        //gbc.ipadx = 300;
        //gbc.ipady = 300;
        pnlMaster.add(pnlGame, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        //gbc.fill = GridBagConstraints.NONE;
        //gbc.weightx = 0.0;
        //gbc.weighty = 0.0;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.gridx = 6;
        gbc.gridy = 1;
        pnlMaster.add(pnlInfo, gbc);
    }

    // helper method used to build game panel
    private void buildGamePanel(){
        pnlGame = new JPanel();
        pnlGame.setLayout(new GridLayout(game.NUM_ROWS, game.NUM_COLUMNS));
        //pnlGame.setPreferredSize(new Dimension(700, 700));
        tileButtons = new JButton[game.NUM_ROWS][game.NUM_COLUMNS];

        // instantiates two-dimensional array of tile buttons & adds tile buttons to panel
        for(int r = 0; r < game.NUM_ROWS; r++){
            for(int c = 0; c < game.NUM_COLUMNS; c++){
                tileButtons[r][c] = new JButton();
                JButton btn = tileButtons[r][c];

                btn.setFont(new Font("Arial", Font.BOLD, 24));
                btn.setContentAreaFilled(false);
                btn.setOpaque(true);
                btn.setBackground(Color.BLACK);
                btn.setFocusPainted(false);
                btn.putClientProperty("row", r);
                btn.putClientProperty("col", c);
                btn.addActionListener(new TileController(game, tileButtons, this));
                if(game.tiles[r][c].isEmpty()){
                    btn.setText("");
                } else {
                    btn.setText(Integer.toString(game.tiles[r][c].getValue()));
                    setColor(btn, game.tiles[r][c].getValue());
                }

                pnlGame.add(btn);
            }
        }
    }

    // helper method used to build info panel
    private void buildInfoPanel(){
        pnlInfo = new JPanel();
        pnlInfo.setLayout(new GridLayout(1, 2));

        buildQueuePanel();
        buildControlInfoPanel();

        pnlInfo.add(pnlQueue);
        pnlInfo.add(pnlControlInfo);
    }

    // helper method used to build queue panel
    private void buildQueuePanel(){
        pnlQueue = new JPanel();
        pnlQueue.setLayout(new GridLayout(8, 1));

        // adds title label to panel
        JLabel lblQueue = new JLabel("QUEUE:", SwingConstants.CENTER);
        lblQueue.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlQueue.add(lblQueue);

        //Creates button to refresh queue 1 time
        JPanel pnlRefresh = new JPanel();
        JButton btnRefreshQueue = new JButton("Refresh Queue");
        btnRefreshQueue.setFocusPainted(false);
        btnRefreshQueue.setBackground(Color.black);
        btnRefreshQueue.setForeground(new Color(76,150,236));
        btnRefreshQueue.setFont(new Font("Arial", Font.PLAIN, 20));
        btnRefreshQueue.setContentAreaFilled(false);
        btnRefreshQueue.setOpaque(true);
        btnRefreshQueue.addActionListener(actionEvent -> {
            game.queue.newQueue();
            btnRefreshQueue.setEnabled(false);
        });
        pnlRefresh.add(btnRefreshQueue);

        // instantiates array of queue labels & adds to panel
        queueLabels = new JLabel[5];
        int[] temp = game.queue.getQueue();
        for(int i = 0; i < 5; i++){
            JPanel pnl = new JPanel();
            queueLabels[i] = new JLabel(Integer.toString(temp[i]), SwingConstants.CENTER);
            queueLabels[i].setFont(new Font("Arial", Font.BOLD, 30));
            queueLabels[i].setPreferredSize(new Dimension(70, 70));
            queueLabels[i].setOpaque(true);
            queueLabels[i].setBackground(Color.BLACK);
            setColor(queueLabels[i], temp[i]);
            pnl.add(queueLabels[i]);
            pnlQueue.add(pnl);
        }
        pnlQueue.add(pnlRefresh);

        /*Creates button to remove 1 number from the board entirely*/
        //Creates button to refresh queue 1 time
        JPanel pnlRemove = new JPanel();
        JButton btnRemoveNumber = new JButton("Magic Trick");
        btnRemoveNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        btnRemoveNumber.setContentAreaFilled(false);
        btnRemoveNumber.setOpaque(true);
        System.out.println("Inside Board Class: "+game.isMagicTrick);

        btnRemoveNumber.setEnabled(true);
        btnRemoveNumber.setText("Magic Trick");
        btnRemoveNumber.setFocusPainted(false);
        btnRemoveNumber.setBackground(Color.black);
        btnRemoveNumber.setForeground(new Color(76,150,236));

        btnRemoveNumber.addActionListener(e -> {

            //if the trick is not -1 then it has not been used and we can load a trick
            if(game.isMagicTrick!=-1) {
                game.isMagicTrick = 1;
                for (int i=0; i<9; i++){
                    for(int j=0; j<9; j++){
                        if(game.tiles[i][j].isEmpty()){
                            tileButtons[i][j].removeNotify();
                        }
                    }
                }
            }


            btnRemoveNumber.setEnabled(false);
            //btnRemoveNumber.setText("Magic Trick");

        });

        pnlRemove.add(btnRemoveNumber);
        pnlQueue.add(pnlRemove);

    }

    // helper method used to build control info panel
    private void buildControlInfoPanel(){
        pnlControlInfo = new JPanel();
        pnlControlInfo.setLayout(new GridLayout(2, 1));

        buildGameDataPanel();
        buildButtonsPanel();

        pnlControlInfo.add(pnlGameData);
        pnlControlInfo.add(pnlButtons);
    }

    // helper method used to build game data panel
    private void buildGameDataPanel(){
        pnlGameData = new JPanel();
        pnlGameData.setLayout(new GridLayout(2, 1));

        buildScorePanel();
        buildDurationPanel();

        pnlGameData.add(pnlScore);
        pnlGameData.add(pnlDuration);
    }

    // helper method used to build score panel
    private void buildScorePanel(){
        pnlScore = new JPanel();
        pnlScore.setLayout(new GridLayout(2,1));
        GridBagConstraints gbc = new GridBagConstraints();

        // creates title label and adds to panel
        JLabel lblScoreDesc = new JLabel("SCORE: ", SwingConstants.CENTER);
        lblScoreDesc.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlScore.add(lblScoreDesc, gbc);

        // instantiates score label and adds to panel
        lblScore = new JLabel(Integer.toString(game.score.getScore()), SwingConstants.CENTER);
        lblScore.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlScore.add(lblScore, gbc);

        JLabel lblPtsEarned = new JLabel("PTS EARNED: " , SwingConstants.CENTER);
        lblPtsEarned.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlScore.add(lblPtsEarned, gbc);

        ptsEarned = new JLabel("+" + Integer.toString(game.score.getAddition()), SwingConstants.CENTER);
        ptsEarned.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlScore.add(ptsEarned, gbc);

    }

    // helper method used to build duration panel
    private void buildDurationPanel(){
        pnlDuration = new JPanel();
        pnlDuration.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // instantiates title label and adds to panel
        lblDurationDesc = new JLabel("", SwingConstants.CENTER);
        lblDurationDesc.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlDuration.add(lblDurationDesc);

        // instantiates duration label and adds to panel
        lblDuration = new JLabel("", SwingConstants.CENTER);
        lblDuration.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlDuration.add(lblDuration);
    }

    // abstract method used to update duration panel components to be used w/ the UntimedBoard or TimedBoard
    public abstract void updateDurationPanel();

    // helper method used to build buttons panel
    private void buildButtonsPanel(){
        pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(3, 1));

        // instantiates hint button and adds to panel
        JPanel pnlHint = new JPanel();
        btnHint = new JButton();
        btnHint.setText("Hint");
        btnHint.setFocusPainted(false);
        btnHint.setBackground(Color.black);
        btnHint.setForeground(new Color(76,150,236));
        btnHint.setFont(new Font("Arial", Font.PLAIN, 20));
        btnHint.setContentAreaFilled(false);
        btnHint.setOpaque(true);
        btnHint.setMnemonic(KeyEvent.VK_Z);
        btnHint.addActionListener(e -> {
            JButton btn = (JButton) e.getSource();
            //tileButtons[row][col].setBackground(new Color(76,150,236));
        });
        pnlHint.add(btnHint);
        pnlButtons.add(pnlHint);

        // instantiates new game button and adds to panel
        JPanel pnlNewGame = new JPanel();
        btnNewGame = new JButton();
        btnNewGame.setText("New Game");
        btnNewGame.setFocusPainted(false);
        btnNewGame.setBackground(Color.black);
        btnNewGame.setForeground(new Color(76,150,236));
        btnNewGame.setFont(new Font("Arial", Font.PLAIN, 20));
        btnNewGame.setContentAreaFilled(false);
        btnNewGame.setOpaque(true);
        btnNewGame.addActionListener(e -> {
            JButton btn = (JButton) e.getSource();
            Application app = (Application) btn.getRootPane().getParent();
            Menu menu = new Menu(Menu.GAME_TYPE_MENU);
            app.updateMasterPanel(menu.retrieveMasterPanel());
        });
        pnlNewGame.add(btnNewGame);
        pnlButtons.add(pnlNewGame);

        // instantiates exit game button and adds to panel
        JPanel pnlExitGame = new JPanel();
        JButton btnExitGame = new JButton();
        btnExitGame.setText("Exit");
        btnExitGame.setFocusPainted(false);
        btnExitGame.setBackground(Color.black);
        btnExitGame.setForeground(new Color(76,150,236));
        btnExitGame.setFont(new Font("Arial", Font.PLAIN, 20));
        btnExitGame.setContentAreaFilled(false);
        btnExitGame.setOpaque(true);
        btnExitGame.addActionListener(e -> {
            System.exit(0);
        });
        pnlExitGame.add(btnExitGame);
        pnlButtons.add(pnlExitGame);
    }

    // private helper method
    // sets text color of JButton depending on value of corresponding tile
    private <T extends JComponent> void setColor(T comp, int value){
        switch(value){
            case 0: comp.setForeground(new Color(200, 200, 200));
                    break;
            case 1: comp.setForeground(new Color(255, 0, 0));
                    break;
            case 2: comp.setForeground(new Color(255, 120, 0));
                    break;
            case 3: comp.setForeground(new Color(255, 255, 0));
                    break;
            case 4: comp.setForeground(new Color(0, 130, 0));
                    break;
            case 5: comp.setForeground(new Color(0, 255, 0));
                    break;
            case 6: comp.setForeground(new Color(0, 0, 255));
                    break;
            case 7: comp.setForeground(new Color(0, 255, 255));
                    break;
            case 8: comp.setForeground(new Color(255, 50, 150));
                    break;
            case 9: comp.setForeground(new Color(150, 0, 150));
                    break;
        }
    }

    // automatically updates view components after model(s) have changed
    public void update(Observable o, Object arg){
        // updates text of corresponding JButton to value contained in tile object
        if(o instanceof Tile){
            Tile tile = (Tile) o;
            int[] coord = tile.getCoordinates();
            JButton btn = tileButtons[coord[0]][coord[1]];
            if(tile.isEmpty()){
                btn.setText("");
                if(game.isMagicTrick==1){
                    btn.setEnabled(false);
                }
            } else {
                btn.setText(Integer.toString(tile.getValue()));
                setColor(btn, tile.getValue());
            }
        }

        // updates JLabels representing queue w/ corresponding values of queue object
        if(o instanceof Queue){

            int[] newQueue = game.queue.getQueue();

            for(int i = 0; i < newQueue.length; i++){
                queueLabels[i].setText(Integer.toString(newQueue[i]));
                setColor(queueLabels[i], newQueue[i]);
            }
        }

        // updates JLabel containing score w/ corresponding value in score object
        if(o instanceof Score){
            lblScore.setText(Integer.toString(game.score.getScore()));
            ptsEarned.setText("+" + Integer.toString(game.score.getAddition()));
        }

        // updates JLabel containing moves left w/ corresponding value in movesLeft object
        if(o instanceof MovesLeft){
            lblDuration.setText(Integer.toString(game.movesLeft.getMovesLeft()));
        }

        // updates JLabel containing timer w/ corresponding value in timer object
        if(o instanceof GameTimer){
            lblDuration.setText(game.gameTimer.getTimeRemaining());
        }

        // updates hint button
        // hint button is disabled if arg is < 1; else it is enabled
        if(o instanceof Game){
            /*
            if((int) arg < 1){
                btnHint.setEnabled(false);
            } else {
                // only re-enables hint button if hints are left
                if(numHints > 0){
                    btnHint.setEnabled(true);
                }
            }
            */
            // resets background colors of JButtons
            for(int r = 0; r < 9; r++){
                for(int c = 0; c < 9; c++){
                    tileButtons[r][c].setBackground(Color.BLACK);
                }
            }
        }
    }

}
