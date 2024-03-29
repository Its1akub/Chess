package Game.ServerClientMode;

import Game.PiecesLoad;
import Other.ChessPieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class ChessBoard extends JPanel implements MouseMotionListener, MouseListener {
    private ArrayList<Piece> pieces = new ArrayList<>();
    private int hitbox = 50;
    private int hoverOverX;
    private int hoverOverY;
    private boolean someoneHover = false;
    //private String[][] board = new String[8][8];

    public ChessBoard(int width, int height) {
        setBounds(0, 0, width, height);
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        setLayout(null);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        PiecesLoad piecesLoad = new PiecesLoad(true);
        pieces = piecesLoad.getPieces();
        pieces = transformPiecesCo(pieces);
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }


    public int[] coordinatesToScreen(int cX, int cY) {
        int xMoveBy = (getWidth() - hitbox * 16) / 2;
        int yMoveBy = (getHeight() - hitbox * 16) / 2;
        return new int[]{
                //cX * hitbox * 2 + xMoveBy - hitbox*2,cY * hitbox * 2 + yMoveBy - hitbox*2
                xMoveBy + cX * hitbox * 2 + hitbox, yMoveBy + cY * hitbox * 2 + hitbox
        };
    }

    public ArrayList<Piece> transformPiecesCo(ArrayList<Piece> piece) {
        for (Piece p : piece) {
            int[] temp = coordinatesToScreen(p.getcX(), p.getcY());
            p.setX(temp[0]);
            p.setY(temp[1]);
        }
        repaint();
        return piece;
    }

    @Override
    public void paintComponent(Graphics g1d) {
        Graphics2D g = (Graphics2D) g1d;

        g.setColor(new Color(115, 56, 39));
        g.fillRect(0, 0, getWidth(), getHeight());

        int xMoveBy = (getWidth() - hitbox * 16) / 2;
        int yMoveBy = (getHeight() - hitbox * 16) / 2;

        g.setColor(new Color(66, 31, 21));
        g.fillRect(xMoveBy - hitbox, yMoveBy - hitbox, hitbox * 18, hitbox * 18);

        boolean white = false;
        for (int i = 0; i < 8; i++) {
            white = !white;
            for (int j = 0; j < 8; j++) {
                if (white) {
                    g.setColor(new Color(245, 205, 193));
                } else {
                    g.setColor(new Color(107, 66, 54));
                }
                g.fillRect(i * hitbox * 2 + xMoveBy, j * hitbox * 2 + yMoveBy, hitbox * 2, hitbox * 2);

                if (hoverOverX == i && hoverOverY == j && someoneHover) {
                    g.setColor(new Color(25, 7, 5, 30));
                    g.fillRect(i * hitbox * 2 + xMoveBy, j * hitbox * 2 + yMoveBy, hitbox * 2, hitbox * 2);
                }
                white = !white;
            }
        }

        Piece hoveringPiece = null;
        for (Piece p : pieces) {
            if (p.isHovering()) {
                hoveringPiece = p;
            } else {
                p.setX(Math.min(p.getX(), hitbox * 16 + xMoveBy - 1));
                p.setY(Math.min(p.getY(), hitbox * 16 + yMoveBy - 1));
                p.setX(Math.max(p.getX(), xMoveBy));
                p.setY(Math.max(p.getY(), yMoveBy));

                g.drawImage(p.getImage(), p.getX() - hitbox, p.getY() - hitbox, hitbox * 2, hitbox * 2, null);

            }
        }

        if (hoveringPiece != null) {
            hoveringPiece.setX(Math.min(hoveringPiece.getX(), hitbox * 16 + xMoveBy - 1));
            hoveringPiece.setY(Math.min(hoveringPiece.getY(), hitbox * 16 + yMoveBy - 1));
            hoveringPiece.setX(Math.max(hoveringPiece.getX(), xMoveBy));
            hoveringPiece.setY(Math.max(hoveringPiece.getY(), yMoveBy));

            g.drawImage(hoveringPiece.getImage(), (int) (hoveringPiece.getX() - hitbox * 1.125), (int) (hoveringPiece.getY() - hitbox * 1.125), (int) (hitbox * 2.25), (int) (hitbox * 2.25), null);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Piece hoveringPiece = null;
        for (Piece p : pieces) {
            if (p.isHovering()) hoveringPiece = p;
        }

        if (hoveringPiece != null) {
            double tempX = hoveringPiece.getX() - (getWidth() - hitbox * 16) / 2.0;
            tempX = Math.floor(tempX / hitbox / 2);
            hoverOverX = (int) tempX;
            double tempY = hoveringPiece.getY() - (getHeight() - hitbox * 16) / 2.0;
            tempY = Math.floor(tempY / hitbox / 2);
            hoverOverY = (int) tempY;

            hoveringPiece.setX(e.getX());
            hoveringPiece.setY(e.getY());

            hoveringPiece.setHovering(true);
            someoneHover = true;
            repaint();
        } else {
            for (Piece p : pieces) {
                if (e.getX() >= p.getX() - hitbox && e.getX() <= p.getX() + hitbox && e.getY() >= p.getY() - hitbox && e.getY() <= p.getY() + hitbox) {
                    p.setX(e.getX());
                    p.setY(e.getY());

                    p.setHovering(true);
                    someoneHover = true;
                    repaint();
                    break;
                }
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        int xMoveBy = (getWidth() - hitbox * 16) / 2;
        int yMoveBy = (getHeight() - hitbox * 16) / 2;

        for (Piece p : pieces) {
            if (p.isHovering()) {
                double tempX = p.getX() - xMoveBy;
                tempX = Math.floor(tempX / hitbox / 2);
                p.setcX((int) tempX);
                tempX = tempX * hitbox * 2 + xMoveBy;

                double tempY = p.getY() - yMoveBy;
                tempY = Math.floor(tempY / hitbox / 2);
                p.setcY((int) tempY);
                tempY = tempY * hitbox * 2 + yMoveBy;

                p.setX((int) tempX + hitbox);
                p.setY((int) tempY + hitbox);
            }
            p.setHovering(false);
            someoneHover = false;
        }
        repaint();
    }
    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}


}
