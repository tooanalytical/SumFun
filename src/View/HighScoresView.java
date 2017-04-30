package View;

import Model.HighScoresModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observer;

public class HighScoresView extends JDialog implements Observer {

    private final int WINDOW_WIDTH = 1300;
    private final int WINDOW_HEIGHT = 700;
    private HighScoresModel highScores;
    private final String pointsScore;
    private final String timeScore;
    private boolean pHighScore;
    private boolean tHighScore;
    private LocalDateTime now;

    private JPanel pnlMaster;

    public HighScoresView(HighScoresModel highScores, String pointsScore, String timeScore, LocalDateTime now){
        super();
        this.highScores = highScores;
        this.pHighScore = highScores.checkHighScore("p", pointsScore);
        this.tHighScore = highScores.checkHighScore("t", timeScore);
        this.pointsScore = pointsScore;
        this.timeScore = timeScore;
        this.now = now;

        setTitle("Sum Fun High Scores");
        ImageIcon logo = new ImageIcon("SumFunIcon.png");
        setIconImage(logo.getImage());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);

        highScores.addObserver(this);
        createMasterPanel();
        add(pnlMaster);

        setVisible(true);
    }

    // private helper method used to create master panel
    private void createMasterPanel(){
        pnlMaster = new JPanel();
        pnlMaster.setLayout(new GridLayout(1, 2));

        pnlMaster.add(createHighScorePanel("p", pHighScore));
        pnlMaster.add(createHighScorePanel("t", tHighScore));
    }

    // private helper method used to create most points panel
    private JPanel createHighScorePanel(String type, boolean newHighScore){
        JPanel pnlHighScore = new JPanel();
        pnlHighScore.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        if(newHighScore){
            pnlHighScore.setLayout(new GridLayout(12, 1));
        } else {
            pnlHighScore.setLayout(new GridLayout(11, 1));
        }

        // row 1 -- title panel
        JPanel pnlTitle = new JPanel();
        //pnlTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel lblTitle = new JLabel("", SwingConstants.CENTER);
        if(type.equals("p")){
            //lblTitle.setText("Top Ten Most Points");
            lblTitle.setText("<HTML><U>Top Ten Most Points</U></HTML>");
        } else if(type.equals("t")){
            //lblTitle.setText("Top Ten Least Time");
            lblTitle.setText("<HTML><U>Top Ten Least Time</U></HTML>");
        }
        lblTitle.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        pnlTitle.add(lblTitle);
        pnlHighScore.add(pnlTitle);

        // gets up-to-date high scores
        ArrayList<String[]> scores;
        if(type.equals("p")){
            scores = highScores.getMostPoints();
        } else {
            scores = highScores.getLeastTime();
        }
        int numScores = scores.size();

        // row 2 through row 10 -- high score panels
        for(int i = 0; i < 10; i++){
            String date = "";
            String name = "";
            String score = "";

            if(numScores - 1 >= i){
                String[] arr = scores.get(i);
                date = arr[0];
                name = arr[1];
                score = arr[2];
            }

            pnlHighScore.add(createScorePanel(date, name, score));
        }

        // row 11 -- edit most points high scores (optional)
        if(newHighScore){
            JPanel pnlNewHighScore = new JPanel();
            //pnlNewHighScore.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel lblEnterName = new JLabel("Please enter name:");
            lblEnterName.setFont(new Font("Arial", Font.BOLD, 20));
            lblEnterName.setVerticalAlignment(SwingConstants.CENTER);
            lblEnterName.setHorizontalAlignment(SwingConstants.CENTER);
            //lblEnterName.setPreferredSize(new Dimension(200, 50));
            pnlNewHighScore.add(lblEnterName);
            JTextField txtEnterName = new JTextField();
            txtEnterName.setPreferredSize(new Dimension(100, 30));
            pnlNewHighScore.add(txtEnterName);
            JButton btnEnterName = new JButton("Enter");
            btnEnterName.setFont(new Font("Arial", Font.BOLD, 20));
            btnEnterName.setContentAreaFilled(false);
            btnEnterName.setOpaque(true);
            btnEnterName.setForeground(Color.WHITE);
            btnEnterName.setBackground(Color.BLACK);
            btnEnterName.setFocusPainted(false);
            btnEnterName.addActionListener(e -> {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
                if(type.equals("p")){
                    highScores.updateHighScores(type, dtf.format(now), txtEnterName.getText().replaceAll(",", ""), pointsScore);
                }
                if(type.equals("t")){
                    highScores.updateHighScores(type, dtf.format(now), txtEnterName.getText().replaceAll(",", ""), timeScore);
                }
                btnEnterName.setEnabled(false);
            });
            pnlNewHighScore.add(btnEnterName);
            /*
            JLabel lblError = new JLabel();
            lblError.setFont(new Font("Arial", Font.BOLD, 20));
            lblError.setVerticalAlignment(SwingConstants.CENTER);
            lblError.setHorizontalAlignment(SwingConstants.CENTER);
            lblError.setForeground(Color.RED);
            pnlNewHighScore.add(lblError);
            */
            pnlHighScore.add(pnlNewHighScore);

        }

        return pnlHighScore;
    }

    // private helper method used in the creation of the points & time panels
    private JPanel createScorePanel(String date, String name, String score){
        JPanel pnl = new JPanel();
        //pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        String[] args = {date, name, score};

        for(int i = 0; i < 3; i++) {
            JLabel lbl = new JLabel(args[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 20));
            lbl.setVerticalAlignment(SwingConstants.CENTER);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(200, 50));
            pnl.add(lbl);
        }

        return pnl;
    }

    public void update(java.util.Observable o, Object arg){
        if(arg.equals("p")){
            pHighScore = false;
        }
        if(arg.equals("t")){
            tHighScore = false;
        }
        remove(pnlMaster);
        createMasterPanel();
        add(pnlMaster);
        revalidate();
    }

}
