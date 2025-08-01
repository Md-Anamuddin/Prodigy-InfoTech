import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends JFrame implements ActionListener {
    private final JTextField guessField;
    private final JButton guessButton;
    private final JLabel messageLabel;
    private final JLabel attemptsLabel;

    private final int targetNumber;
    private int attempts;

    public NumberGuessingGame() {
        // Window setup
        setTitle("Number Guessing Game");
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background panel
        GradientPanel panel = new GradientPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Guess the Number (1 to 100)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        guessField = new JTextField(10);
        guessField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(guessField, gbc);

        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        guessButton.setBackground(new Color(70, 130, 180));
        guessButton.setForeground(Color.WHITE);
        guessButton.setFocusPainted(false);
        guessButton.addActionListener(this);
        gbc.gridx = 1;
        panel.add(guessButton, gbc);

        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        attemptsLabel = new JLabel("Attempts: 0");
        attemptsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        attemptsLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 3;
        panel.add(attemptsLabel, gbc);

        setContentPane(panel);
        setVisible(true);

        // Game logic
        Random rand = new Random();
        targetNumber = rand.nextInt(100) + 1;
        attempts = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = guessField.getText();
        try {
            int guess = Integer.parseInt(input);
            attempts++;
            attemptsLabel.setText("Attempts: " + attempts);

            if (guess < 1 || guess > 100) {
                messageLabel.setText("❗ Please guess a number between 1 and 100.");
            } else if (guess < targetNumber) {
                messageLabel.setText("Too low! Try again.");
            } else if (guess > targetNumber) {
                messageLabel.setText("Too high! Try again.");
            } else {
                messageLabel.setText("🎉 Correct! You guessed it in " + attempts + " attempts.");
                guessButton.setEnabled(false);
                guessField.setEditable(false);
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("❗ Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGame::new);
    }
}

// 🌈 Gradient background panel
class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Create a vertical gradient from sky blue to deep blue
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(
            0, 0, new Color(135, 206, 250),
            0, getHeight(), new Color(25, 25, 112)
        );
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
