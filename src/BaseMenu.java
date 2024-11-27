import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public abstract class BaseMenu extends JPanel implements BaseDisplay {
    protected JFrame frame;
    protected JPanel buttonPanel;

    public BaseMenu(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel backgroundImage = new JLabel(BaseDisplay.background);
        backgroundImage.setLayout(new BorderLayout());

        buttonPanel = new JPanel(new GridLayout(3, 0, 5, 5)); // Default spacing; can be changed
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(290, 150, 100, 150));
        buttonPanel.setOpaque(false);

        backgroundImage.add(buttonPanel, BorderLayout.CENTER);
        add(backgroundImage);
    }

    // helper methods ...
    protected JButton addButton(ImageIcon icon, ActionListener listener) {
        JButton button = new JButton(); // No text in the constructor

        // Set the image icon for the button
        button.setIcon(icon);

        // Remove focus outline on the button
        button.setFocusable(false);

        // Remove default background and border for a cleaner look
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        // Add action listener
        button.addActionListener(listener);

        // Add the button to the panel
        buttonPanel.add(button);

        return button;
    }

    protected void navigateTo(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
}

////////////////////////////////
//////////MAIN MENU/////////////
////////////////////////////////
class MainMenu extends BaseMenu {

    public MainMenu(JFrame frame) {
        super(frame);

        // Create ImageIcon objects for each button
        ImageIcon startIcon = new ImageIcon("src/images/buttons/start_button.png");
        ImageIcon scoresIcon = new ImageIcon("src/images/buttons/scores_button.png");
        ImageIcon exitIcon = new ImageIcon("src/images/buttons/exit_button.png");

        // Add buttons with icons
        addButton(startIcon, e -> navigateTo(new GameMenu(frame)));
        addButton(scoresIcon, e -> JOptionPane.showMessageDialog(frame, "scorerere"));
        addButton(exitIcon, e -> System.exit(0));
    }
}

////////////////////////////////
//////////GAME MENU/////////////
////////////////////////////////
class GameMenu extends BaseMenu {

    public GameMenu(JFrame frame) {
        super(frame);

        // Create ImageIcon objects for each button
        ImageIcon slotIcon = new ImageIcon("src/images/buttons/slot_machine_button.png");
        ImageIcon lucky9Icon = new ImageIcon("src/images/buttons/lucky9_button.png");
        ImageIcon balanceIcon = new ImageIcon("src/images/buttons/balance_button.png");
        ImageIcon backIcon = new ImageIcon("src/images/buttons/back_button.png");

        // Add buttons with icons
        addButton(slotIcon, e -> navigateTo(new SlotMachine(frame)));
        addButton(lucky9Icon, e -> JOptionPane.showMessageDialog(frame, "luckyy"));
        addButton(balanceIcon, e -> navigateTo(new Balance(frame)));
        addButton(backIcon, e -> navigateTo(new MainMenu(frame)));

    }
}
