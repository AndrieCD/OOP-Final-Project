// MAIN.JAVA

import java.awt.event.*; // provides us with the classes and fields for GUI creation and management
import javax.swing.*; // allows us to detect and manage events

public class Main implements BaseDisplay {

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

        PlayerStorage.loadData(); // load data on launch, if there's any

        // on 'X' button press on window/frame...
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                PlayerStorage.saveData(); // save data
                System.exit(0); // we exit the program

            }
        });

        
    }
}