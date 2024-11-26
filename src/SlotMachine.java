import javax.swing.*;
import java.util.HashMap;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// -------------------------------------------------------------------------
// -------------------S--L--O--T----M--A--C--H--I--N--E---------------------
// -------------------------------------------------------------------------
public class SlotMachine extends Game {
    private final HashMap<String, Double> winningPatterns;
    private final Random random = new Random();
    private final String[] icons;
    private boolean isSpinning;
    private JLabel icon1, icon2, icon3;
    private JLabel moneyLabel;
    private String[] result;
    private JPanel bottomPanel;

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
        JLabel label = new JLabel(icons[random.nextInt(icons.length)]);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        return label;
    }

    private void initializeDisplay() {
        // create a label to display the player's money, set properties
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        moneyLabel = new JLabel("Money: " + Game.money);
        moneyLabel.setForeground(Color.WHITE);
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.add(moneyLabel, BorderLayout.SOUTH);
        background.add(bottomPanel, BorderLayout.SOUTH);

        // create the panel for the slot machine's icons
        JPanel iconPanel = new JPanel();
        iconPanel.setBorder(BorderFactory.createEmptyBorder(200, 75, 0, 75));
        iconPanel.setOpaque(false);

        // create the labels using the helper method
        icon1 = createIconLabel();
        icon2 = createIconLabel();
        icon3 = createIconLabel();

        // add the labels to the iconPanel
        iconPanel.add(icon1);
        iconPanel.add(icon2);
        iconPanel.add(icon3);

        background.add(iconPanel, BorderLayout.CENTER);

        addButton("SPIN", e -> spin());
        bet();
    }

    // ---------SPIN METHOD------------//
    // ---------SPIN METHOD------------//
    private void spin() {
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
                icon1.setText(icons[random.nextInt(icons.length)]); // random spin icon1
                if (System.currentTimeMillis() - startTime >= spinDuration) {
                    timer1.stop(); // stop spinning after 2s
                }
            }
        });

        timer2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                icon2.setText(icons[random.nextInt(icons.length)]); // random spin icon2
                if (System.currentTimeMillis() - startTime >= spinDuration * 1.5) {
                    timer2.stop(); // stop spinning after 2s
                }
            }
        });

        timer3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                icon3.setText(icons[random.nextInt(icons.length)]); // random spin icon3
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
        // set the result from the randomized spin
        result[0] = icon1.getText();
        result[1] = icon2.getText();
        result[2] = icon3.getText();

        // apply multiplier
        this.multiplier = calculateMultiplier(result);

        ///// FOR TESTING ONLY-------------- CONSOLE PRINTS
        System.out.println("Result: " + result[0] + " " + result[1] + " " + result[2]);
        System.out.println("Multiplier: " + this.multiplier);

        Game.money += (this.bet * this.multiplier);
        this.bet = 0;
        // refresh money label
        moneyLabel.setText("Money: " + Game.money);

        this.isSpinning = false;
    }

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
        JPanel bettingPanel = new JPanel(new FlowLayout());
        bettingPanel.setOpaque(false); // Transparent background

        // Input field for the bet amount
        JTextField betInput = new JTextField(10); // Single-line input
        betInput.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        betInput.setToolTipText("Enter your bet here");

        // Confirm bet button
        JButton confirmBetButton = new JButton("Confirm Bet");
        confirmBetButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        confirmBetButton.setBackground(new Color(0, 128, 0)); // Green
        confirmBetButton.setForeground(Color.WHITE);

        // Add components to the betting panel
        bettingPanel.add(new JLabel("Enter Bet:"));
        bettingPanel.add(betInput);
        bettingPanel.add(confirmBetButton);

        // Add the betting panel to the top of the bottom panel
        bottomPanel.add(bettingPanel, BorderLayout.NORTH);

        // Action listener for the confirm bet button
        confirmBetButton.addActionListener(e -> {
            if (isSpinning) {
                JOptionPane.showMessageDialog(null, "You cannot bet while spinning!", "Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Validate input
                String input = betInput.getText().trim();
                int validateBet = Integer.parseInt(input);

                if (validateBet > Game.money) {
                    throw new IllegalArgumentException("Bet exceeds available money!");
                } else if (validateBet <= 0) {
                    throw new IllegalArgumentException("Bet must be greater than zero!");
                }

                // Update bet and clear input
                this.bet = validateBet;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}