package Game;

import javax.swing.*;
import java.awt.*;

public class Chess extends JPanel {
    public static StopMenu stopMenu;
    public static boolean stopMenuShown = false;

    public Chess() {
        setBackground(Color.RED);
        stopMenu = new StopMenu();
        stopMenu.setVisible(false);
        add(stopMenu, BorderLayout.CENTER);
    }



}
