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