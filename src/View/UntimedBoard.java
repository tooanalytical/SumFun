package View;

import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;
import Model.*;

// queue takes up too much of the window
// need to add score, moves left, and other components
public class UntimedBoard extends JFrame implements Observer {

    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 800;
    private final int numRows, numColumns;

    private JPanel pnlMaster, pnlGame, pnlQueue;
    private JButton[][] tileButtons;
    private JLabel[] queueLabels;

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
        tileButtons = new JButton[numRows][numColumns];
        queueLabels = new JLabel[5];
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
        buildQueuePanel();

        pnlMaster = new JPanel();
        pnlMaster.setLayout(new GridLayout(1, 2));

        pnlMaster.add(pnlGame);
        pnlMaster.add(pnlQueue);
    }

    private void buildGamePanel(){
        pnlGame = new JPanel();
        pnlGame.setLayout(new GridLayout(numRows, numColumns));

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
                // sets action listener
                if(tiles[r][c].isEmpty()){
                    btn.setText("");
                }
                else{
                    btn.setText(Integer.toString(tiles[r][c].value()));
                }

                pnlGame.add(btn);
            }
        }
    }

    private void buildQueuePanel(){
        pnlQueue = new JPanel();
        pnlQueue.setLayout(new GridLayout(5, 1));

        // instantiates array of queue labels & adds queue labels to panel
        int[] temp = queue.getQueue();
        for(int i = 0; i < 5; i++){
            queueLabels[i] = new JLabel(Integer.toString(temp[i]), SwingConstants.CENTER);
            queueLabels[i].setFont(new Font("Arial", Font.BOLD, 30));
            pnlQueue.add(queueLabels[i]);
        }
    }

    public void update(Observable o, Object arg){
        if(o instanceof Tile){

        }

        if(o instanceof Queue){

        }

        if(o instanceof Score){

        }

        if(o instanceof MovesLeft){

        }
    }
}
