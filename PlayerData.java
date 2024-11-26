public interface PlayerData {
    int getTotalSpins();

    int getTotalEarnings();

    int getTotalLosses();

    int getMoney();

    void setTotalSpins(int totalSpins);

    void setTotalEarnings(int totalEarnings);

    void setTotalLosses(int totalLosses);

    void setMoney(int money);
}

class PlayerStorage {
    private static int totalSpins;
    private static int totalEarnings;
    private static int totalLosses;
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