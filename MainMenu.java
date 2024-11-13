import javax.swing.*; // provides us with the classes and fields for GUI creation and management
import java.awt.*; // provides us with the classes and fields for GUI creation and management
import java.awt.event.ActionEvent; // provides us with the possible events to listen for
import java.awt.event.ActionListener; // provides us with listeners to detect events (like clicking a button)
public class MainMenu extends JPanel {
    // private JFrame frame;

    public MainMenu(JFrame frame) {
        // this.frame = frame;
        setPreferredSize(new Dimension(1280 / 2, 720 / 2));
        setBackground(Color.BLACK);

        // create 4? buttons
        JButton playButton = new JButton("Play");
        JButton viewMoneyButton = new JButton("View Money");
        JButton buyChipsButton = new JButton("Buy Chips");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(500, 50);
        playButton.setPreferredSize(buttonSize);
        playButton.setMaximumSize(buttonSize);
        viewMoneyButton.setPreferredSize(buttonSize);
        viewMoneyButton.setMaximumSize(buttonSize);
        buyChipsButton.setPreferredSize(buttonSize);
        buyChipsButton.setMaximumSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);

        // center our buttons (x-axis)
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewMoneyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyChipsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // set our layout in a box
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // add our buttons in our main menu panel
        add(Box.createRigidArea(new Dimension(0, 60)));
        add(playButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(viewMoneyButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(buyChipsButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exitButton);

        // event listeners
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "PLAY"); // may chat or notif window pag ka press for sample
            }
        });

        viewMoneyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Wala kang pera");
            }
        });

        buyChipsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Mama mo chips");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
