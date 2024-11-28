//this section should cover the screen when the player
//has entered bankcrupty
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameOverFrame extends JPanel {

    public GameOverFrame(JFrame frame) {
        // Set the layout for this panel
        setLayout(new BorderLayout());
        setBackground(Color.BLACK); // Set the background to black

        // Create a panel for the "Game Over" image
        JLabel gameOverImage = new JLabel(new ImageIcon(new ImageIcon("src/images/game_over.png")
                .getImage().getScaledInstance(1004, 591, Image.SCALE_SMOOTH))); // Scale image
        gameOverImage.setHorizontalAlignment(SwingConstants.CENTER); // Center the image
        add(gameOverImage, BorderLayout.CENTER); // Add the image in the center of the panel

        // Create a button panel with GridLayout for back and exit buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // 1 row, 2 columns, 20px horizontal gap
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 300, 20, 300)); // Add padding


        // Exit button
        JButton exitButton = new JButton(new ImageIcon("src/images/buttons/exit_button.png"));
    
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); // Exit the application
        });

        // Add buttons to the button panel
        buttonPanel.add(exitButton);

        // Add the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // This will only modify the content inside the existing frame, not the frame itself
        frame.getContentPane().removeAll(); // Clear the previous content
        frame.add(this); // Add the current GameOverFrame panel
        frame.revalidate();
        frame.repaint(); // Revalidate and repaint the frame to ensure the changes are reflected
    }
}
