package View;

import Model.HighScoresModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


public class HighScoresView extends JDialog implements Observer {

    private final int Window_Width = 1300;
    private final int Window_Height = 700;
    private HighScoresModel highScores;
    private final String pointsScore;
    private final String timeScore;
    private boolean pHighScore;
    private boolean tHighScore;
    private LocalDateTime now;

    private JPanel pnlMaster;
    private Color backColor;

    public HighScoresView(HighScoresModel highScores, String pointsScore,
                          String timeScore, LocalDateTime now){
        super();
        this.highScores = highScores;
        this.pHighScore = highScores.checkHighScore("p", pointsScore);
        this.tHighScore = highScores.checkHighScore("t", timeScore);
        this.pointsScore = pointsScore;
        this.timeScore = timeScore;
        this.now = now;
        this.backColor = new Color(255, 224, 212);

        setTitle("Sum Fun High Scores");
        ImageIcon logo = new ImageIcon("SumFunIcon.png");
        setIconImage(logo.getImage());
        setSize(Window_Width, Window_Height);
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
        pnlHighScore.setBackground(backColor);
        if(newHighScore){
            pnlHighScore.setLayout(new GridLayout(12, 1));
        } else {
            pnlHighScore.setLayout(new GridLayout(11, 1));
        }

        // row 1 -- title panel
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBackground(backColor);
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
            pnlNewHighScore.setBackground(backColor);
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
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
                        "MM/dd/yyyy HH:mm");
                if(type.equals("p")){
                    highScores.updateHighScores(type, dtf.format(now),
                            txtEnterName.getText().replaceAll(",", ""),
                            pointsScore);
                }
                if(type.equals("t")){
                    highScores.updateHighScores(type, dtf.format(now),
                            txtEnterName.getText().replaceAll(",", ""),
                            timeScore);
                }
                btnEnterName.setEnabled(false);
            });
            pnlNewHighScore.add(btnEnterName);
            pnlHighScore.add(pnlNewHighScore);

        }

        return pnlHighScore;
    }

    // private helper method used in the creation of the points & time panels
    private JPanel createScorePanel(String date, String name, String score){
        JPanel pnl = new JPanel();
        //pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnl.setBackground(backColor);
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
