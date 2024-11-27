import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerStorage {
    private static int totalSpins = 0;
    private static int totalEarnings = 0;
    private static int totalLosses = 0;
    private static int money = 777;

    public static int getTotalSpins() {
        return PlayerStorage.totalSpins;
    }

    public static void setTotalSpins(int spins) {
        PlayerStorage.totalSpins = spins;
    }

    public static int getTotalEarnings() {
        return PlayerStorage.totalEarnings;
    }

    public static void setTotalEarnings(int earnings) {
        PlayerStorage.totalEarnings = earnings;
    }

    public static int getTotalLosses() {
        return PlayerStorage.totalLosses;
    }

    public static void setTotalLosses(int losses) {
        PlayerStorage.totalLosses = losses;
    }

    public static int getMoney() {
        return PlayerStorage.money;
    }

    public static void setMoney(int newMoney) {
        PlayerStorage.money = newMoney;
    }

    // SAVE OUR PLAYER'S DATA
    public static void saveData() {
        try {
            FileWriter plrData = new FileWriter("playerData.txt");

            plrData.write(PlayerStorage.getTotalSpins() + "\n");
            plrData.write(PlayerStorage.getTotalEarnings() + "\n");
            plrData.write(PlayerStorage.getTotalLosses() + "\n");
            plrData.write(PlayerStorage.getMoney() + "\n");

            plrData.close();
            System.out.println("Player data saved successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the data.");
        }
        printData();
    }

    public static void loadData() {
        try {
            BufferedReader plrData = new BufferedReader(new FileReader("playerData.txt"));

            String line;
            int lineNumber = 0;
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
                }
                lineNumber++;
            }
            plrData.close();
            printData();
        } catch (IOException e) {
            System.out.println("Player data not found.");
        }
    }

    public static void printData() {
        System.out.println(getTotalSpins());
        System.out.println(getTotalEarnings());
        System.out.println(getTotalLosses());
        System.out.println(getMoney());
    }
}