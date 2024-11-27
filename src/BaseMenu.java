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

        buttonPanel = new JPanel(new GridLayout(3, 0, 20, 15)); // Default spacing; can be changed
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(270, 150, 60, 150));
        buttonPanel.setOpaque(false);

        backgroundImage.add(buttonPanel, BorderLayout.CENTER);
        add(backgroundImage);
    }

    // helper methods ...
    protected JButton addButton(String text, ActionListener listener) {
        JButton button = new JButton(text);

        // disable focus on text
        button.setFocusable(false);

        // Set font and size
        button.setFont(new Font("Trebuchet MS", Font.BOLD, 18));

        // Set background color
        button.setBackground(new Color(0, 0, 0));

        // Set text color
        button.setForeground(new Color(255, 255, 255));

        button.setBorder(BorderFactory.createLineBorder(new Color(255, 173, 1), 2)); // Border color and thickness
        button.addActionListener(listener);
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
class MainMenu extends BaseMenu {

    public MainMenu(JFrame frame) {
        super(frame);

        // add buttons ([STRING_LABEL] , [ACTION])
        addButton("START", e -> navigateTo(new GameMenu(frame)));
        addButton("SCORES", e -> JOptionPane.showMessageDialog(frame, "scorerere"));
        addButton("EXIT", e -> System.exit(0));
    }
}

////////////////////////////////
//////////GAME MENU/////////////
class GameMenu extends BaseMenu {

    public GameMenu(JFrame frame) {
        super(frame);

        // add buttons ([STRING_LABEL] , [ACTION])
        addButton("SLOT MACHINE", e -> navigateTo(new SlotMachine(frame)));
        addButton("LUCKY9", e -> navigateTo(new Lucky9(frame)));
        addButton("BALANCE", e -> JOptionPane.showMessageDialog(frame, "balbalbal"));
        addButton("BACK", e -> navigateTo(new MainMenu(frame)));
    }
}
