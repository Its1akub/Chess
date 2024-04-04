package Other.ChessPieces;

import Other.Coordinates;

import java.util.ArrayList;

import static Game.Mechanics.possMoveToKill;

public class Knight extends Piece{
    public Knight(int x, int y, boolean isWhite) {
        super(x, y, isWhite, PieceType.KNIGHT);
    }

    @Override
    public ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite) {
        ArrayList<Coordinates> possibleMovements = new ArrayList<>();
        int[][] offsets = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, -2}, {1, -2}, {-1, 2}, {1, 2}};

        for (int[] offset : offsets) {
            int newX = getcX() + offset[0];
            int newY = getcY() + offset[1];

            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && (board[newY][newX] == null || possMoveToKill(board, newX, newY, isWhite))) {
                possibleMovements.add(new Coordinates(newX, newY));
            }
        }

        return possibleMovements;
    }

}
