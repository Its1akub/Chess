package Other.ChessPieces;

import Other.Coordinates;

import static Game.Mechanics.possMoveToKill;

import java.util.ArrayList;

public class King extends Piece {
    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite, PieceType.KING);
    }

    @Override
    public ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite) {
        ArrayList<Coordinates> allowedMovements = new ArrayList<>();
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        for (int i = 0; i < 8; i++) {
            int newX = getcX() + dx[i];
            int newY = getcY() + dy[i];

            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                if (board[newY][newX] == null || possMoveToKill(board, newX, newY, isWhite)) {
                    allowedMovements.add(new Coordinates(newX, newY));
                }
            }
        }

        return allowedMovements;
    }

}
