package Window;

import javax.swing.*;
import java.awt.*;

public class IntroWindow extends JPanel {
   JButton playButton;

    public IntroWindow() {
        setBackground(Color.BLUE);
        playButton = new JButton("Play");

        setLayout(new BorderLayout());
        add(playButton, BorderLayout.CENTER);
    }
    public JButton getPlayButton() {
        return playButton;
    }
}
