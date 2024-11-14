import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;

public class GameMenu extends BaseMenu {

    public GameMenu(JFrame frame) {
        super(frame);

        // add buttons ([STRING_LABEL] , [ACTION])
        addButton("slotButton", e -> navigateTo(new SlotMachine(frame)));
        addButton("luckyButton", e -> JOptionPane.showMessageDialog(frame, "luckyy"));
        addButton("rouletteButton", e -> JOptionPane.showMessageDialog(frame, "scorerere"));
        addButton("balButton", e -> JOptionPane.showMessageDialog(frame, "balbalbal"));
        addButton("BACK", e -> navigateTo(new MainMenu(frame)));
    }
}
