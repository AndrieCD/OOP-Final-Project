import javax.swing.*;
import java.awt.*;
// import java.awt.event.ActionEvent;

public class MainMenu extends BaseMenu {

    public MainMenu(JFrame frame) {
        super(frame);

        // add buttons ([STRING_LABEL] , [ACTION])
        addButton("START", e -> navigateTo(new GameMenu(frame)));
        addButton("SCORES", e -> JOptionPane.showMessageDialog(frame, "scorerere"));
        addButton("EXIT", e -> System.exit(0));
    }
}
