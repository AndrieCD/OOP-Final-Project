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
        moneyLabel = new JLabel();
        moneyLabel.setText("Money: " + Game.money);
        moneyLabel.setForeground(Color.WHITE);
        topPanel.add(moneyLabel, BorderLayout.EAST);

        // create the panel for the slot machine's icons
        JPanel iconPanel = new JPanel();
        iconPanel.setBorder(BorderFactory.createEmptyBorder(75, 75, 150, 75));
        iconPanel.setOpaque(false);

        // create the labels using the helper method
        icon1 = createIconLabel();
        icon2 = createIconLabel();
        icon3 = createIconLabel();

        // add the labels to the iconPanel
        iconPanel.add(icon1);
        iconPanel.add(icon2);
        iconPanel.add(icon3);

        backgroundImage.add(iconPanel, BorderLayout.CENTER);

        addButton("SPIN", e -> spin());
        addButton("BET", e -> bet());
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
        if (this.isSpinning == true) // disable betting when currently spinning
            return;

        while (true) {
            try {

                // create a textfield to get user input via GUI
                JTextField betField = new JTextField(5);

                // input prompt with an option pane / mini window
                int result = JOptionPane.showConfirmDialog(null, betField, "Enter Bet Amount",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // check if user OK or not
                if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                    System.out.println("Betting canceled.");
                    return; // exit from this method
                }

                // convert the string from textfield to an integer
                int validateBet = Integer.parseInt(betField.getText().trim()); // get the text from the field then
                                                                               // remove spaces

                // check if bet is valid
                if (validateBet > money) {
                    // this is the exception and its error message
                    throw new IllegalArgumentException("Bet exceeds available money!");
                } else if (validateBet <= 0) {
                    // this is the exception and its error message
                    throw new IllegalArgumentException("Bet must be a positive amount!");
                }

                // if the player changes their bet, the previous bet
                // (if there's any, hence we check if its not 0) will be added to money
                if (this.bet != 0)
                    Game.money += this.bet;

                // if input is valid, assign the value to the primary bet variable
                this.bet = validateBet;
                System.out.println("Bet placed: " + this.bet);

                // subtract the bet from the player's money
                Game.money -= this.bet;
                // refresh money label
                moneyLabel.setText("Money: " + Game.money);
                break;

            } catch (NumberFormatException e) {
                // if the input is not a number, show this error message --> from parseInt()
                // example, input = "abc" or "1.2"
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter an integer.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                // if bet exceeds the bet range, show catch the exception
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}