import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

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
        backButtPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Keep FlowLayout, but remove unnecessary
                                                                           // padding
        backButtPanel.setOpaque(false);

        // Create the button
        JButton backButton = new JButton("BACK");
        backButton.setPreferredSize(new Dimension(90, 45)); // Set button size
        backButton.setBackground(new Color(56, 43, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5)); // Button border
        backButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        backButtPanel.setBorder(BorderFactory.createEmptyBorder(35, 28, 0, 0));

        // Add action listener to the button
        backButton.addActionListener(e -> play());

        // Add button to the panel
        backButtPanel.add(backButton);

        // Add panel to the background container (assuming `background` is the parent
        // container)
        background.add(backButtPanel, BorderLayout.NORTH);

        // add background image to the main panel
        add(background, BorderLayout.CENTER);

        // init game variables
        this.multiplier = 0;
        this.bet = 0;

        Balance balance = new Balance(frame);
        balance.triggerBankruptcyCheck();
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

    protected void processWinLoss() {
        int winLoss = (int) (this.bet * this.multiplier);
        if (PlayerStorage.getLoanAmount() > 0) { // if in debt, pay half our debt with half of earnings
            if (winLoss > 1) { // check if we have earnings or wins to pay
                PlayerStorage.setMoney(PlayerStorage.getMoney() + (int) (winLoss / 2));
                PlayerStorage.setLoanAmount(PlayerStorage.getLoanAmount() - (int) (winLoss / 2));
            }
        } else { // if we're not in debt, then add wins to money
            PlayerStorage.setMoney(PlayerStorage.getMoney() + winLoss);
        }
        // for data saving purposes only...
        if (winLoss <= 0) {
            // save our losses
            PlayerStorage.setTotalLosses(PlayerStorage.getTotalLosses() + this.bet);
        } else {
            // save our earnings
            PlayerStorage.setTotalEarnings(PlayerStorage.getTotalEarnings() + (winLoss - this.bet));
        }
    }

    protected abstract void bet();

}
