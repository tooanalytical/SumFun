package sumfun;

import java.awt.*;
import javax.swing.*;

// queue takes up too much of the window
// need to add score, moves left, and other components
public class Board extends JFrame {

    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 800;
    private final int numRows;
    private final int numColumns;

    private JPanel pnlMaster;
    private JPanel pnlGame;
    private JPanel pnlQueue;

    private JLabel [] queue;
    private Game game;

    public Board(int numRows, int numColumns){
        queue = new JLabel[5];
        game = new Game(numRows, numColumns);
        this.numRows = numRows;
        this.numColumns = numColumns;

        setTitle("Sum Fun!");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        buildMasterPanel();
        add(pnlMaster);
        setVisible(true);
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

        // adds tiles to panel
        for(Tile[] row : game.tiles){
            for(Tile tile : row){
                pnlGame.add(tile);
            }
        }
    }

    private void buildQueuePanel(){
        pnlQueue = new JPanel();
        pnlQueue.setLayout(new GridLayout(5, 1));

        // instantiates array of labels which represent queue
        int[] temp = game.queue.getQueue();
        for(int i = 0; i < 5; i++){
            queue[i] = new JLabel(Integer.toString(temp[i]), SwingConstants.CENTER);
            queue[i].setFont(new Font("Arial", Font.BOLD, 30));
        }

        // adds queue labels to panel
        for(JLabel lbl : queue){
            pnlQueue.add(lbl);
        }
    }

}
