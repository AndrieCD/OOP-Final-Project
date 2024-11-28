import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

// abstract class, BaseMenu that specific menus will further implement
public abstract class BaseMenu extends JPanel implements BaseDisplay {
    protected JFrame frame;
    protected JPanel buttonPanel;

    public BaseMenu(JFrame frame) {
        // use the frame from Main.java
        this.frame = frame;
        setLayout(new BorderLayout());

        // apply our background
        JLabel backgroundImage = new JLabel(BaseDisplay.background);
        backgroundImage.setLayout(new BorderLayout());

        // create a panel to ocntain our buttons
        buttonPanel = new JPanel(new GridLayout(3, 0, 10, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(290, 150, 100, 150));
        buttonPanel.setOpaque(false);

        // add the buttonPanel on the background
        backgroundImage.add(buttonPanel, BorderLayout.CENTER);
        add(backgroundImage);
    }

    // helper methods ...
    protected JButton addButton(Object buttonContent, ActionListener listener) {
        JButton button = new JButton();

        // if statement for image icons and string icons
        // so we can use both string and images
        if (buttonContent instanceof String) {
            // tring-based buttons
            button.setText((String) buttonContent);
            button.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(235, 58, 48));
            button.setBorder(BorderFactory.createLineBorder(new Color(255, 173, 1), 2));
        } else if (buttonContent instanceof ImageIcon) {
            // image-based buttons
            button.setIcon((ImageIcon) buttonContent);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
        }

        button.setFocusable(false); // focus off
        button.addActionListener(listener);
        buttonPanel.add(button);
        return button;
    }

    // helper method for button navigation
    protected void navigateTo(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
}

////////////////////////////////
//////////GAME MENU/////////////
class GameMenu extends BaseMenu {
    // game menu will contain lucky9, slotmachine, and balance

    public GameMenu(JFrame frame) {
        super(frame);

        // image icons fo buttons
        ImageIcon slotIcon = new ImageIcon("src/images/buttons/slot_machine_button.png");
        ImageIcon lucky9Icon = new ImageIcon("src/images/buttons/lucky9_button.png");
        ImageIcon balanceIcon = new ImageIcon("src/images/buttons/balance_button.png");
        ImageIcon backIcon = new ImageIcon("src/images/buttons/back_button.png");

        // navigating towards the games or checking balance
        addButton(slotIcon, e -> {
            // Check if the player is bankrupt before navigating
            if (PlayerStorage.getMoney() <= 0) {
                Balance balance = new Balance(frame);
                balance.triggerBankruptcyCheck(); // Show error and navigate to Game Over
            } else {
                navigateTo(new SlotMachine(frame)); // Proceed to Slot Machine
            }
        });

        addButton(lucky9Icon, e -> navigateTo(new Lucky9(frame)));
        addButton(balanceIcon, e -> navigateTo(new Balance(frame)));
        addButton(backIcon, e -> navigateTo(new MainMenu(frame)));
    }
}

////////////////////////////////
//////////MAIN MENU/////////////
class MainMenu extends BaseMenu {
    // main menu is the menu on start
    public MainMenu(JFrame frame) {
        super(frame);

        // for main menu buttons
        ImageIcon startIcon = new ImageIcon("src/images/buttons/start_button.png");
        // ImageIcon scoresIcon = new ImageIcon("src/images/buttons/scores_button.png");
        ImageIcon exitIcon = new ImageIcon("src/images/buttons/exit_button.png");

        // navigation
        addButton(startIcon, e -> navigateTo(new GameMenu(frame)));
        // addButton(scoresIcon, e -> JOptionPane.showMessageDialog(frame,
        // "scorerere"));
        addButton(exitIcon, e -> saveAndExit());
    }

    // save and exit method.. yeah...
    private void saveAndExit() {
        PlayerStorage.saveData();
        System.exit(0);
    }

}
