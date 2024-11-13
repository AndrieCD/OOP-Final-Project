package prototypes;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SlotPrototype {
    public static void main(String[] args) {

        // store our icons in an array
        String[] icons = { "Seven", "Cherry", "Bar", "Star" };

        Scanner input = new Scanner(System.in);

        int money = 100;

        // in a hashmap or dictionary, we assign a multiplier value for each pattern
        HashMap<String, Integer> winningPatterns = new HashMap<String, Integer>();
        winningPatterns.put("SevenSevenSeven", 35);
        winningPatterns.put("BarBarBar", 15);
        winningPatterns.put("CherryStarCherry", 10);
        winningPatterns.put("CherryBarStar", 5);
        winningPatterns.put("CherryCherryCherry", 2);

        while (true) {
            int bet = 0;
            int multiplier = -1;
            System.out.println("Current Money " + money);

            while (true) {
                // we validate our input using exception handling
                try {
                    System.out.print("Enter bet: ");
                    bet = input.nextInt();
                    if (bet > money) {
                        throw new IllegalArgumentException("Bet exceeds available money!");
                    } else if (bet < 0)
                        throw new IllegalArgumentException("Bet can't be negative!");
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error! " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            // we randomize the output of each column
            String col1, col2, col3, pattern;
            Random rand = new Random();
            col1 = icons[rand.nextInt(icons.length)];
            col2 = icons[rand.nextInt(icons.length)];
            col3 = icons[rand.nextInt(icons.length)];

            pattern = col1 + col2 + col3;

            System.out.println("Pattern: " + pattern);

            // we check for a match of pattern
            for (String match : winningPatterns.keySet()) {
                if (match.equals(pattern)) {
                    multiplier = winningPatterns.get(match);
                }
            }
            // we add the multiplied bet to current money
            money += (bet * multiplier);

            System.out.println("Multiplier: " + multiplier);
            System.out.println("Total Earning: " + (bet * multiplier));
        }
    }
}
