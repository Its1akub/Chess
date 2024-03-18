package Game;

import javax.swing.*;
import java.awt.*;

public class StopMenu extends JPanel {

    public StopMenu(int width, int height) {
        setBackground(Color.ORANGE);
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        JLabel label = new JLabel("Hra skončila");
        add(label);

    }

}
