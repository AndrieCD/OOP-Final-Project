import javax.swing.*;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lucky9 extends Game 
{
    private HashMap<String, String> cardImages;
    private String[] icons;
    private JTextField betInput;
    private JPanel bottomPanel;
    private JLabel moneyLabel;
    private ArrayList<String> playerHand;
    private ArrayList<String> dealerHand;
    private int bet;
    private JPanel playerHandPanel;
    private JPanel dealerHandPanel;
    
        public Lucky9(JFrame frame) 
        {
            super(frame);

            // Initialize card icons and hands
            icons = new String[]{"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
            playerHand = new ArrayList<>();
            dealerHand = new ArrayList<>();
            cardImages = new HashMap<>();

            // Load card images
            loadCardImages();

            // Bottom panel with hands and money label
            bottomPanel = new JPanel(new BorderLayout());

            playerHandPanel = new JPanel(new FlowLayout());
            dealerHandPanel = new JPanel(new FlowLayout());

            moneyLabel = new JLabel("Money: " + PlayerStorage.getMoney());
            moneyLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));

            bottomPanel.add(new JLabel("Player Hand:"), BorderLayout.WEST);
            bottomPanel.add(playerHandPanel, BorderLayout.CENTER);
            bottomPanel.add(new JLabel("Dealer Hand:"), BorderLayout.EAST);
            bottomPanel.add(dealerHandPanel, BorderLayout.SOUTH);
            bottomPanel.add(moneyLabel, BorderLayout.NORTH);

            frame.add(bottomPanel, BorderLayout.CENTER);
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
            updateHandDisplay(playerHand, "Player");
        }
    
        private void dealCardToDealer() {
            String card = drawRandomCard();
            dealerHand.add(card); // Add card to dealer's hand
            updateHandDisplay(dealerHand, "Dealer");
        }
    
        private String drawRandomCard() {
            // Randomly select a card from the icons array
            Random rand = new Random();
            return icons[rand.nextInt(icons.length)];
        }

        private void updateHandDisplay(ArrayList<String> hand, String handOwner) {
            // Create or update GUI display for the hand (e.g., in a panel or label)
            StringBuilder handDisplay = new StringBuilder(handOwner + " Hand: ");
            for (String card : hand) {
                handDisplay.append(card).append(" ");
            }
            System.out.println(handDisplay.toString()); // For debugging, replace with GUI update logic
        }

        public void startGame() {
            // Clear hands for a new game
            playerHand.clear();
            dealerHand.clear();
    
            // Deal initial cards
            dealCardToPlayer();
            dealCardToDealer();
            dealCardToPlayer();
            dealCardToDealer();

            // Update GUI
            updateHandDisplay(playerHand, playerHandPanel);
            updateHandDisplay(dealerHand, dealerHandPanel);

            // Calculate and display scores
            int playerScore = calculateHandScore(playerHand);
            int dealerScore = calculateHandScore(dealerHand);

            JOptionPane.showMessageDialog(null, "Player Score: " + playerScore + "\nDealer Score: " + dealerScore,
                    "Game Start", JOptionPane.INFORMATION_MESSAGE);

            // Determine the winner (optional logic)
            determineWinner(playerScore, dealerScore);
        }

        private void determineWinner(int playerScore, int dealerScore) {
            if (playerScore > dealerScore) {
                JOptionPane.showMessageDialog(null, "Player wins!", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else if (dealerScore > playerScore) {
                JOptionPane.showMessageDialog(null, "Dealer wins!", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "It's a tie!", "Result", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        

         // ---------BET METHOD------------//
         protected void bet() 
         {
            // Create a panel for the betting GUI
            JPanel bettingPanel = new JPanel(new FlowLayout());
            bettingPanel.setOpaque(false); // Transparent background
    
            // Input field for the bet amount
            betInput = new JTextField(10); // Single-line input
            betInput.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
            betInput.setToolTipText("Enter your bet here");

            // Add components to the betting panel
            bettingPanel.add(new JLabel("Enter Bet:"));
            bettingPanel.add(betInput);

            // Add the betting panel to the top of the bottom panel
            bottomPanel.add(bettingPanel, BorderLayout.NORTH);
        }


        // VALIDATES THE BET //
        private void validateBet() 
        {
        
            try 
            {
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

            } catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) 
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }


        }
        //calculate the scores of the hand
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



        // HELPER METHOD TO UPDATE THE MONEY LABEL
        private void updateMoneyLabel() 
        {
            // refresh money label
            moneyLabel.setText("Money: " + PlayerStorage.getMoney());
        }

        private void updateHandDisplay(ArrayList<String> hand, JPanel handPanel) {
            handPanel.removeAll(); // Clear previous cards
        
            for (String card : hand) {
                String imagePath = cardImages.get(card); // Get the image path for the card
                ImageIcon imgIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH));
                JLabel cardLabel = new JLabel(imgIcon); // Create JLabel for the card
                handPanel.add(cardLabel); // Add the card to the panel
            }
        
            handPanel.revalidate(); // Refresh panel
            handPanel.repaint();
        }



}
