package NumberGuess;



import javax.swing.*;
import java.awt.*;

public class GamePlayUi extends JFrame {

    private final int target = (int)(Math.random()*100) + 1;

    private JTextField guessField;
    private JTextField decisionField;
    private JLabel attemptLabel;
    private int currentAttempt = 0;
    private int maxAttempt;

    public GamePlayUi(UserModeDto dto, int maxAttempt) {

        this.maxAttempt = maxAttempt;

        setTitle("Guess The Number !");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= TOP =================
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(30,144,255));
        JLabel heading = new JLabel("- Number Guess -");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial",Font.BOLD,19));
        topPanel.add(heading);
        add(topPanel,BorderLayout.NORTH);

        // ================= CENTER =================
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.WHITE);

        attemptLabel = new JLabel();
        attemptLabel.setBounds(180,50,200,30);
        updateAttemptLabel();
        centerPanel.add(attemptLabel);

        guessField = new JTextField();
        guessField.setBounds(150,120,200,40);
        centerPanel.add(guessField);

        JButton guessButton = new JButton("Guess");
        guessButton.setBounds(200,180,100,35);
        centerPanel.add(guessButton);

        decisionField = new JTextField("Guess number between 1 to 100");
        decisionField.setBounds(100,240,300,40);
        decisionField.setEditable(false);
        decisionField.setHorizontalAlignment(JTextField.CENTER);
        centerPanel.add(decisionField);

        guessButton.addActionListener(e -> processGuess(dto, guessButton));

        add(centerPanel,BorderLayout.CENTER);

        setVisible(true);
    }

    private void processGuess(UserModeDto dto, JButton guessButton) {

        try {
            int guess = ConverTONum(guessField.getText());
            if(guess==-404) throw new Exception();
            System.out.println(guess);
            currentAttempt++;


            if(guess == target) {
                decisionField.setText("🎉 Correct!");
                decisionField.setBackground(new Color(0xFF749157, true));
                dto.setTotalMatchWon(dto.getTotalMatchWon()+1);
                guessButton.setEnabled(false);
                dto.setTotalMatchPlayed(dto.getTotalMatchPlayed()+currentAttempt);
                Integer accurate=(dto.getTotalMatchWon()*100)/dto.getTotalMatchPlayed();
                dto.setAccuracy(accurate);
                Timer timer = new Timer(2500, event -> {
                    new InputUi().Storing_Input(dto);
                    dispose();
                });
                timer.setRepeats(false);
                timer.start();


            }
            else if(guess < target) {
                decisionField.setText("⬆ Too Low!");
                decisionField.setBackground(Color.ORANGE);
            }
            else {
                decisionField.setText("⬇ Too High!");
                decisionField.setBackground(Color.ORANGE);
            }

            if(maxAttempt != -1 && currentAttempt >= maxAttempt && guess != target){
                decisionField.setText("❌ Game Over! " +"Target is "+target);
                decisionField.setBackground(Color.RED);
                guessButton.setEnabled(false);
                dto.setTotalMatchPlayed(dto.getTotalMatchPlayed()+currentAttempt);
                Integer accurate=(dto.getTotalMatchWon()*100)/dto.getTotalMatchPlayed();
                dto.setAccuracy(accurate);


                Timer timer = new Timer(2500, event -> {
                    new InputUi().Storing_Input(dto);
                    dispose();
                });

                timer.setRepeats(false);
                timer.start();
            }

            updateAttemptLabel();

        } catch(Exception ex){
            decisionField.setText("Enter valid number!");

            decisionField.setBackground(new Color(0xDC1555));
        }

    }

    private int ConverTONum(String text) {
        int no=0;
        for(char c:text.toCharArray()){
            no*=10;
            int num=(int)c;
            if(num<48 || num>57) return -404;
            no+=num-48;


        }return no;
    }

    private void updateAttemptLabel() {

        if(maxAttempt == -1){
            attemptLabel.setText("Attempts Used: " + currentAttempt);
        } else {
            attemptLabel.setText("Remaining: " + (maxAttempt - currentAttempt));
        }
    }
}