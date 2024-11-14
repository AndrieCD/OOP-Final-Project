import javax.swing.*;
import java.util.HashMap;
import java.util.Random;

// -------------------------------------------------------------------------
// -------------------S--L--O--T----M--A--C--H--I--N--E---------------------
// -------------------------------------------------------------------------
public class SlotMachine extends Game {
    public SlotMachine(JFrame frame) {
        super(frame);
        // store our icons in an array
        String[] icons = { "Seven", "Cherry", "Bar", "Star" };
        // in a hashmap or dictionary, we assign a multiplier value for each pattern
        HashMap<String, Integer> winningPatterns = new HashMap<String, Integer>();
        // PAIR IS < PATTERN , MULTIPLIER >
        winningPatterns.put("SevenSevenSeven", 35); // triple 7 pattern is 35x multiplier
        winningPatterns.put("BarBarBar", 15);
        winningPatterns.put("CherryStarCherry", 10);
        winningPatterns.put("CherryBarStar", 5);
        winningPatterns.put("CherryCherryCherry", 2);
    }

    public void bet() {
        // while (true) {
        // // we validate our input using exception handling
        // try {
        // System.out.print("Enter bet: ");
        // JTextField betField = new JTextField(5);

        // bet = input.nextInt();
        // if (bet > money) {
        // throw new IllegalArgumentException("Bet exceeds available money!");
        // } else if (bet < 0)
        // throw new IllegalArgumentException("Bet can't be negative!");
        // break;
        // } catch (IllegalArgumentException e) {
        // System.out.println("Error! " + e.getMessage());
        // } catch (Exception e) {
        // System.out.println("Invalid input. Please enter a valid number.");
        // }
        // }
    }

    // public void spin() {}
}