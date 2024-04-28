package Game;

import Other.Users.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;


public class Chess extends JPanel {
    public static StopMenu stopMenu;
    public static boolean stopMenuShown = false;
    private ChessBoard chessBoard;
    private Player p1;
    private Player p2;


    public Chess(int width, int height,boolean isWhite) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        /*stopMenu = new StopMenu(width, height);
        stopMenu.setVisible(false);
        add(stopMenu);*/

        chessBoard = new ChessBoard(width, height, isWhite) {
            @Override public void mouseMoved(MouseEvent e) {}
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        };
        add(chessBoard,BorderLayout.CENTER);


    }
    public ChessBoard getChessBoard() {
        return chessBoard;
    }
}
