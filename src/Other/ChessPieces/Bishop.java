package Other.ChessPieces;

import Other.Coordinates;

import java.util.ArrayList;

import static Game.Mechanics.possMoveToKill;

public class Bishop extends Piece {

    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite, PieceType.BISHOP);
    }

    /**
     * Calculates the allowed movements for the bishop on the chessboard.
     *
     * @param  board       the chessboard represented as a 2D array of strings
     * @param  isWhite     indicates whether the bishop is white or black
     * @return             an ArrayList of Coordinates representing the possible movements
     */
    @Override
    public ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite) {
        ArrayList<Coordinates> possibleMovements = new ArrayList<>();
        int[][] directions = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            for (int i = 1; i < 8; i++) {
                int newX = getcX() + i * dx;
                int newY = getcY() + i * dy;

                if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                    if (board[newY][newX] == null) {
                        possibleMovements.add(new Coordinates(newX, newY));
                    } else if (possMoveToKill(board, newX, newY, isWhite)) {
                        possibleMovements.add(new Coordinates(newX, newY));
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return possibleMovements;
    }

}
