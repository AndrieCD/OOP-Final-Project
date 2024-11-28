//needs improvement
//the ummm,, picture isn't showing, the gameover png
//manmaged to show the other stats, but there on the left corner
//new new

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameOverFrame extends JPanel {

    public GameOverFrame(JFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK); //default background to black though it will be replaced with image

         //panel for tge game over image
        JLabel gameOverImage = new JLabel(new ImageIcon(new ImageIcon("src/images/game_over.png")
                .getImage().getScaledInstance(1004, 591, Image.SCALE_SMOOTH))); 
        gameOverImage.setHorizontalAlignment(SwingConstants.CENTER); //used for positioning for the screen
        add(gameOverImage, BorderLayout.CENTER);//the image will be put at the center

        // buttons for exit and stats
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 0)); 
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150)); //padding

        // exit button image
        JButton exitButton = new JButton(new ImageIcon("src/images/buttons/exit_button.png")); //directory
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); //exiting
        });

        // stats button image
        JButton statsButton = new JButton(new ImageIcon("src/images/buttons/stats_button.png"));
        statsButton.setContentAreaFilled(false);
        statsButton.setBorderPainted(false);
        statsButton.setFocusPainted(false);
        statsButton.addActionListener((ActionEvent e) -> {
            //stats dialouge should show the total plays earnings and losses
            showStatsDialog();
        });

        //adding the buttons to the pannels
        buttonPanel.add(statsButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(this);
        frame.revalidate();
        frame.repaint();
    }

    //for the dialog stats showing
    private void showStatsDialog() {
        String statsMessage = "<html><h2>Player Stats</h2>"
                + "<p><strong>Total Plays:</strong> " + PlayerStorage.getTotalSpins() + "</p>"
                + "<p><strong>Total Earnings:</strong> " + PlayerStorage.getTotalEarnings() + "</p>"
                + "<p><strong>Total Losses:</strong> " + PlayerStorage.getTotalLosses() + "</p>"
                + "</html>";
        JOptionPane.showMessageDialog(this, statsMessage, "Player Stats", JOptionPane.INFORMATION_MESSAGE);
    }

}
