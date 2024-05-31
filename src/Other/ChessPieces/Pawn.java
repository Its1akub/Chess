package Other.ChessPieces;

import Other.Coordinates;

import java.util.ArrayList;

import static Game.Mechanics.possMoveToKill;

public class Pawn extends Piece {
    private boolean firstMove = true;
    private boolean moveBy2 = false;
    private int direction;
    private ArrayList<Piece> pieces;

    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite, PieceType.PAWN);
    }

    /**
     * Calculates the allowed movements for a pawn piece on a chess board.
     *
     * @param  board    the chess board represented as a 2D array of strings
     * @param  isWhite  a boolean indicating whether the pawn is white or black
     * @return          an ArrayList of Coordinates representing the allowed movements
     */
    @Override
    public ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite) {
        ArrayList<Coordinates> possibleMovements = new ArrayList<>();

        if (firstMove && getcY() > 0 && board[getcY() - direction][getcX()] == null && board[getcY() - (2*direction)][getcX()] == null) {
            possibleMovements.add(new Coordinates(getcX(), getcY() - 2));
        }

            if (getcY() > 0 && board[getcY() - direction][getcX()] == null) {
                possibleMovements.add(new Coordinates(getcX(), getcY() - 1));
            }
            if (getcX() > 0 && getcY() > 0 && board[getcY() - direction][getcX() - 1] != null && possMoveToKill(board, getcX() - 1, getcY() - direction, isWhite)) {
                possibleMovements.add(new Coordinates(getcX() - 1, getcY() - direction));
            }
            if (getcX() < 7 && getcY() > 0 && board[getcY() - direction][getcX() + 1] != null && possMoveToKill(board, getcX() + 1, getcY() - direction, isWhite)) {
                possibleMovements.add(new Coordinates(getcX() + 1, getcY() - direction));
            }
            // En passant
            for (Piece p : pieces) {
                if (p.isWhite() != isWhite && p instanceof Pawn && ((Pawn) p).isMoveBy2()) {
                    char c = isWhite ? 'b' : 'w';
                    if (getcY()==3 && getcX() < 7 && p.getcX() + 1 == getcX() && p.getcY() == getcY() && board[getY()][getX()+1].toCharArray()[1] != c) {
                        possibleMovements.add(new Coordinates(getcX() - 1, getcY() - direction));
                    }
                    if (getcY()==3 && getcX() > 0 && p.getcX() - 1 == getcX() && p.getcY() == getcY() && board[getY()][getX()-1].toCharArray()[1] != c) {
                        possibleMovements.add(new Coordinates(getcX() + 1, getcY() - direction));
                    }
                }
            }



        return possibleMovements;
    }

    public void setDirection(boolean playOnBottom) {
        if (playOnBottom) {
            this.direction = 1;
        }else {
            this.direction = -1;
        }
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isMoveBy2() {
        return moveBy2;
    }

    public void setMoveBy2(boolean moveBy2) {
        this.moveBy2 = moveBy2;
    }
}
