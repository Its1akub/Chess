package Other.ChessPieces;

import Other.Coordinates;

import java.util.ArrayList;

import static Game.Mechanics.possMoveToKill;

public class Pawn extends Piece {
    private boolean firstMove = true;

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
        if (isWhite) {
            if (getcX() > 0 && getcY() > 0 && board[getcY() - 1][getcX() - 1] != null && possMoveToKill(board, getcX() - 1, getcY() - 1, true)) {
                possibleMovements.add(new Coordinates(getcX() - 1, getcY() - 1));
            }
            if (getcX() < 7 && getcY() > 0 && board[getcY() - 1][getcX() + 1] != null && possMoveToKill(board, getcX() + 1, getcY() - 1, true)) {
                possibleMovements.add(new Coordinates(getcX() + 1, getcY() - 1));
            }

        } else {
            if (getcX() > 0 && getcY() > 0 && board[getcY() - 1][getcX() - 1] != null && possMoveToKill(board, getcX() - 1, getcY() - 1, false)) {
                possibleMovements.add(new Coordinates(getcX() - 1, getcY() - 1));
            }
            if (getcX() < 7 && getcY() > 0 && board[getcY() - 1][getcX() + 1] != null && possMoveToKill(board, getcX() + 1, getcY() - 1, false)) {
                possibleMovements.add(new Coordinates(getcX() + 1, getcY() - 1));
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
    /*@Override
    public void move(int x, int y) {
        super.move(x, y);
        if (firstMove) {
            firstMove = false;
        }
    }*/
}
