package Other.ChessPieces;

import Other.Coordinates;

import java.util.ArrayList;

import static Game.Mechanics.possMoveToKill;

public class Queen extends Piece {
    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite, PieceType.QUEEN);
    }

    @Override
    public ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite) {
        ArrayList<Coordinates> possibleMovements = new ArrayList<>();

        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            for (int i = 1; i < 8; i++) {
                int newX = getcX() + (i * dx);
                int newY = getcY() + (i * dy);

                if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) break;

                if (board[newY][newX] == null) {
                    possibleMovements.add(new Coordinates(newX, newY));
                } else if (possMoveToKill(board, newX, newY, isWhite)) {
                    possibleMovements.add(new Coordinates(newX, newY));
                    break;
                } else {
                    break;
                }
            }
        }

        return possibleMovements;
    }
}
