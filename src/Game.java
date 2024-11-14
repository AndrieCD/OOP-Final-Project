import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Game extends JPanel implements BaseDisplay {
    protected static GameTimer timer;
    protected JFrame frame;
    protected JPanel buttonPanel;
    protected JPanel timerPanel;
    protected JPanel backButtPanel;
    int money;
    int bet;
    int multiplier;

    public Game(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // background!
        JLabel backgroundImage = new JLabel(BaseDisplay.background);
        backgroundImage.setLayout(new BorderLayout());

        // button panel
        buttonPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(75, 150, 150, 150));
        buttonPanel.setOpaque(false);

        // add button panel to center of background
        backgroundImage.add(buttonPanel, BorderLayout.CENTER);

        // add background image to the main panel
        add(backgroundImage, BorderLayout.CENTER);

        backButtPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        backButtPanel.setOpaque(false);

        JButton backButton = new JButton("<");
        backButton.setPreferredSize(new Dimension(45, 45)); // Set button size
        backButton.addActionListener(e -> play());

        backButtPanel.add(backButton);
        backgroundImage.add(backButtPanel, BorderLayout.NORTH);

        // add gameTimer on bottom-left corner of the window
        timerPanel = new JPanel(new BorderLayout());
        timerPanel.setOpaque(false);
        if (timer == null)
            timer = new GameTimer(frame);
        timerPanel.add(timer);
        backgroundImage.add(timerPanel, BorderLayout.SOUTH);

        // init game variables
        this.money = 1000;
        this.multiplier = -1;
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

    public abstract void bet();
}
