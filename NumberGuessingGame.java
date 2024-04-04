import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private JButton startButton;
    private boolean isLimitedMode;
    private int maxGuesses;
    private int randomNumber;
    private int attempts;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        // Set the background image for the main content pane
        setContentPane(new BackgroundPanel("C:\\Users\\aravi\\OneDrive\\Desktop\\color-black-facts.jpg"));

        startButton = new JButton("Start Game");
        startButton.setBackground(new Color(178, 83, 7)); // Brown color
        startButton.setForeground(new Color(255, 255, 255));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayGameModes();
            }
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false); // Make the panel transparent
        panel.add(Box.createVerticalGlue()); // Add vertical glue to center the button
        panel.add(startButton);
        panel.add(Box.createVerticalGlue()); // Add vertical glue to center the button
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void displayGameModes() {
        getContentPane().removeAll();
        repaint();

        JPanel panel = new BackgroundPanel("C:\\Users\\aravi\\OneDrive\\Desktop\\color-black-facts.jpg");
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering

        JButton commonModeButton = createStyledButton("Common Mode");
        commonModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isLimitedMode = false;
                startGame();
            }
        });

        JButton limitedModeButton = createStyledButton("Limited Chances Mode");
        limitedModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isLimitedMode = true;
                maxGuesses = Integer.parseInt(JOptionPane.showInputDialog("Enter the maximum number of guesses:"));
                startGame();
            }
        });

        // Add buttons to panel with constraints for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing
        gbc.anchor = GridBagConstraints.CENTER; // Center the buttons
        panel.add(commonModeButton, gbc);

        gbc.gridy++;
        panel.add(limitedModeButton, gbc);

        setContentPane(panel);
        revalidate(); // Revalidate the frame to reflect changes
    }

    private void startGame() {
        getContentPane().removeAll();
        repaint();

        randomNumber = new Random().nextInt(100) + 1;
        attempts = 0;

        JPanel panel = new BackgroundPanel("C:\\Users\\aravi\\OneDrive\\Desktop\\color-black-facts.jpg");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel promptLabel = new JLabel("Guess a number between 1 and 100:");
        panel.add(promptLabel, gbc);
        promptLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        JTextField textField = new JTextField(5); // Reduce the size of the text field
        textField.setMaximumSize(new Dimension(60, 30)); // Set maximum size
        panel.add(textField, gbc);

        gbc.gridy++;
        JButton guessButton = new JButton("Guess");
        panel.add(guessButton, gbc);
        guessButton.setBackground(new Color(178, 83, 7)); // Brown color
        guessButton.setForeground(new Color(255, 255, 255));
        gbc.gridy++;
        JLabel resultLabel = new JLabel("");
        resultLabel.setForeground(Color.WHITE); // Set text color to white
        panel.add(resultLabel, gbc);

        gbc.gridy++;
        JLabel attemptLabel = new JLabel("Attempts: 0");
        attemptLabel.setForeground(Color.WHITE); // Set text color to white
        panel.add(attemptLabel, gbc);

        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    int guess = Integer.parseInt(textField.getText());
                    if (guess < randomNumber) {
                        resultLabel.setText("Too low, try again.");
                    } else if (guess > randomNumber) {
                        resultLabel.setText("Too high, try again.");
                    } else {
                        resultLabel.setText("Congratulations! You guessed the number.");
                        guessButton.setEnabled(false);
                    }
                    if (isLimitedMode) {
                        maxGuesses--;
                        if (maxGuesses == 0) {
                            guessButton.setEnabled(false);
                            resultLabel.setText("Chances are over. The number was " + randomNumber);
                            resultLabel.setFont(new Font("bold", Font.PLAIN, 40));
                        }
                    }
                    attempts++; // Increment attempts
                    attemptLabel.setText("Attempts: " + attempts); // Update attempt label
                } catch (NumberFormatException e) {
                    resultLabel.setText("Invalid input. Please enter a number.");
                    resultLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
                }
                textField.setText(""); // Clear text field
                textField.requestFocus(); // Set focus to text field
            }
        });

        // Add a focus listener to text field
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                textField.selectAll(); // Select all text when text field gains focus
            }
        });

        setContentPane(panel);
        revalidate(); // Revalidate the frame to reflect changes
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(178, 83, 7)); // Brown color
        button.setForeground(new Color(255, 255, 255)); // Sand color
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGame();
            }
        });
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        backgroundImage = new ImageIcon("C:\\Users\\aravi\\OneDrive\\Desktop\\color-black-facts.jpg").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
