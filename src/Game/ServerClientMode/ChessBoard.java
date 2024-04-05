package Game.ServerClientMode;

import Game.Mechanics;
import Game.Move;
import Game.PiecesLoad;
import Other.ChessPieces.*;
import Other.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static Game.Mechanics.promotion;
import static Game.Mechanics.removePiece;

public abstract class ChessBoard extends JPanel implements MouseMotionListener, MouseListener {
    private ArrayList<Piece> pieces = new ArrayList<>();
    private final int hitbox = 50;
    private int hoverOverX, hoverOverY;
    private final int xMoveBy, yMoveBy;
    private boolean someoneHover = false;
    private final boolean isWhite;
    private String[][] previousBoard = new String[8][8];
    private String[][] board = new String[8][8];
    private Move lastMove;
    private boolean madeMove = false;

    public ChessBoard(int width, int height, boolean isWhite) {
        setBounds(0, 0, width, height);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        xMoveBy = (getWidth() - hitbox * 16) / 2;
        yMoveBy = (getHeight() - hitbox * 16) / 2;
        PiecesLoad piecesLoad = new PiecesLoad(isWhite);
        pieces = piecesLoad.getPieces();
        pieces = transformPiecesCo(pieces);
        this.isWhite = isWhite;
        board = Mechanics.transformBoardToArray(pieces);
    }

    public Move getLastMove() {
        return lastMove;
    }

    public boolean isMadeMove() {
        return madeMove;
    }

    public void setMadeMove(boolean madeMove) {
        this.madeMove = madeMove;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
        this.pieces = transformPiecesCo(this.pieces);
        board = Mechanics.transformBoardToArray(pieces);
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    /**
     * A method to convert coordinates to screen position.
     * @param  cX    the X coordinate
     * @param  cY    the Y coordinate
     * @return an array containing the screen coordinates
     */
    public int[] coordinatesToScreen(int cX, int cY) {
        return new int[]{xMoveBy + cX * hitbox * 2 + hitbox, yMoveBy + cY * hitbox * 2 + hitbox};
    }

    /**
     * A function that transforms the pieces in a given ArrayList to screen coordinates.
     * @param  piece    the ArrayList of pieces to be transformed
     * @return the transformed ArrayList of pieces
     */
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
        g.setColor(new Color(66, 31, 21));
        g.fillRect(xMoveBy - hitbox, yMoveBy - hitbox, hitbox * 18, hitbox * 18);

        boolean white = false;
        for (int i = 0; i < 8; i++) {
            white = !white;
            for (int j = 0; j < 8; j++) {
                if (white) g.setColor(new Color(245, 205, 193));
                else g.setColor(new Color(107, 66, 54));
                g.fillRect(i * hitbox * 2 + xMoveBy, j * hitbox * 2 + yMoveBy, hitbox * 2, hitbox * 2);
                if (hoverOverX == i && hoverOverY == j && someoneHover) {
                    g.setColor(new Color(25, 7, 5, 30));
                    g.fillRect(i * hitbox * 2 + xMoveBy, j * hitbox * 2 + yMoveBy, hitbox * 2, hitbox * 2);
                }
                white = !white;
            }
        }

        char[] lettersWhite = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        char[] lettersBlack = {'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'};
        char[] numbersWhite = {'8', '7', '6', '5', '4', '3', '2', '1'};
        char[] numbersBlack = {'1', '2', '3', '4', '5', '6', '7', '8'};
        for (int i = 0; i < 8; i++) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            if (isWhite) {
                g.drawString(String.valueOf(lettersWhite[i]), i * hitbox * 2 + xMoveBy + hitbox - 5, hitbox * 16 + yMoveBy + hitbox / 2 + 5);
                g.drawString(String.valueOf(numbersWhite[i]), xMoveBy - hitbox / 2 - 5, i * hitbox * 2 + yMoveBy + hitbox + 5);
            } else {
                g.drawString(String.valueOf(lettersBlack[i]), i * hitbox * 2 + xMoveBy + hitbox - 5, hitbox * 16 + yMoveBy + hitbox / 2 + 5);
                g.drawString(String.valueOf(numbersBlack[i]), xMoveBy - hitbox / 2 - 5, i * hitbox * 2 + yMoveBy + hitbox + 5);
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
            for (Coordinates c : hoveringPiece.allowedMovements(board, hoveringPiece.isWhite())) {
                g.setColor(new Color(250, 218, 94, 75));
                g.fillRect(c.getX() * hitbox * 2 + xMoveBy, c.getY() * hitbox * 2 + yMoveBy, hitbox * 2, hitbox * 2);
            }
            g.drawImage(hoveringPiece.getImage(), (int) (hoveringPiece.getX() - hitbox * 1.125), (int) (hoveringPiece.getY() - hitbox * 1.125), (int) (hitbox * 2.25), (int) (hitbox * 2.25), null);
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        Piece hoveringPiece = null;
        for (Piece p : pieces) {
            if (p.isHovering()) hoveringPiece = p;
        }
        if (hoveringPiece != null && !madeMove) {
            double tempX = hoveringPiece.getX() - (double) xMoveBy;
            tempX = Math.floor(tempX / hitbox / 2);
            hoverOverX = (int) tempX;
            double tempY = hoveringPiece.getY() - (double) yMoveBy;
            tempY = Math.floor(tempY / hitbox / 2);
            hoverOverY = (int) tempY;

            hoveringPiece.setX(e.getX());
            hoveringPiece.setY(e.getY());
            hoveringPiece.setHovering(true);
            someoneHover = true;
            repaint();
        } else if (!madeMove){
            for (Piece p : pieces) {
                if (e.getX() >= p.getX() - hitbox && e.getX() <= p.getX() + hitbox && e.getY() >= p.getY() - hitbox && e.getY() <= p.getY() + hitbox && ((p.isWhite() && isWhite) || (!p.isWhite() && !isWhite))) {
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
        ArrayList<Piece> rePieces = pieces;
        for (Piece p : pieces) {
            if (p.isHovering()) {
                double tempX = p.getX() - xMoveBy;
                tempX = Math.floor(tempX / hitbox / 2);
                int hitX = (int) tempX;

                double tempY = p.getY() - yMoveBy;
                tempY = Math.floor(tempY / hitbox / 2);
                int hitY = (int) tempY;
                boolean canMove = false;
                for (Coordinates c : p.allowedMovements(board, p.isWhite())) {
                    if (hitX == c.getX() && hitY == c.getY()) {
                        canMove = true;
                        break;
                    }
                }
                if (canMove) {
                    Iterator<Piece> iterator = pieces.iterator();
                    while (iterator.hasNext()) {
                        Piece piece = iterator.next();
                        if (hitX == piece.getcX() && hitY == piece.getcY()) iterator = removePiece(pieces, hitX, hitY).iterator();
                    }
                    p.setcX((int) tempX);
                    p.setcY((int) tempY);
                } else {
                    tempX = p.getcX();
                    tempY = p.getcY();
                }
                tempX = tempX * hitbox * 2 + xMoveBy;
                tempY = tempY * hitbox * 2 + yMoveBy;

                p.setX((int) tempX + hitbox);
                p.setY((int) tempY + hitbox);
                if (p instanceof Pawn) ((Pawn) p).setFirstMove(false);
                rePieces = promotion(rePieces, xMoveBy, yMoveBy);
                if (previousBoard != null && !Arrays.deepEquals(previousBoard, board)) {
                    lastMove = new Move(p.getcX(), p.getcY(),p);
                    madeMove = true;
                }
                p.setHovering(false);
                someoneHover = false;
                break;
            }
        }
        pieces = rePieces;
        board = Mechanics.transformBoardToArray(pieces);
        previousBoard = board;
        repaint();
    }
}