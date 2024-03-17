package Game;

import Other.ChessPieces.Bishop;
import Other.ChessPieces.King;

import javax.swing.*;
import java.awt.*;

public class Chess extends JPanel {
    public static StopMenu stopMenu;
    public static boolean stopMenuShown = false;
    Bishop b;
    King k;

    public Chess() {
        setBackground(Color.RED);
        stopMenu = new StopMenu();
        stopMenu.setVisible(false);
        add(stopMenu, BorderLayout.CENTER);
        this.b = new Bishop(100,100,true);
        this.k = new King(200,200,true);

    }



}
