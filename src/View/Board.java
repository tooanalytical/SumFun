package View;

import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;
import Model.*;
import Control.*;

// creates JPanel, pnlMaster, which represents either UntimedBoard or TimedBoard
public abstract class Board implements Observer{

    private final int numRows, numColumns;

    // reference to main JFrame
    private Application app;

    // important components contained in master panel
    private JPanel pnlMaster, pnlGame, pnlInfo, pnlQueue, pnlControlInfo, pnlGameData, pnlScore, pnlDuration, pnlButtons;
    private JButton[][] tileButtons;
    private JButton btnNewGame, btnExitGame;
    private JLabel[] queueLabels;
    private JLabel lblScore;
    protected JLabel lblDurationDesc, lblDuration;

    protected Game game;

    public Board(Game game){
        this.numRows = game.getNumRows();
        this.numColumns = game.getNumColumns();
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
        pnlMaster.setLayout(new GridLayout(1, 2));

        pnlMaster.add(pnlGame);
        pnlMaster.add(pnlInfo);
    }

    // helper method used to build game panel
    private void buildGamePanel(){
        pnlGame = new JPanel();
        pnlGame.setLayout(new GridLayout(numRows, numColumns));
        tileButtons = new JButton[numRows][numColumns];

        // instantiates two-dimensional array of tile buttons & adds tile buttons to panel
        for(int r = 0; r < numRows; r++){
            for(int c = 0; c < numColumns; c++){
                tileButtons[r][c] = new JButton();
                JButton btn = tileButtons[r][c];

                btn.setFont(new Font("Arial", Font.PLAIN, 24));
                btn.setContentAreaFilled(false);
                btn.setOpaque(true);
                btn.putClientProperty("row", r);
                btn.putClientProperty("col", c);
                btn.addActionListener(new TileListener(game));
                if(game.tiles[r][c].isEmpty()){
                    btn.setText("");
                }
                else{
                    btn.setText(Integer.toString(game.tiles[r][c].getValue()));
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
        pnlQueue.setLayout(new GridLayout(7, 1));

        // adds title label to panel
        JLabel lblQueue = new JLabel("QUEUE:", SwingConstants.CENTER);
        lblQueue.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlQueue.add(lblQueue);

        //Creates button to refresh queue 1 time
        JPanel pnlRefresh = new JPanel();
        JButton btnRefreshQueue = new JButton("Refresh Queue");
        btnRefreshQueue.setFont(new Font("Arial", Font.PLAIN, 20));
        btnRefreshQueue.setContentAreaFilled(false);
        btnRefreshQueue.setOpaque(true);
        btnRefreshQueue.addActionListener(actionEvent -> {
            game.queue.newQueue();
            btnRefreshQueue.setEnabled(false);
            btnRefreshQueue.setText("No Remaining Refreshes");
        });
        pnlRefresh.add(btnRefreshQueue);

        // instantiates array of queue labels & adds to panel
        queueLabels = new JLabel[5];
        int[] temp = game.queue.getQueue();
        for(int i = 0; i < 5; i++){
            queueLabels[i] = new JLabel(Integer.toString(temp[i]), SwingConstants.CENTER);
            queueLabels[i].setFont(new Font("Arial", Font.BOLD, 30));
            pnlQueue.add(queueLabels[i]);
        }
        pnlQueue.add(pnlRefresh);
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
        pnlScore.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // creates title label and adds to panel
        JLabel lblScoreDesc = new JLabel("SCORE: ", SwingConstants.CENTER);
        lblScoreDesc.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlScore.add(lblScoreDesc, gbc);

        // instantiates score label and adds to panel
        lblScore = new JLabel(Integer.toString(game.score.getScore()), SwingConstants.CENTER);
        lblScore.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlScore.add(lblScore, gbc);
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
        pnlButtons.setLayout(new GridLayout(2, 1));

        // instantiates new game button and adds to panel
        JPanel pnlNewGame = new JPanel();
        btnNewGame = new JButton();
        btnNewGame.setText("New Game");
        btnNewGame.setFont(new Font("Arial", Font.PLAIN, 20));
        btnNewGame.setContentAreaFilled(false);
        btnNewGame.setOpaque(true);
        btnNewGame.addActionListener(new MenuListener(game));
        pnlNewGame.add(btnNewGame);
        pnlButtons.add(pnlNewGame);

        // instantiates exit game button and adds to panel
        JPanel pnlExitGame = new JPanel();
        btnExitGame = new JButton();
        btnExitGame.setText("Exit");
        btnExitGame.setFont(new Font("Arial", Font.PLAIN, 20));
        btnExitGame.setContentAreaFilled(false);
        btnExitGame.setOpaque(true);
        btnExitGame.addActionListener(new MenuListener(game));
        pnlExitGame.add(btnExitGame);
        pnlButtons.add(pnlExitGame);
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
            }
            else{
                btn.setText(Integer.toString(tile.getValue()));
            }
        }

        // updates JLabels representing queue w/ corresponding values of queue object
        if(o instanceof Queue){

            int[] newQueue = game.queue.getQueue();

            for(int i = 0; i < newQueue.length; i++){
                queueLabels[i].setText(Integer.toString(newQueue[i]));
            }
        }

        // updates JLabel containing score w/ corresponding value in score object
        if(o instanceof Score){
            lblScore.setText(Integer.toString(game.score.getScore()));
        }

        // updates JLabel containing moves left w/ corresponding value in movesLeft object
        if(o instanceof MovesLeft){
            lblDuration.setText(Integer.toString(game.movesLeft.getMovesLeft()));
        }

        /*
        // updates JLabel containing timer w/ corresponding value in timer object
        if(o instanceof Timer){
            lblDuration.setText("uses method from timer object to populate label");
        }
        */
    }

}
