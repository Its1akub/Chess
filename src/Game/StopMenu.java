package Game;

import javax.swing.*;
import java.awt.*;

public class StopMenu extends JPanel {

    public StopMenu() {
        setBackground(Color.ORANGE);
        JLabel label = new JLabel("Hra skončila");
        add(label);
    }
}
