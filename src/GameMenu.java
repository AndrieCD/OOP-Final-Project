import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;

public class GameMenu extends BaseMenu {

    public GameMenu(JFrame frame) {
        super(frame);

        // Add buttons with their respective actions
        addButton("slotButton", e -> JOptionPane.showMessageDialog(frame, "slottt"));
        addButton("luckyButton", e -> JOptionPane.showMessageDialog(frame, "luckyy"));
        addButton("rouletteButton", e -> JOptionPane.showMessageDialog(frame, "scorerere"));
        addButton("balButton", e -> JOptionPane.showMessageDialog(frame, "balbalbal"));
        addButton("BACK", e -> back());
    }

    private void back() {
        MainMenu mainMenu = new MainMenu(frame);
        frame.getContentPane().removeAll();
        frame.add(mainMenu);
        frame.revalidate();
        frame.repaint();
    }
}
