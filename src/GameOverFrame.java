//needs improvement
//the ummm,, picture isn't showing, the gameover png
//manmaged to show the other stats, but there on the left corner

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameOverFrame extends JPanel {

    public GameOverFrame(JFrame frame) {

        setLayout(new BorderLayout());
        setBackground(Color.BLACK); //new background,set to black

        //panel for tge game over image
        JLabel gameOverImage = new JLabel(new ImageIcon(new ImageIcon("src/images/game_over.png")
                .getImage().getScaledInstance(1004, 591, Image.SCALE_SMOOTH))); 
        gameOverImage.setHorizontalAlignment(SwingConstants.CENTER); 
        add(gameOverImage, BorderLayout.CENTER); // Add the image in the center of the panel

        // for stats pannel
        JPanel statsPanel = new JPanel();
        statsPanel.setOpaque(false); 
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS)); //stacking on yaxis or verticALLY

        //here is the labes (not images) for the stats
        JLabel totalSpinsLabel = new JLabel("Total Plays: " + PlayerStorage.getTotalSpins());
        totalSpinsLabel.setForeground(Color.WHITE); 
        totalSpinsLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel totalEarningsLabel = new JLabel("Total Earnings: " + PlayerStorage.getTotalEarnings());
        totalEarningsLabel.setForeground(Color.WHITE); 
        totalEarningsLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel totalLossesLabel = new JLabel("Total Losses: " + PlayerStorage.getTotalLosses());
        totalLossesLabel.setForeground(Color.WHITE); 
        totalLossesLabel.setFont(new Font("Arial", Font.BOLD, 18));

        //adding the labels
        statsPanel.add(totalSpinsLabel);
        statsPanel.add(totalEarningsLabel);
        statsPanel.add(totalLossesLabel);


        //**should be center, but is not centering, its not giving**
        add(statsPanel, BorderLayout.CENTER);

        //button pannel for exit butt
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0)); 
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 300, 20, 300)); //padding

        //exit button image
        JButton exitButton = new JButton(new ImageIcon("src/images/buttons/exit_button.png"));
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); //will exit the app or program
        });
      

        //add buttons in panel
        buttonPanel.add(exitButton);

        //the button will be at the bottom of the frame, which worked
        add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().removeAll(); 
        frame.add(this);
        frame.revalidate();
        frame.repaint();
    }
}
