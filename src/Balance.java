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

    static int globalLoanPool; // package-wide visibility

    public Balance(JFrame frame) {
        super(frame);

        // recalibrate globalLoanPool if we're in debt
        globalLoanPool = 777 - PlayerStorage.getLoanAmount();
        System.out.println("Global loan pool: " + globalLoanPool);

        //layout setting for the boxes or grid to be 2x2
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, 10px gaps
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(290, 150, 195, 150)); // Padding around grid

        //current balance label
        balanceLabel = new JLabel("Current Balance: " + PlayerStorage.getMoney() + " coins");
        balanceLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanel.add(balanceLabel);

        //current loan label
        loanLabel = new JLabel("Current Loan: " + PlayerStorage.getLoanAmount() + " coins");
        loanLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        loanLabel.setForeground(Color.WHITE);
        loanLabel.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanel.add(loanLabel);

        //button for loan
        ImageIcon loanIcon = new ImageIcon("src/images/buttons/loan_button.png");
        loanButton = addButton(loanIcon, e -> promptLoanInput());
        buttonPanel.add(loanButton);

        //button for back
        ImageIcon backIcon = new ImageIcon("src/images/buttons/back_button.png");
        backButton = addButton(backIcon, e -> navigateTo(new GameMenu(frame)));
        buttonPanel.add(backButton);

        updateUIComponents();
    }

    //input for how much the user wants to loan
    private void promptLoanInput() {

        String input = JOptionPane.showInputDialog(frame, "Enter the loan amount (Available: " + globalLoanPool + "):",
                "Borrow Loan", JOptionPane.PLAIN_MESSAGE);
        if (input != null) {
            try {
                int amount = Integer.parseInt(input);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Loan amount must be greater than zero.", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                } else if (amount > globalLoanPool) {
                    JOptionPane.showMessageDialog(frame, "The loan amount exceeds the available loan pool.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {
                    loanMoney(amount);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Updating loan information
    private void loanMoney(int amount) {
        PlayerStorage.setLoanAmount(PlayerStorage.getLoanAmount() + amount);
        globalLoanPool -= amount;
        PlayerStorage.setMoney(PlayerStorage.getMoney() + amount);

        updateUIComponents();
        JOptionPane.showMessageDialog(frame, "Loan approved! You borrowed " + amount + " coins.", "Loan Approved",
                JOptionPane.INFORMATION_MESSAGE);

        checkBankruptcy();
    }

    // Updating balance and loan labels
    private void updateUIComponents() {
        balanceLabel.setText("Current Balance: " + PlayerStorage.getMoney() + " coins");
        loanLabel.setText("Current Loan: " + PlayerStorage.getLoanAmount() + " coins");
        loanButton.setEnabled(globalLoanPool > 0);
    }

    // Bankruptcy check

    public void triggerBankruptcyCheck() {
        checkBankruptcy(); // Calls the private method internally
    }

    private void checkBankruptcy() {
        if (PlayerStorage.getMoney() <= 0 && Balance.globalLoanPool <= 0) {
            JOptionPane.showMessageDialog(frame, "You are bankrupt! No money and no loans available.", "Bankruptcy",
                    JOptionPane.ERROR_MESSAGE);
            new GameOverFrame(frame);
        }
    }

}
