import javax.swing.*; // provides us with the classes and fields for GUI creation and management
import java.awt.event.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main implements BaseDisplay {

    // SAVE OUR PLAYER'S DATA
    private static void saveData() {
        // System.out.println(PlayerStorage.getTotalSpins());
        // System.out.println(PlayerStorage.getTotalEarnings());
        // System.out.println(PlayerStorage.getTotalLosses());
        // System.out.println(PlayerStorage.getMoney());
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
    }

    private static void loadData() {
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

            System.out.println(PlayerStorage.getTotalSpins());
            System.out.println(PlayerStorage.getTotalEarnings());
            System.out.println(PlayerStorage.getTotalLosses());
            System.out.println(PlayerStorage.getMoney());
        } catch (IOException e) {
            System.out.println("An error occurred while loading player data.");
        }
    }

    public static void main(String[] args) {
        // create our system's window
        JFrame frame = new JFrame("Multi-Gambling System"); // create the window with the title
        frame.setSize(BaseDisplay.width, BaseDisplay.height); // size of window
        frame.setLocationRelativeTo(null); // relative to null, center
        frame.setResizable(false); // window is not resizable
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // do nothing so we can save first before closing

        MainMenu mainMenu = new MainMenu(frame); // display main menu
        frame.add(mainMenu); // add main menu to our window
        frame.pack();
        frame.setVisible(true); // make the window visible

        loadData(); // load data on launch, if there's any

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                saveData(); // save data
                System.exit(0);

            }
        });

    }
}
