package Window;

import javax.swing.*;
import java.awt.*;

public class SelectingGame extends JPanel {
    JButton localHostButton,localJoinButton;
    public SelectingGame() {
        setBackground(Color.RED);
        localHostButton = new JButton("Local host");
        add(localHostButton, BorderLayout.CENTER);
        localJoinButton = new JButton("Local join");
        add(localJoinButton, BorderLayout.SOUTH);
    }


}
