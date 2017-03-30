package View;

import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;
import Model.*;
import Control.*;

// update method needs finished
// board design needs cleaned up & made "prettier"
public class UntimedBoard extends JFrame implements Observer {

    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 800;
    private final int numRows, numColumns;

    private JPanel pnlMaster, pnlGame, pnlInfo;
    private JButton[][] tileButtons;
    private JLabel[] queueLabels;
    private JLabel lblScore, lblMovesLeft;

    private Tile[][] tiles;
    private Queue queue;
    private Score score;
    private MovesLeft movesLeft;

    public UntimedBoard(int numRows, int numColumns, Tile[][] tiles, Queue queue, Score score, MovesLeft movesLeft){
        super();
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.tiles = tiles;
        this.queue = queue;
        this.score = score;
        this.movesLeft = movesLeft;
        addObservers();

        setTitle("Sum Fun!");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        buildMasterPanel();
        add(pnlMaster);
        setVisible(true);
    }

    private void addObservers(){
        for(Tile[] row : tiles){
            for(Tile tile : row){
                tile.addObserver(this);
            }
        }
        queue.addObserver(this);
        score.addObserver(this);
        movesLeft.addObserver(this);
    }

    private void buildMasterPanel(){
        buildGamePanel();
        buildInfoPanel();

        pnlMaster = new JPanel();
        pnlMaster.setLayout(new GridLayout(1, 2));

        pnlMaster.add(pnlGame);
        pnlMaster.add(pnlInfo);
    }

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
                btn.addActionListener(new TileListener(tiles, queue, score, movesLeft));
                if(tiles[r][c].isEmpty()){
                    btn.setText("");
                }
                else{
                    btn.setText(Integer.toString(tiles[r][c].getValue()));
                }

                pnlGame.add(btn);
            }
        }
    }

    // this method needs cleaned up
    private void buildInfoPanel(){
        pnlInfo = new JPanel();
        pnlInfo.setLayout(new GridLayout(1, 2));
        queueLabels = new JLabel[5];
        lblScore = new JLabel(Integer.toString(score.getScore()), SwingConstants.CENTER);
        lblScore.setFont(new Font("Arial", Font.PLAIN, 20));
        lblMovesLeft = new JLabel(Integer.toString(movesLeft.getMovesLeft()), SwingConstants.CENTER);
        lblMovesLeft.setFont(new Font("Arial", Font.PLAIN, 20));

        // creates queue panel & adds panel to info panel
        // instantiates array of queue labels & adds queue labels to queue panel
        JPanel pnlQueue = new JPanel();
        pnlQueue.setLayout(new GridLayout(6, 1));
        JLabel lblQueue = new JLabel("QUEUE:", SwingConstants.CENTER);
        lblQueue.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlQueue.add(lblQueue);
        int[] temp = queue.getQueue();
        for(int i = 0; i < 5; i++){
            queueLabels[i] = new JLabel(Integer.toString(temp[i]), SwingConstants.CENTER);
            queueLabels[i].setFont(new Font("Arial", Font.BOLD, 30));
            pnlQueue.add(queueLabels[i]);
        }
        pnlInfo.add(pnlQueue);

        // creates scoremoves panel & adds panel to info panel
        // creates score panel & moves panel & adds both panels to scoremoves panel
        JPanel pnlScoreMoves = new JPanel();
        pnlScoreMoves.setLayout(new GridLayout(2, 1));
        JPanel pnlScore = new JPanel();
        pnlScore.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel lblScoreDesc = new JLabel("SCORE: ", SwingConstants.CENTER);
        lblScoreDesc.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlScore.add(lblScoreDesc, gbc);
        pnlScore.add(lblScore, gbc);
        pnlScoreMoves.add(pnlScore);
        JPanel pnlMoves = new JPanel();
        pnlMoves.setLayout(new GridBagLayout());
        JLabel lblMovesDesc = new JLabel("MOVES LEFT: ", SwingConstants.CENTER);
        lblMovesDesc.setFont(new Font("Arial", Font.PLAIN, 20));
        pnlMoves.add(lblMovesDesc, gbc);
        pnlMoves.add(lblMovesLeft, gbc);
        pnlScoreMoves.add(pnlMoves);
        pnlInfo.add(pnlScoreMoves);

    }

    // automatically updates view components after model(s) have changed
    public void update(Observable o, Object arg){
        if(o instanceof Tile){

        }

        if(o instanceof Queue){

        }

        if(o instanceof Score){
            lblScore.setText(Integer.toString(Score.getScore()));
        }

        if(o instanceof MovesLeft){
            lblMovesLeft.setText(Integer.toString(MovesLeft.getMovesLeft()));
        }
    }
}
