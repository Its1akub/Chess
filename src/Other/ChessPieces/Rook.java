package Other.ChessPieces;

import Other.Coordinates;

import java.util.ArrayList;

import static Game.Mechanics.possMoveToKill;

public class Rook extends Piece{
    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite, PieceType.ROOK);
    }

    @Override
    public ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite) {
        ArrayList<Coordinates> possibleMovements = new ArrayList<>();

        int UP, LEFT, DOWN, RIGHT;

        //up
        UP = getcY() - 1;
        while (UP >= 0) {
            if (board[UP][getcX()] == null) {
                possibleMovements.add(new Coordinates(getcX(), UP));
            } else if (possMoveToKill(board, getcX(), UP, isWhite)) {
                possibleMovements.add(new Coordinates(getcX(), UP));
                break;
            } else {
                break;
            }
            UP--;
        }

        //left
        LEFT = getcX() - 1;
        while (LEFT >= 0) {
            if (board[getcY()][LEFT] == null) {
                possibleMovements.add(new Coordinates(LEFT, getcY()));
            } else if (possMoveToKill(board, LEFT, getcY(), isWhite)) {
                possibleMovements.add(new Coordinates(LEFT, getcY()));
                break;
            } else {
                break;
            }
            LEFT--;
        }

        //down
        DOWN = getcY() + 1;
        while (DOWN < 8) {
            if (board[DOWN][getcX()] == null) {
                possibleMovements.add(new Coordinates(getcX(), DOWN));
            } else if (possMoveToKill(board, getcX(), DOWN, isWhite)) {
                possibleMovements.add(new Coordinates(getcX(), DOWN));
                break;
            } else {
                break;
            }
            DOWN++;
        }

        //right
        RIGHT = getcX() + 1;
        while (RIGHT < 8) {
            if (board[getcY()][RIGHT] == null) {
                possibleMovements.add(new Coordinates(RIGHT, getcY()));
            } else if (possMoveToKill(board, RIGHT, getcY(), isWhite)) {
                possibleMovements.add(new Coordinates(RIGHT, getcY()));
                break;
            } else {
                break;
            }
            RIGHT++;
        }
        return possibleMovements;
    }
}
