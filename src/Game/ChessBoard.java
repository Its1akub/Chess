package Game;

import Other.ChessPieces.*;
import Other.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.Iterator;

import static Game.Mechanics.promotion;
import static Game.Mechanics.removePiece;
import static Game.MechanicsForBoard.castling;
import static Game.StockFishMode.MechanicsStockFish.convertBoardtoBoardFEN;
import static Other.AudioMethod.audioForChess;

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
    private Move opponentMove;
    private boolean madeMove = false;
    private String previousPosition, currentPosition;
    private boolean kingMoves = false;
    private boolean lRookMoves = false;
    private boolean rRookMoves = false;


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
        audioForChess("src/main/resources/audio/gameStart.wav");
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public String getPreviousPosition() {
        return previousPosition;
    }

    public String[][] getBoard() {
        return board;
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
        this.board = Mechanics.transformBoardToArray(this.pieces);
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public String[][] getPreviousBoard() {
        return previousBoard;
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

    public void setOpponentMove(Move opponentMove) {
        this.opponentMove = opponentMove;
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
        if (opponentMove != null) {
            g.setColor(new Color(255, 0, 0, 110));
            g.fillRect(opponentMove.getCurrentCX() * hitbox * 2 + xMoveBy, opponentMove.getCurrentCY() * hitbox * 2 + yMoveBy, hitbox * 2, hitbox * 2);
            g.fillRect(opponentMove.getPreviousCX() * hitbox * 2 + xMoveBy, opponentMove.getPreviousCY() * hitbox * 2 + yMoveBy, hitbox * 2, hitbox * 2);
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
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
            if (hoveringPiece instanceof Pawn) ((Pawn) hoveringPiece).setPieces(pieces);
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
        for (Piece p : pieces) if (p.isHovering()) hoveringPiece = p;

        if (!madeMove) {
            if (hoveringPiece != null) {
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
            } else {
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        ArrayList<Piece> rePieces = pieces;
        boolean playedAudio = false;
        for (Piece p : pieces) {
            if (p.isHovering()) {
                boolean movingWithKing = p instanceof King;

                double tempX = p.getX() - xMoveBy;
                tempX = Math.floor(tempX / hitbox / 2);
                int hitX = (int) tempX;

                double tempY = p.getY() - yMoveBy;
                tempY = Math.floor(tempY / hitbox / 2);
                int hitY = (int) tempY;


                String[][] bP = convertBoardtoBoardFEN(board);
                previousPosition = bP[p.getcY()][p.getcX()];

                boolean canMove = false;
                for (Coordinates c : p.allowedMovements(board, p.isWhite())) {
                    if (hitX == c.getX() && hitY == c.getY()) {
                        if (p instanceof Pawn) ((Pawn) p).setMoveBy2(p.getcY() - 2 == c.getY());
                        canMove = true;
                        break;
                    }
                }
                int pCX, pCY;
                if (canMove) {
                    Iterator<Piece> iterator = pieces.iterator();
                    while (iterator.hasNext()) {
                        Piece piece = iterator.next();
                        if (hitX == piece.getcX() && hitY == piece.getcY()) {
                            iterator = removePiece(pieces, hitX, hitY).iterator();
                            audioForChess("src/main/resources/audio/capture.wav");
                            playedAudio = true;
                        }
                        if (p instanceof Pawn && piece instanceof Pawn && ((Pawn) piece).isMoveBy2() && hitX == piece.getcX() && hitY == piece.getcY() - 1)
                            iterator = removePiece(pieces, hitX, piece.getcY()).iterator();
                    }
                    pCX = p.getcX();
                    pCY = p.getcY();
                    p.setcX((int) tempX);
                    p.setcY((int) tempY);
                } else {
                    tempX = p.getcX();
                    tempY = p.getcY();
                    pCX = p.getcX();
                    pCY = p.getcY();
                }

                tempX = tempX * hitbox * 2 + xMoveBy;
                tempY = tempY * hitbox * 2 + yMoveBy;

                p.setX((int) tempX + hitbox);
                p.setY((int) tempY + hitbox);

                String[][] bC = convertBoardtoBoardFEN(board);
                currentPosition = bC[p.getcY()][p.getcX()];

                rePieces = promotion(rePieces, xMoveBy, yMoveBy);
                if (movingWithKing && !kingMoves && !lRookMoves && !rRookMoves) {
                    Move move = new Move(pCX, pCY, p, p.getcX(), p.getcY());
                    rePieces = castling(rePieces, xMoveBy, yMoveBy, move, isWhite, board, true);
                }

                board = Mechanics.transformBoardToArray(rePieces);
                if (previousBoard != null && !Mechanics.checkForPlayerMoves(board, previousBoard, isWhite)) {
                    lastMove = new Move(pCX, pCY, p, p.getcX(), p.getcY());
                    if (p instanceof Pawn) ((Pawn) p).setFirstMove(false);
                    madeMove = true;
                    if (!playedAudio) audioForChess("src/main/resources/audio/move.wav");
                } else if (Mechanics.checkForPlayerMoves(board, previousBoard, isWhite)) {
                    lastMove = null;
                    madeMove = false;
                }
                p.setHovering(false);
                someoneHover = false;
                opponentMove = null;

                if (p instanceof King) {
                    kingMoves = true;
                }
                if (p instanceof Rook) {
                    if (p.getcX() == 0) lRookMoves = true;
                    else if (p.getcX() == 7) rRookMoves = true;
                }
                break;
            }
        }
        pieces = rePieces;
        board = Mechanics.transformBoardToArray(pieces);
        previousBoard = board;
        repaint();
    }
}