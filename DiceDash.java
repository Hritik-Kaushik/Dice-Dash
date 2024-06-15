import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceDash extends JFrame {
    JPanel player1RollPanel;
    JPanel player1DicePanel;
    JPanel scoreSheetPanel;
    JPanel player2DicePanel;
    JPanel player2RollPanel;
    JLabel player1ScoreLabel;
    JLabel player2ScoreLabel;

    int visibleButtons = 12; // Initial count of visible buttons

    int player1Score = 0;
    int player2Score = 0;

    public DiceDash() {
        setTitle("Simple Yahtzee Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Calculate width based on 9:16 aspect ratio with height 500 pixels
        int height = 500;
        int width = (int) (height * (9.0 / 16.0));
        setSize(width, height);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Player 1 Score Label
        player1ScoreLabel = new JLabel("Player 1 Score: " + player1Score);
        player1ScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(player1ScoreLabel);

        // Player 2 Score Label
        player2ScoreLabel = new JLabel("Player 2 Score: " + player2Score);
        player2ScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(player2ScoreLabel);

        // Player 2 Roll
        player2RollPanel = new JPanel(new GridLayout(1, 1));
        player2RollPanel.setBorder(BorderFactory.createTitledBorder("Player 2 Roll"));
        player2RollPanel.setPreferredSize(new Dimension(150, 100)); // Setting preferred size
        add(player2RollPanel);
        JButton player2RollButton = new JButton("Roll"); // Creating player 2 roll button
        player2RollPanel.add(player2RollButton); // Adding button to player 2 roll panel

        // Player 2 Dice
        player2DicePanel = new JPanel(new GridLayout(1, 5));
        player2DicePanel.setBorder(BorderFactory.createTitledBorder("Player 2 Dice"));
        player2DicePanel.setPreferredSize(new Dimension(750, 100)); // Setting preferred size
        add(player2DicePanel);
        for (int i = 0; i < 5; i++) {
            player2DicePanel.add(new JButton("Die " + (i + 1))); // Adding buttons to player 2 dice panel
        }

        // Score Sheet
        scoreSheetPanel = new JPanel(new GridLayout(6, 3)); // Update grid layout to 6 rows and 3 columns
        scoreSheetPanel.setBorder(BorderFactory.createTitledBorder("Score Sheet"));
        scoreSheetPanel.setPreferredSize(new Dimension(900, 600)); // Setting preferred size
        add(scoreSheetPanel);

        // Add labels and buttons
        String[] labels = { "1", "2", "3", "4", "5", "6" };
        int i1 = 1, j1 = 1;
        for (String labelText : labels) {
            JLabel label = new JLabel(labelText);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            scoreSheetPanel.add(label);
            JButton button1 = new JButton((i1++) + ", " + j1);
            JButton button2 = new JButton(i1 + ", " + (j1++));
            scoreSheetPanel.add(button1);
            scoreSheetPanel.add(button2);
            i1 = 1;

            // Add ActionListener to buttons in the second column
            button1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    int score = Integer.parseInt(clickedButton.getText());
                    player1Score += score;
                    player1ScoreLabel.setText("Player 1 Score: " + player1Score);
                    clickedButton.setVisible(false);
                    checkEndGame();
                }
            });

            // Add ActionListener to buttons in the first column
            button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    int score = Integer.parseInt(clickedButton.getText());
                    player2Score += score;
                    player2ScoreLabel.setText("Player 2 Score: " + player2Score);
                    clickedButton.setVisible(false);
                    checkEndGame();
                }
            });
        }

        // Player 1 Dice
        player1DicePanel = new JPanel(new GridLayout(1, 5));
        player1DicePanel.setBorder(BorderFactory.createTitledBorder("Player 1 Dice"));
        player1DicePanel.setPreferredSize(new Dimension(750, 100)); // Setting preferred size
        add(player1DicePanel);
        for (int i = 0; i < 5; i++) {
            JButton dieButton = new JButton("Die " + (i + 1));
            player1DicePanel.add(dieButton); // Adding buttons to player 1 dice panel
        }

        // Player 1 Roll
        player1RollPanel = new JPanel(new GridLayout(1, 1));
        player1RollPanel.setBorder(BorderFactory.createTitledBorder("Player 1 Roll"));
        player1RollPanel.setPreferredSize(new Dimension(150, 100)); // Setting preferred size
        add(player1RollPanel);
        JButton player1RollButton = new JButton("Roll"); // Creating player 1 roll button
        player1RollPanel.add(player1RollButton); // Adding button to player 1 roll panel

        // ActionListener for player 1 roll button
        player1RollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int[] diceValues = new int[6]; // Array to store counts for each dice value

                // Roll the dice and update counts
                for (Component component : player1DicePanel.getComponents()) {
                    if (component instanceof JButton) {
                        JButton dieButton = (JButton) component;
                        int randomNumber = random.nextInt(6) + 1; // Generate random number from 1 to 6
                        dieButton.setText(String.valueOf(randomNumber)); // Set text of dice button
                        diceValues[randomNumber - 1]++; // Increment count for this dice value
                    }
                }

                // Update score sheet buttons
                for (int i = 0; i < diceValues.length; i++) {
                    JButton button = (JButton) scoreSheetPanel.getComponent(i * 3 + 1); // Get button for this dice
                                                                                        // value
                    int sum = diceValues[i] * (i + 1); // Calculate sum by multiplying occurrences with label value
                    button.setText(String.valueOf(sum)); // Set sum as text
                }
            }
        });

        // ActionListener for player 2 roll button
        player2RollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int[] diceValues = new int[6]; // Array to store counts for each dice value

                // Roll the dice and update counts
                for (Component component : player2DicePanel.getComponents()) {
                    if (component instanceof JButton) {
                        JButton dieButton = (JButton) component;
                        int randomNumber = random.nextInt(6) + 1; // Generate random number from 1 to 6
                        dieButton.setText(String.valueOf(randomNumber)); // Set text of dice button
                        diceValues[randomNumber - 1]++; // Increment count for this dice value
                    }
                }

                // Update score sheet buttons in the second column
                for (int i = 0; i < diceValues.length; i++) {
                    JButton button = (JButton) scoreSheetPanel.getComponent(i * 3 + 2); // Get button in the second
                                                                                        // column for this dice value
                    int sum = diceValues[i] * (i + 1); // Calculate sum by multiplying occurrences with label value
                    button.setText(String.valueOf(sum)); // Set sum as text
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to check if all buttons are invisible and determine the winner or if
    // it's a tie
    void checkEndGame() {
        visibleButtons--; // Decrement the count of visible buttons
        if (visibleButtons == 0) {
            if (player1Score > player2Score) {
                JOptionPane.showMessageDialog(this, "Player 1 wins with a score of " + player1Score);
            } else if (player2Score > player1Score) {
                JOptionPane.showMessageDialog(this, "Player 2 wins with a score of " + player2Score);
            } else {
                JOptionPane.showMessageDialog(this, "It's a tie!");
            }
            System.exit(0); // End the game
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiceDash::new);
    }
}