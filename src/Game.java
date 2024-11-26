import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Game extends JPanel implements BaseDisplay {
    protected JFrame frame;
    protected JPanel buttonPanel;
    protected JPanel topPanel;
    protected JPanel backButtPanel;
    protected JLabel background;
    protected int bet;
    protected double multiplier;

    public Game(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // background!
        background = new JLabel(BaseDisplay.background);
        background.setLayout(new BorderLayout());

        // buttons panel
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(75, 150, 150, 150));
        buttonPanel.setOpaque(false);

        background.add(buttonPanel, BorderLayout.CENTER);

        // BACK BUTTON
        backButtPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        backButtPanel.setOpaque(false);
        JButton backButton = new JButton("<");
        backButton.setPreferredSize(new Dimension(45, 45)); // Set button size
        backButton.addActionListener(e -> play());
        backButtPanel.add(backButton);
        background.add(backButtPanel, BorderLayout.NORTH);

        // add background image to the main panel
        add(background, BorderLayout.CENTER);

        // init game variables
        this.multiplier = 0;
        this.bet = 0;
    }

    // helper method ...
    protected JButton addButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        buttonPanel.add(button);
        return button;
    }

    private void play() {
        GameMenu gameMenu = new GameMenu(frame);
        frame.getContentPane().removeAll();
        frame.add(gameMenu);
        frame.revalidate();
        frame.repaint();
    }

    protected abstract void bet();

}
