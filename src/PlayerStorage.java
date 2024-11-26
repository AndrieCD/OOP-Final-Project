public class PlayerStorage {
    private static int totalSpins = 0;
    private static int totalEarnings = 0;
    private static int totalLosses = 0;
    private static int money = 777;

    public static int getTotalSpins() {
        return totalSpins;
    }

    public static void setTotalSpins(int spins) {
        totalSpins = spins;
    }

    public static int getTotalEarnings() {
        return totalEarnings;
    }

    public static void setTotalEarnings(int earnings) {
        totalEarnings = earnings;
    }

    public static int getTotalLosses() {
        return totalLosses;
    }

    public static void setTotalLosses(int losses) {
        totalLosses = losses;
    }

    public static int getMoney() {
        return money;
    }

    public static void setMoney(int newMoney) {
        money = newMoney;
    }
}