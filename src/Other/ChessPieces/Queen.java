package Other.ChessPieces;

import Other.Coordinates;

import java.util.ArrayList;

import static Game.Mechanics.possMoveToKill;

public class Queen extends Piece{
    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite, PieceType.QUEEN);
    }

    @Override
    public ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite) {
        ArrayList<Coordinates> possibleMovements = new ArrayList<>();

        int lUX, LUY, rUX, rUY, lDX, lDY, rDX, rDY,left, right, up, down;

        //left up
        for (int i = 1; i < 8; i++) {
            if (getcX() - i >= 0 && getcY() - i >= 0) {
                lUX = getcX() - i;
                LUY = getcY() - i;
                if (board[LUY][lUX] == null) {
                    possibleMovements.add(new Coordinates(lUX, LUY));
                } else if (possMoveToKill(board, lUX, LUY, isWhite)) {
                    possibleMovements.add(new Coordinates(lUX, LUY));
                    break;
                } else {
                    break;
                }
            }
        }
        //left down
        for (int i = 1; i < 8; i++) {
            if (getcX() - i >= 0 && getcY() + i < 8) {
                lDX = getcX() - i;
                lDY = getcY() + i;
                if (board[lDY][lDX] == null) {
                    possibleMovements.add(new Coordinates(lDX, lDY));
                } else if (possMoveToKill(board, lDX, lDY, isWhite)) {
                    possibleMovements.add(new Coordinates(lDX, lDY));
                    break;
                } else {
                    break;
                }
            }
        }
        //right up
        for (int i = 1; i < 8; i++) {
            if (getcX() + i < 8 && getcY() - i >= 0) {
                rUX = getcX() + i;
                rUY = getcY() - i;
                if (board[rUY][rUX] == null) {
                    possibleMovements.add(new Coordinates(rUX, rUY));
                } else if (possMoveToKill(board, rUX, rUY, isWhite)) {
                    possibleMovements.add(new Coordinates(rUX, rUY));
                    break;
                } else {
                    break;
                }
            }
        }
        //right down
        for (int i = 1; i < 8; i++) {
            if (getcX() + i < 8 && getcY() + i < 8) {
                rDX = getcX() + i;
                rDY = getcY() + i;
                if (board[rDY][rDX] == null) {
                    possibleMovements.add(new Coordinates(rDX, rDY));
                } else if (possMoveToKill(board, rDX, rDY, isWhite)) {
                    possibleMovements.add(new Coordinates(rDX, rDY));
                    break;
                } else {
                    break;
                }
            }
        }
        //up
        for (int i = 1; i < 8; i++) {
            if (getcY() - i >= 0) {
                up = getcY() - i;
                if (board[up][getcX()] == null) {
                    possibleMovements.add(new Coordinates(getcX(), up));
                } else if (possMoveToKill(board, getcX(), up, isWhite)) {
                    possibleMovements.add(new Coordinates(getcX(), up));
                    break;
                } else {
                    break;
                }
            }
        }
        //down
        for (int i = 1; i < 8; i++) {
            if (getcY() + i < 8) {
                down = getcY() + i;
                if (board[down][getcX()] == null) {
                    possibleMovements.add(new Coordinates(getcX(), down));
                } else if (possMoveToKill(board, getcX(), down, isWhite)) {
                    possibleMovements.add(new Coordinates(getcX(), down));
                    break;
                } else {
                    break;
                }
            }
        }
        //left
        for (int i = 1; i < 8; i++) {
            if (getcX() - i >= 0) {
                left = getcX() - i;
                if (board[getcY()][left] == null) {
                    possibleMovements.add(new Coordinates(left, getcY()));
                } else if (possMoveToKill(board, left, getcY(), isWhite)) {
                    possibleMovements.add(new Coordinates(left, getcY()));
                    break;
                } else {
                    break;
                }
            }
        }
        //right
        for (int i = 1; i < 8; i++) {
            if (getcX() + i < 8) {
                right = getcX() + i;
                if (board[getcY()][right] == null) {
                    possibleMovements.add(new Coordinates(right, getcY()));
                }else if (possMoveToKill(board, right, getcY(), isWhite)) {
                    possibleMovements.add(new Coordinates(right, getcY()));
                    break;
                } else {
                    break;
                }
            }
        }


        return possibleMovements;
    }
}
