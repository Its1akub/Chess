package Other.ChessPieces;

import Other.Coordinates;

import java.util.ArrayList;

import static Game.Mechanics.possMoveToKill;

public class Rook extends Piece{
    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite, PieceType.ROOK);
    }

    /**
     * Calculates the allowed movements for the queen chess piece on the given board.
     *
     * @param  board    the chess board represented as a 2D array of strings
     * @param  isWhite  a boolean indicating whether the queen is white or black
     * @return          an ArrayList of Coordinates representing the allowed movements for the queen
     */
    @Override
    public ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite) {
        ArrayList<Coordinates> possibleMovements = new ArrayList<>();

        int[][] directions = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int newX = getcX() + dx;
            int newY = getcY() + dy;

            while (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                if (board[newY][newX] == null) {
                    possibleMovements.add(new Coordinates(newX, newY));
                } else if (possMoveToKill(board, newX, newY, isWhite)) {
                    possibleMovements.add(new Coordinates(newX, newY));
                    break;
                } else {
                    break;
                }
                newX += dx;
                newY += dy;
            }
        }

        return possibleMovements;
    }
}
