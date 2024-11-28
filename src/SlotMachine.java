import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.HashMap;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// -------------------------------------------------------------------------
// -------------------S--L--O--T----M--A--C--H--I--N--E---------------------
// -------------------------------------------------------------------------
public class SlotMachine extends Game {
    // non graphics related fields
    private final HashMap<String, Double> winningPatterns;
    private final String[] icons;
    private HashMap<String, String> iconImages; // holds keys as image's label, and value as img path
    private String[] result;
    private boolean isSpinning;
    private String lastIcon;

    // graphics related fields
    private JLabel icon1, icon2, icon3;
    private JLabel moneyLabel;
    private JPanel bottomPanel;
    private JTextField betInput;

    // CONSTRUCTOR //
    public SlotMachine(JFrame frame) {
        super(frame);

        // initalize the icons array
        icons = new String[] { "Seven", "Cherry", "Lemon", "Orange", "Plum", "Bar", "Star" };

        // initialize patterns
        winningPatterns = new HashMap<>();
        initializePatterns();

        // init result array
        result = new String[3];

        this.isSpinning = false;

        // initialize display
        initializeDisplay();
    }

    // INITIALIZE THE PATTERN HASHMAP //
    private void initializePatterns() {
        // PAIR IS < PATTERN , MULTIPLIER >
        winningPatterns.put("Seven Seven Seven", 10.0); // triple 7 pattern is 10x multiplier
        winningPatterns.put("Bar Bar Bar", 5.0);
        winningPatterns.put("Bar * Bar", 5.0);
        winningPatterns.put("Lemon Lemon Lemon", 2.0);
        winningPatterns.put("Plum Plum Plum", 2.0);
        winningPatterns.put("Orange Orange Orange", 2.0);
        winningPatterns.put("Cherry Cherry Cherry", 2.0);
        winningPatterns.put("Seven Seven *", 1.5); // two 7 then any
        winningPatterns.put("* Seven Seven", 1.5);
        winningPatterns.put("Seven * *", 1.0); // 1 seven then two any
        winningPatterns.put("* * Seven", 1.0); // 1 seven then two any
        winningPatterns.put("Star * *", 2.0);
        winningPatterns.put("* * Star", 2.0);
        winningPatterns.put("Star Star Star", 5.0);
    }

    // ---------DISPLAY METHOD------------//
    // ---------DISPLAY METHOD------------//
    // icon label helper method
    private JLabel createIconLabel() {
        JLabel label = new JLabel(updateIcons());
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        return label;
    }

    // // INITIALIZE DISPLAY //
    private void initializeDisplay() {
        // create a label to display the player's money, set properties
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        background.add(bottomPanel, BorderLayout.SOUTH);

        // create the panel for the slot machine's icons
        JPanel iconPanel = new JPanel();
        iconPanel.setBorder(BorderFactory.createEmptyBorder(150, 140, 0, 150));
        iconPanel.setOpaque(false);

        // create the labels using the helper method
        icon1 = createIconLabel();
        icon2 = createIconLabel();
        icon3 = createIconLabel();

        // add the labels to the iconPanel
        iconPanel.add(icon1);
        iconPanel.add(icon2);
        iconPanel.add(icon3);

        buttonPanel.add(iconPanel, BorderLayout.SOUTH);

        // SPIN BUTTON
        addButton("SPIN", e -> spin());
        JButton spinButton = (JButton) getButtonFromPanel(buttonPanel, "SPIN");
        if (spinButton != null) {
            spinButton.setBackground(new Color(235, 58, 48));
            spinButton.setForeground(Color.WHITE); // Set text color to white
            spinButton.setFont(new Font("Trebuchet MS", Font.BOLD, 18)); // Set font style and size
        }

        bet();
    }

    // helper method to customize button
    private JButton getButtonFromPanel(JPanel panel, String buttonText) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (button.getText().equals(buttonText)) {
                    return button; // Return the button if its text matches "SPIN"
                }
            }
        }
        return null; // Return null if button is not found
    }

    // HELPER METHOD TO UPDATE THE ICONS //
    private ImageIcon updateIcons() {
        iconImages = new HashMap<>();
        iconImages.put("Seven", "src/images/seven.png");
        iconImages.put("Cherry", "src/images/cherry.png");
        iconImages.put("Lemon", "src/images/lemon.png");
        iconImages.put("Orange", "src/images/orange.png");
        iconImages.put("Plum", "src/images/plum.png");
        iconImages.put("Bar", "src/images/bar.png");
        iconImages.put("Star", "src/images/star.png");

        // get all our keys in an array to randomize
        Object[] keys = iconImages.keySet().toArray();
        String randomKey = (String) keys[new Random().nextInt(keys.length)];

        lastIcon = randomKey;

        // randomize imgIcon on key's value
        String imagePath = iconImages.get(randomKey);
        ImageIcon imgIcon = new ImageIcon(imagePath);
        Image scaledImage = imgIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        imgIcon = new ImageIcon(scaledImage);

        return imgIcon;
    }

    // ---------SPIN METHOD------------//
    // ---------SPIN METHOD------------//
    private void spin() {
        validateBet();
        if (this.bet == 0 || this.isSpinning == true)
            return;

        this.isSpinning = true;

        int spinDuration = 2000; // 2 sec
        int spinInterval = 100; // 0.1 sec

        // timers for each icon
        Timer timer1 = new Timer(spinInterval, null);
        Timer timer2 = new Timer(spinInterval, null);
        Timer timer3 = new Timer(spinInterval, null);

        long startTime = System.currentTimeMillis();

        // timers that update each icons, lasting for 2, 4, and 6 seconds respectively
        timer1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                icon1.setIcon(updateIcons()); // random spin icon1
                result[0] = lastIcon;
                if (System.currentTimeMillis() - startTime >= spinDuration) {
                    timer1.stop(); // stop spinning after 2s
                }
            }
        });

        timer2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                icon2.setIcon(updateIcons()); // random spin icon2
                result[1] = lastIcon;
                if (System.currentTimeMillis() - startTime >= spinDuration * 1.5) {
                    timer2.stop(); // stop spinning after 2s
                }
            }
        });

        timer3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                icon3.setIcon(updateIcons()); // random spin icon3
                result[2] = lastIcon;
                if (System.currentTimeMillis() - startTime >= spinDuration * 2) {
                    timer3.stop(); // stop spinning after 2s
                    finalizeSpin(); // finalize our spin and store the results
                }
            }
        });

        // start the timed spins
        timer1.start();
        timer2.start();
        timer3.start();
    }

    // finalize spin
    private void finalizeSpin() {

        // apply multiplier
        this.multiplier = calculateMultiplier(result);

        ///// FOR TESTING ONLY-------------- CONSOLE PRINTS
        System.out.println("Result: " + result[0] + " " + result[1] + " " + result[2]);
        System.out.println("Multiplier: " + this.multiplier);

        // we process the spin
        processWinLoss();
        this.bet = 0;
        updateMoneyLabel();

        // set our flag to false
        this.isSpinning = false;
        PlayerStorage.setTotalSpins(PlayerStorage.getTotalSpins() + 1);
    }

    // CALCULATES THE WIN/LOSS BASED ON MULTIPLIER
    private double calculateMultiplier(String[] result) {
        String pattern = String.join(" ", result);

        // check for exact patterns in our talbe
        if (winningPatterns.containsKey(pattern)) {
            return winningPatterns.get(pattern);
        }

        // check patterns with wildcard '*' and 7
        if (result[0].equals("Seven") && result[1].equals("Seven")) { // Seven Seven *
            return winningPatterns.get("Seven Seven *");
        } else if (result[2].equals("Seven") && result[1].equals("Seven")) { // * Seven Seven
            return winningPatterns.get("* Seven Seven");
        } else if (result[0].equals("Seven")) { // Seven * *
            return winningPatterns.get("Seven * *");
        } else if (result[2].equals("Seven")) { // * * Seven
            return winningPatterns.get("* * Seven");
        }

        // check patterns with wildcard '*'and star
        if (result[0].equals("Seven")) { // star + two same icons
            if (result[1].equals(result[2]))
                return winningPatterns.get("Star * *");
        }

        // check patterns with wildcard '*'and bar
        if (result[0].equals("Bar") && result[2].equals("Bar")) { // bar * bar
            return winningPatterns.get("Bar * Bar");
        }

        return 0.0; // no match....
    }

    // ---------BET METHOD------------//
    // ---------BET METHOD------------//
    protected void bet() {
        // Create a panel for the betting GUI
        JPanel bettingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bettingPanel.setOpaque(false); // Transparent background

        // Set a bottom padding using an empty border (if needed)
        bettingPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        // Input field for the bet amount
        betInput = new JTextField(9); // Single-line input
        betInput.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        betInput.setToolTipText("Enter your bet here");
        betInput.setForeground(Color.BLACK);

        // Label for bet input
        JLabel betLabel = new JLabel("Enter Bet: ");
        betLabel.setForeground(Color.WHITE);
        betLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));

        // Add the label and text field to the betting panel
        bettingPanel.add(betLabel);
        bettingPanel.add(betInput);

        // MONEy Label
        moneyLabel = new JLabel("Money: " + PlayerStorage.getMoney());
        moneyLabel.setForeground(Color.WHITE);
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text horizontally
        moneyLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16)); // Set a consistent font for the money label
        moneyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 140, 0));

        // Create a new JPanel to center the money label
        JPanel moneyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // FlowLayout to center content
        moneyPanel.setOpaque(false); // Transparent background
        moneyPanel.add(moneyLabel); // Add the money label to the centered panel

        // Set the layout of bottomPanel to BoxLayout to stack components vertically
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);

        // Add the betting panel and money panel to the bottom panel
        bottomPanel.add(bettingPanel); // Add the betting input panel first
        bottomPanel.add(moneyPanel); // Add the centered money label below it
    }

    // VALIDATES THE BET //
    private void validateBet() {
        // avoid betting whiel spinning
        if (isSpinning) {
            JOptionPane.showMessageDialog(null, "You cannot bet while spinning!", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Validate input
            String input = betInput.getText().trim();
            int validateBet = Integer.parseInt(input);

            // bet cannot exceed current money or be less than or equal to 0
            if (validateBet > PlayerStorage.getMoney()) {
                throw new IllegalArgumentException("Bet exceeds available money!");
            } else if (validateBet <= 0) {
                throw new IllegalArgumentException("Bet must be greater than zero!");
            }

            // Update bet and clear input
            this.bet = validateBet;
            PlayerStorage.setMoney(PlayerStorage.getMoney() - this.bet);
            updateMoneyLabel();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // HELPER METHOD TO UPDATE THE MONEY LABEL
    private void updateMoneyLabel() {
        // refresh money label
        moneyLabel.setText("Money: " + PlayerStorage.getMoney());
    }
}