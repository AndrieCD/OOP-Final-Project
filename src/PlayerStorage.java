import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerStorage {
    // static fields so we'll only have one source (not separate objects)
    private static int totalSpins = 0;
    private static int totalEarnings = 0;
    private static int totalLosses = 0;
    private static int money = 777;
    private static int loanAmount = 0; // Added loan amount tracking

    // getters
    public static int getTotalSpins() {
        return PlayerStorage.totalSpins;
    }

    public static int getTotalEarnings() {
        return PlayerStorage.totalEarnings;
    }

    public static int getLoanAmount() {
        return PlayerStorage.loanAmount;
    }

    public static int getMoney() {
        return PlayerStorage.money;
    }

    public static int getTotalLosses() {
        return PlayerStorage.totalLosses;
    }

    // setters
    public static void setTotalEarnings(int earnings) {
        PlayerStorage.totalEarnings = earnings;
    }

    public static void setTotalSpins(int spins) {
        PlayerStorage.totalSpins = spins;
    }

    public static void setTotalLosses(int losses) {
        PlayerStorage.totalLosses = losses;
    }

    public static void setMoney(int newMoney) {
        PlayerStorage.money = newMoney;
    }

    public static void setLoanAmount(int newLoan) {
        PlayerStorage.loanAmount = newLoan;
    }

    // SAVE OUR PLAYER'S DATA
    public static void saveData() {
        try {
            FileWriter plrData = new FileWriter("playerData.txt"); // open or create our file

            // write the following data to the file
            plrData.write(PlayerStorage.getTotalSpins() + "\n");
            plrData.write(PlayerStorage.getTotalEarnings() + "\n");
            plrData.write(PlayerStorage.getTotalLosses() + "\n");
            plrData.write(PlayerStorage.getMoney() + "\n");
            plrData.write(PlayerStorage.getLoanAmount() + "\n");

            plrData.close();
            System.out.println("data saved successfully!");
        } catch (IOException e) {
            System.out.println("error saving.");
        }
        // printData();
    }

    public static void loadData() {
        try {
            // open the file
            BufferedReader plrData = new BufferedReader(new FileReader("playerData.txt"));

            String line; // we'll read per line
            int lineNumber = 0;
            // for each line, we'll save the value in our static fields
            while ((line = plrData.readLine()) != null) {
                switch (lineNumber) {
                    case 0:
                        PlayerStorage.setTotalSpins(Integer.parseInt(line));
                        break;
                    case 1:
                        PlayerStorage.setTotalEarnings(Integer.parseInt(line));
                        break;
                    case 2:
                        PlayerStorage.setTotalLosses(Integer.parseInt(line));
                        break;
                    case 3:
                        PlayerStorage.setMoney(Integer.parseInt(line));
                        break;
                    case 4:
                        PlayerStorage.setLoanAmount(Integer.parseInt(line));
                        break;
                }
                lineNumber++; // to change the value that will receive a line
            }
            plrData.close();
            printData(); // FOR TESTING ONLY TO CHECK IF ITS WORKING
        } catch (IOException e) {
            System.out.println("data not found.");
        }
    }

    // testing method
    public static void printData() {
        System.out.println(getTotalSpins());
        System.out.println(getTotalEarnings());
        System.out.println(getTotalLosses());
        System.out.println(getMoney());
        System.out.println(getLoanAmount());
    }
}
