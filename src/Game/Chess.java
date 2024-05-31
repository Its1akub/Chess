package Game;

import Other.Users.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;


public class Chess extends JPanel {
    private MovePanel movePanel;
    private ChessBoard chessBoard;

    private Player p1;
    private Player p2;


    public Chess(int width, int height,boolean isWhite) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());

        chessBoard = new ChessBoard(width, height, isWhite) {
            @Override public void mouseMoved(MouseEvent e) {}
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        };
        movePanel = new MovePanel();
        add(chessBoard,BorderLayout.CENTER);
        add(movePanel,BorderLayout.EAST);

    }
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public MovePanel getMovePanel() {
        return movePanel;
    }
}
