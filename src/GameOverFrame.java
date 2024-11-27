import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameOverFrame {
    public GameOverFrame(JFrame frame) {
        frame.getContentPane().removeAll(); // Clear the frame
        frame.setLayout(new GridLayout(2, 1)); // 2 rows, 1 column

        // Game Over message
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(gameOverLabel); // Add to the first row

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        exitButton.addActionListener((ActionEvent e) -> {
            // Close the application
            System.exit(0);
        });

        // Panel for the Exit button (optional for padding)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 5, 0, 0)); // GridBagLayout allows more control over the layout
        buttonPanel.add(exitButton);

        // Add padding to the button panel (adjust bottom padding as needed)
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 50, 150)); // 50px bottom padding

        frame.add(buttonPanel); // Add to the second row

        // Refresh the frame
        frame.revalidate();
        frame.repaint();
    }
}
