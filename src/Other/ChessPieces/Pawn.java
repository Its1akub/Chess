package Other.ChessPieces;

import Other.Coordinates;

import java.util.ArrayList;

import static Game.Mechanics.possMoveToKill;

public class Pawn extends Piece {
    private boolean firstMove = true;
    private boolean moveBy2 = false;
    private ArrayList<Piece> pieces;

    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite, PieceType.PAWN);
    }

    @Override
    public ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite) {
        ArrayList<Coordinates> possibleMovements = new ArrayList<>();

        if (firstMove) {
            possibleMovements.add(new Coordinates(getcX(), getcY() - 2));
        }

            if (getcY() > 0 && board[getcY() - 1][getcX()] == null) {
                possibleMovements.add(new Coordinates(getcX(), getcY() - 1));
            }
            if (getcX() > 0 && getcY() > 0 && board[getcY() - 1][getcX() - 1] != null && possMoveToKill(board, getcX() - 1, getcY() - 1, isWhite)) {
                possibleMovements.add(new Coordinates(getcX() - 1, getcY() - 1));
            }
            if (getcX() < 7 && getcY() > 0 && board[getcY() - 1][getcX() + 1] != null && possMoveToKill(board, getcX() + 1, getcY() - 1, isWhite)) {
                possibleMovements.add(new Coordinates(getcX() + 1, getcY() - 1));
            }
            // En passant
            for (Piece p : pieces) {
                if (p.isWhite() != isWhite && p instanceof Pawn && ((Pawn) p).isMoveBy2()) {
                    char c = isWhite ? 'b' : 'w';
                    if (getcY()==3 && getcX() < 7 && p.getcX() + 1 == getcX() && p.getcY() == getcY() && board[getY()][getX()+1].toCharArray()[1] != c) {
                        possibleMovements.add(new Coordinates(getcX() - 1, getcY() - 1));
                    }
                    if (getcY()==3 && getcX() > 0 && p.getcX() - 1 == getcX() && p.getcY() == getcY() && board[getY()][getX()-1].toCharArray()[1] != c) {
                        possibleMovements.add(new Coordinates(getcX() + 1, getcY() - 1));
                    }
                }
            }



        return possibleMovements;
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
