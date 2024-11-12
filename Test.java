import javax.swing.*; // provides us with the classes and fields for GUI creation and management

public class Test {
    public static void main(String[] args) {
        // create our system's window
        JFrame frame = new JFrame("Multi-Gambling System"); // create the window with the title
        frame.setSize(1280, 720); // size of window
        frame.setLocationRelativeTo(null); // relative to null, center
        frame.setResizable(false); // window is not resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close on exit

        MainMenu mainMenu = new MainMenu(frame); // display main menu
        frame.add(mainMenu); // add main menu to our window
        frame.setVisible(true); // make the window visible
    }
}
