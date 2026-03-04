package NumberGuess;

import javax.swing.*;
import java.awt.*;

public class InputUi extends JFrame {

    private JTextField usernameField;
    private JTextField wonField;
    private JTextField playedField;
    private JTextField scoreField;
    private JComboBox<GameMode> gameModeBox;
    private JTextField MaxAttempt;



    public void Storing_Input(UserModeDto userModeDto) {

        setTitle("Player Dashboard");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(30, 144, 255));
        JLabel heading = new JLabel("Player Game Information");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(heading);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        centerPanel.setBackground(new Color(245, 245, 245));



        if(userModeDto.getUsername()==null)        usernameField = new JTextField();
        else {
            usernameField = new JTextField(userModeDto.getUsername());
            usernameField.setEditable(false);
        }

        wonField = new JTextField(String.valueOf(userModeDto.getTotalMatchWon()));
        wonField.setEditable(false);
        playedField = new JTextField(String.valueOf(userModeDto.getTotalMatchPlayed()));
        playedField.setEditable(false);
        scoreField = new JTextField(String.valueOf(userModeDto.getAccuracy()));
        scoreField.setEditable(false);
        gameModeBox = new JComboBox<>(GameMode.values());

        centerPanel.add(new JLabel("Username:"));
        centerPanel.add(usernameField);

        centerPanel.add(new JLabel("Total Match Won:"));
        centerPanel.add(wonField);

        centerPanel.add(new JLabel("Total Match Played:"));
        centerPanel.add(playedField);

        centerPanel.add(new JLabel("Accuracy(%):"));
        centerPanel.add(scoreField);

        centerPanel.add(new JLabel("Game Mode:"));
        centerPanel.add(gameModeBox);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(220, 220, 220));

        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(e -> {

            savePlayer(userModeDto);

            int maxAttempt;

            if(userModeDto.getGameMode().equals(GameMode.Easy)) {

                maxAttempt = -1; // unlimited

            } else if(userModeDto.getGameMode().equals(GameMode.Challenge)) {

                String input = JOptionPane.showInputDialog(
                        this,
                        "Enter Maximum Attempt:"
                );

                try {
                    maxAttempt = Integer.parseInt(input.trim());
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number!");
                    return;
                }

            } else {

                maxAttempt = 1;
            }

            new GamePlayUi(userModeDto, maxAttempt);
            dispose();
        });
        exitButton.addActionListener(e -> System.exit(0));


        bottomPanel.add(startButton);
        bottomPanel.add(exitButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void savePlayer(UserModeDto userModeDto) {
        try {
            String username = usernameField.getText();

            Integer won = Integer.parseInt(wonField.getText());
            Integer played = Integer.parseInt(playedField.getText());
            Integer score = Integer.parseInt(scoreField.getText());
            GameMode mode = (GameMode) gameModeBox.getSelectedItem();

            userModeDto.setUsername(username);
            userModeDto.setGameMode(mode);


            JOptionPane.showMessageDialog(this, "Player Saved Successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input!");
        }
    }
}