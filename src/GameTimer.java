import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer extends JLabel {
    private Timer timer;
    private int remainingSeconds;
    protected JFrame frame;

    public GameTimer(JFrame frame) {
        this.frame = frame;

        // 7 min
        remainingSeconds = 7 * 60;

        // initialzie the label for the timer's display
        setText(formatTime(remainingSeconds));
        setFont(new Font("Arial", Font.BOLD, 12));
        setForeground(Color.WHITE);
        setOpaque(false);

        // update timer every 1000ms or 1s
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (remainingSeconds > 0) {
                    remainingSeconds--;
                    setText(formatTime(remainingSeconds));
                } else {
                    timer.stop();
                    setText("Time's up!");
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    // formatter
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("Time Remaining: %02d:%02d", minutes, seconds);
    }
}
