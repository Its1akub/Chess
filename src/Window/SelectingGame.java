package Window;

import Other.ChessPieces.Bishop;

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
        Bishop b = new Bishop(0,0,true,"/src/main/resources/bw.png");


    }


}
