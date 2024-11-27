/////////////////////
///
///
///
///
///
///
/// 
//drafting
//needs improvement:
// saving money thingy
// bankruptcy notification
// D KO PA MAEXTEND TO PLAYER DATA, I NEED THE FORMATTING WITH JLABEL BUTTONS
// current balance not set yet, d rin masyadong connected sa playerdata/playerstorage
// when exited, di nagrerestart ung game

import java.awt.*;
import javax.swing.*;

class Balance extends BaseMenu {
    private JLabel balanceLabel;
    private JLabel loanLabel;
    private JButton loanButton;
    private JButton backButton;

    private static int globalLoanPool = 777; 
    private int loanAmount; // current loan

    public Balance(JFrame frame) {
        super(frame);

        loanAmount = 0;

        // Set layout to a 2x2 grid
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, 10px gaps
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(290, 150, 195, 150)); // Padding around grid

        // Label for current balance
        balanceLabel = new JLabel("Current Balance: " + PlayerStorage.getMoney() + " coins");
        balanceLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanel.add(balanceLabel);

        // Label for current loan
        loanLabel = new JLabel("Current Loan: " + loanAmount + " coins");
        loanLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        loanLabel.setForeground(Color.WHITE);
        loanLabel.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanel.add(loanLabel);

        // Loan button
        ImageIcon loanIcon = new ImageIcon("src/images/buttons/loan_button.png");
        loanButton = addButton(loanIcon, e -> promptLoanInput());
        buttonPanel.add(loanButton);

        // Back button
        ImageIcon backIcon = new ImageIcon("src/images/buttons/back_button.png");
        backButton = addButton(backIcon, e -> navigateTo(new GameMenu(frame)));
        buttonPanel.add(backButton);

        updateUIComponents();
    }

    // Inputting how much the user wants to loan
    private void promptLoanInput() {
        if (globalLoanPool <= 0) {
            JOptionPane.showMessageDialog(frame, "The loan pool is empty. No more loans can be taken.", "Loan Unavailable", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String input = JOptionPane.showInputDialog(frame, "Enter the loan amount (Available: " + globalLoanPool + "):", "Borrow Loan", JOptionPane.PLAIN_MESSAGE);
        if (input != null) {
            try {
                int amount = Integer.parseInt(input);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Loan amount must be greater than zero.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else if (amount > globalLoanPool) {
                    JOptionPane.showMessageDialog(frame, "The loan amount exceeds the available loan pool.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {
                    loanMoney(amount);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Updating loan information
    private void loanMoney(int amount) {
        loanAmount += amount;
        globalLoanPool -= amount;
        PlayerStorage.setMoney(PlayerStorage.getMoney() + amount);

        updateUIComponents();
        JOptionPane.showMessageDialog(frame, "Loan approved! You borrowed " + amount + " coins.", "Loan Approved", JOptionPane.INFORMATION_MESSAGE);

        checkBankruptcy();
    }

    // Updating balance and loan labels
    private void updateUIComponents() {
        balanceLabel.setText("Current Balance: " + PlayerStorage.getMoney() + " coins");
        loanLabel.setText("Current Loan: " + loanAmount + " coins");
        loanButton.setEnabled(globalLoanPool > 0);
    }

    // Bankruptcy check
    private void checkBankruptcy() {
        if (PlayerStorage.getMoney() <= 0 && globalLoanPool <= 0) {
            JOptionPane.showMessageDialog(frame, "You are bankrupt! No money and no loans available.", "Bankruptcy", JOptionPane.ERROR_MESSAGE);
        }
    }
}
