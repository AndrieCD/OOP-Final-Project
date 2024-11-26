import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Game extends JPanel implements BaseDisplay {
    protected static GameTimer timer;
    protected JFrame frame;
    protected JPanel buttonPanel;
    protected JPanel topPanel;
    protected JPanel backButtPanel;
    protected JLabel backgroundImage;
    public static int money = 1000;
    public int bet;
    public double multiplier;

    public Game(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // background!
        backgroundImage = new JLabel(BaseDisplay.background);
        backgroundImage.setLayout(new BorderLayout());

        // buttons panel
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(75, 150, 150, 150));
        buttonPanel.setOpaque(false);

        backgroundImage.add(buttonPanel, BorderLayout.SOUTH);

        /////////////////////////   /////////////////////////   /////////////////////////   
        /////////////////////////   BACK BUTTON MISSING    /// BORDERLAYOUT ERROR???
        /////////////////////////   /////////////////////////   /////////////////////////   
        // BACK BUTTON
        backButtPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        backButtPanel.setOpaque(false);
        JButton backButton = new JButton("<");
        backButton.setPreferredSize(new Dimension(45, 45)); // Set button size
        backButton.addActionListener(e -> play());
        backButtPanel.add(backButton);
        backgroundImage.add(backButtPanel, BorderLayout.NORTH);

        // add gameTimer on bottom-left corner of the window
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        if (timer == null)
            timer = new GameTimer(frame);
        topPanel.add(timer);
        backgroundImage.add(topPanel, BorderLayout.NORTH);

        // add background image to the main panel
        add(backgroundImage, BorderLayout.CENTER);

        // init game variables
        this.multiplier = 0;
        this.bet = 0;
    }

    // SETTER FOR BALANCE OPTION.... LOAN -- > setMoney(getMoney() + loan);
    public static void setMoney(int newVal) {
        money = newVal;
    }

    public static int getMoney() {
        return money;
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
