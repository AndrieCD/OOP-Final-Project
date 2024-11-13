package prototypes;

import java.util.Scanner;
import java.util.Random;

public class RoulettePrototype {

    public static class Cell {
        private int key;
        private int value;

        Cell(int key, int value) {
            this.key = key;
            this.value = value;
        }

        // Getters and setters
        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int money = 100;
        while (true) {

            Cell[][] betTable = new Cell[3][9];

            int keyCounter = 0;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 9; col++) {
                    betTable[row][col] = new Cell(keyCounter, 0);
                    keyCounter++;
                }
            }

            int bet = 0;
            System.out.println("Welcome to Roulette! ");
            System.out.println("Current Money: " + money + "\n");

            int betCell;
            int bets = 0;
            while (bets < 3) {
                for (int row = 0; row < betTable.length; row++) {
                    for (int col = 0; col < betTable[row].length; col++) {
                        System.out.print(betTable[row][col].getKey() + ":" + betTable[row][col].getValue() + "\t");
                    }
                    System.out.println();
                }

                System.out.print("Bet on a number (0 - 26): ");
                betCell = input.nextInt();

                try {
                    System.out.print("Enter bet: ");
                    bet = input.nextInt();
                    if (bet > money) {
                        throw new IllegalArgumentException("Bet exceeds available money!");
                    } else if (bet < 0)
                        throw new IllegalArgumentException("Bet can't be negative!");
                    money -= bet;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error! " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }

                for (int row = 0; row < betTable.length; row++) {
                    for (int col = 0; col < betTable[row].length; col++) {
                        if (betCell == betTable[row][col].getKey()) {
                            betTable[row][col].setValue(bet);
                        }
                    }
                }
                bets++;

                if (bets > 3) {
                    bets = 0;
                    break;
                }
            }

            Random rand = new Random();
            int winningBet = rand.nextInt(0, 17);

            System.out.println("The winning number is " + winningBet + "!");

            for (int row = 0; row < betTable.length; row++) {
                for (int col = 0; col < betTable[row].length; col++) {
                    if (betTable[row][col].getValue() != 0) {
                        if (betTable[row][col].getKey() == winningBet) {
                            money += (betTable[row][col].getValue() * 2);
                            System.out.println("You Won!");
                        }
                    }
                }
            }

        }
    }
}
