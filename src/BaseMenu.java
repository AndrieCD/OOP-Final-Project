import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class BaseMenu extends JPanel implements BaseDisplay {
    protected JFrame frame;
    protected JPanel buttonPanel;

    public BaseMenu(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel backgroundImage = new JLabel(BaseDisplay.background);
        backgroundImage.setLayout(new BorderLayout());

        buttonPanel = new JPanel(new GridLayout(0, 1, 0, 10)); // Default spacing; can be changed
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(75, 150, 150, 150));
        buttonPanel.setOpaque(false);

        backgroundImage.add(buttonPanel, BorderLayout.CENTER);
        add(backgroundImage);
    }

    // helper method ...
    protected JButton addButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        buttonPanel.add(button);
        return button;
    }
}
