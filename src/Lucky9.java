import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

public class Lucky9 extends Game {
    private HashMap<String, String> cardImages;
    private String[] icons;
    private JTextField betInput;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JLabel moneyLabel;
    private JLabel playerHandLabel;
    private JLabel dealerHandLabel;
    private ArrayList<String> playerHand;
    private ArrayList<String> dealerHand;
    private int bet;
    private JPanel playerHandPanel;
    private JPanel dealerHandPanel;

    public Lucky9(JFrame frame) {
        super(frame);

        // Initialize card icons and hands
        icons = new String[] { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        cardImages = new HashMap<>();

        // Load card images
        loadCardImages();

        topPanel = new JPanel(new BorderLayout());

        playerHandPanel = new JPanel(new FlowLayout());
        dealerHandPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Player Hand:"), BorderLayout.WEST);
        topPanel.add(playerHandPanel, BorderLayout.CENTER);
        topPanel.add(new JLabel("Dealer Hand:"), BorderLayout.EAST);
        topPanel.add(dealerHandPanel, BorderLayout.SOUTH);

        frame.add(topPanel, BorderLayout.CENTER);

        // Initialize the game display
        initializeDisplay();
    }

    private void loadCardImages() {

        cardImages.put("Ace", "src/images/ace.png");
        cardImages.put("2", "src/images/2.png");
        cardImages.put("3", "src/images/3.png");
        cardImages.put("4", "src/images/4.png");
        cardImages.put("5", "src/images/5.png");
        cardImages.put("6", "src/images/6.png");
        cardImages.put("7", "src/images/7.png");
        cardImages.put("8", "src/images/8.png");
        cardImages.put("9", "src/images/9.png");
        cardImages.put("10", "src/images/10.png");
        cardImages.put("Jack", "src/images/jack.png");
        cardImages.put("Queen", "src/images/queen.png");
        cardImages.put("King", "src/images/king.png");

    }

    private void dealCardToPlayer() {
        String card = drawRandomCard();
        playerHand.add(card); // Add card to player's hand
    }

    private void dealCardToDealer() {
        String card = drawRandomCard();
        dealerHand.add(card); // Add card to dealer's hand
    }

    private String drawRandomCard() {
        // Randomly select a card from the icons array
        Random rand = new Random();
        return icons[rand.nextInt(icons.length)];
    }
    

    private void startGame() {
        // Clear hands for a new game
        playerHand.clear();
        dealerHand.clear();

        // Deal initial cards
        dealCardToPlayer();
        dealCardToDealer();
        dealCardToPlayer();
        dealCardToDealer();


        // Calculate and display scores
        int playerScore = calculateHandScore(playerHand);
        int dealerScore = calculateHandScore(dealerHand);

        //for natural 9s
        
        if (playerScore == 9)
        {
            showPlayerHand(playerHand);

            showDealerHand(dealerHand);

            JOptionPane.showMessageDialog(null, 
                                                "Player Natural 9! Player Wins!",
                                                "Game Result", 
                                                JOptionPane.INFORMATION_MESSAGE);

            determineWinner(playerScore, dealerScore);

            processWinLoss();
         }

        showPlayerHand(playerHand);

        showDealerHand(dealerHand);

         //ask player if they want to draw another card
        var selection = JOptionPane.showConfirmDialog(null, "Do you want to draw another card?", "Draw Card?", JOptionPane.YES_NO_OPTION);
        if (selection == 0) 
        {
            //deal card to player
            dealCardToPlayer();
            //show players new hand
            showPlayerHand(playerHand);
            //show dealers both cards
            showDealerHandFinal(dealerHand);
            //recalculate player score
            playerScore = calculateHandScore(playerHand);
        }
        //show score
        JOptionPane.showMessageDialog(null, "Player Score: " + playerScore + "\nDealer Score: " + dealerScore,
                "Score", JOptionPane.INFORMATION_MESSAGE);

        //if dealer  has less than or equal to 4 value of cards they draw one more card
        if (dealerScore <= 4){
            JOptionPane.showMessageDialog(null, 
                                                "Dealer score is less than or equal to 4",
                                                "Dealer Draws", 
                                                JOptionPane.INFORMATION_MESSAGE);
            
            //add new card to dealer's hand
            dealCardToDealer();
            //show player and dealers final hand
            showPlayerHand(playerHand);

            showDealerHandFinal(dealerHand);
            //recalculate score
            dealerScore = calculateHandScore(dealerHand);

            JOptionPane.showMessageDialog(null, "Player Score: " + playerScore + "\nDealer Score: " + dealerScore,
                "Score", JOptionPane.INFORMATION_MESSAGE);
        } 

        // Determine the winner (optional logic)
        determineWinner(playerScore, dealerScore);

        processWinLoss();
    }
    // used to process who wins the game
    private void determineWinner(int playerScore, int dealerScore) {
        if (playerScore > dealerScore) {
            JOptionPane.showMessageDialog(null, "Player wins!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else if (dealerScore > playerScore) {
            JOptionPane.showMessageDialog(null, "Dealer wins!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "It's a tie!", "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // --------- BET METHOD ------------ //
    // ginaya q nlng ung bet method ng slotMachine -Crishia
    protected void bet() {
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

        // MONEY Label
        moneyLabel = new JLabel("Money: " + PlayerStorage.getMoney());
        moneyLabel.setForeground(Color.WHITE);
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text horizontally
        moneyLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16)); // Set a consistent font for the money label
        moneyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

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
        bottomPanel.add(buttonPanel); // Add the buttonPanel(betButton) below it

        // BET BUTTON
        addButton("BET", e -> validateBet());

        // Setup the BET button style
        JButton betButton = (JButton) getButtonFromPanel(buttonPanel, "BET");
        if (betButton != null) {
            betButton.setBackground(new Color(235, 58, 48));
            betButton.setForeground(Color.WHITE); // Set text color to white
            betButton.setFont(new Font("Trebuchet MS", Font.BOLD, 18)); // Set font style and size
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 200, 0)); // 10px top margin, no margin on
                                                                                   // other sides
        }
    }

    // VALIDATES THE BET //
    private void validateBet() {
        try {
            // Validate input
            String input = betInput.getText().trim();
            int validateBet = Integer.parseInt(input);

            if (validateBet > PlayerStorage.getMoney()) {
                throw new IllegalArgumentException("Bet exceeds available money!");
            } else if (validateBet <= 0) {
                throw new IllegalArgumentException("Bet must be greater than zero!");
            }

            // Update bet and clear input
            this.bet = validateBet;
            PlayerStorage.setMoney(PlayerStorage.getMoney() - this.bet);
            updateMoneyLabel();
            startGame(); // nilipat ko dto ung startgame() para mag validate muna ng input bet bago
                         // magstart ung game

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Calculate the scores of the hand
    private int calculateHandScore(ArrayList<String> hand) {
        int total = 0;

        for (String card : hand) {
            switch (card) {
                case "Ace":
                    total += 1;
                    break;
                case "Jack":
                case "Queen":
                case "King":
                    total += 0;
                    break;
                default:
                    total += Integer.parseInt(card); // Parse numeric card values
            }
        }

        // Modulo 10 to get the Lucky 9 score
        return total % 10;
    }

    // Helper method to update the money label
    private void updateMoneyLabel() {
        moneyLabel.setText("Money: " + PlayerStorage.getMoney());
    }

    private void initializeDisplay() {
        // Create a label to display the player's money, set properties
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        background.add(bottomPanel, BorderLayout.SOUTH);
        /* 
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        */
        
        
        playerHandLabel = new JLabel("Player Hand: [ ]");
        playerHandLabel.setForeground(Color.WHITE);
        playerHandLabel.setHorizontalAlignment(SwingConstants.LEFT); // Center the text horizontally
        playerHandLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));  // Set a consistent font for the money label
        playerHandLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        dealerHandLabel = new JLabel("Dealer Hand: [ ]");
        dealerHandLabel.setForeground(Color.WHITE);
        dealerHandLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Center the text horizontally
        dealerHandLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));  // Set a consistent font for the money label
        dealerHandLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));



        this.playerHandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.dealerHandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        playerHandPanel.setOpaque(false); 
        playerHandPanel.add(playerHandLabel);
        dealerHandPanel.setOpaque(false); 
        dealerHandPanel.add(dealerHandLabel);


        bottomPanel.add(playerHandPanel);

        bottomPanel.add(dealerHandPanel);

        
        bet();
    }


    private void showPlayerHand(ArrayList<String> playerHand)  
    {

        String playercards = "";
        for(String card : playerHand)
        {
            playercards += card + " ";
        }

        playerHandLabel.setText("Player Hand: [ " + playercards + "]");
    } 

    private void showDealerHand(ArrayList<String> dealerHand)
    {
        String dealercards = dealerHand.get(0);

        dealerHandLabel.setText("Dealer Hand: [ " + dealercards + " ??? ]");
    } 

    private void showDealerHandFinal(ArrayList<String> dealerHand)
    {
        String dealercards = "";
        for(String card : dealerHand)
        {
            dealercards += card + " ";
        }

        dealerHandLabel.setText("Dealer Hand: [ " + dealercards + "]");
    } 
    private JButton getButtonFromPanel(JPanel panel, String buttonText) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (button.getText().equals(buttonText)) {
                    return button; // Return the button if its text matches "BET"
                }
            }
        }
        return null; // Return null if button is not found
    }
}
