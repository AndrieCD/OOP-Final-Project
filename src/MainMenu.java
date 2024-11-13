import javax.swing.*;
import java.awt.*;
// import java.awt.event.ActionEvent;

public class MainMenu extends BaseMenu {

    public MainMenu(JFrame frame) {
        super(frame);

        buttonPanel.setLayout(new GridLayout(0, 1, 0, 25));

        addButton("START", e -> play());
        addButton("SCORES", e -> JOptionPane.showMessageDialog(frame, "scorerere"));
        addButton("EXIT", e -> System.exit(0));
    }

    private void play() {
        GameMenu gameMenu = new GameMenu(frame);
        frame.getContentPane().removeAll();
        frame.add(gameMenu);
        frame.revalidate();
        frame.repaint();
    }
}
