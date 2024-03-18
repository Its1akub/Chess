package Game;

import Game.ServerClientMode.ChessBoard;
import Other.Users.Player;

import javax.swing.*;
import java.awt.*;


public class Chess extends JPanel {
    public static StopMenu stopMenu;
    public static boolean stopMenuShown = false;
    private Player p1;
    private Player p2;


    public Chess(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        stopMenu = new StopMenu(width, height);
        stopMenu.setVisible(false);
        add(stopMenu);

        ChessBoard chessBoard = new ChessBoard(width, height);
        add(chessBoard,BorderLayout.CENTER);



    }
    public void setPlayers(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
