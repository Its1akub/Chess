package Game;

import Other.ChessPieces.Piece;
import Other.Coordinates;

import java.io.Serializable;

public class Move implements Serializable {
    private final int previousCX,previousCY, currentCX, currentCY;
    private Piece piece;

    public Move(int cX, int cY, Piece piece) {
        this.previousCX = cX;
        this.previousCY = cY;
        this.piece = piece;
        Coordinates current = flipCoordinates(cX, cY);
        this.currentCX = current.getX();
        this.currentCY = current.getY();
        piece.setcX(currentCX);
        piece.setcY(currentCY);
    }

    public int getCurrentCX() {
        return currentCX;
    }

    public int getCurrentCY() {
        return currentCY;
    }

    public int getPreviousCX() {
        return previousCX;
    }

    public int getPreviousCY() {
        return previousCY;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    private Coordinates flipCoordinates(int x, int y) {
        return new Coordinates(7-x, 7-y);
    }

    @Override
    public String toString() {
        return "Move{" +
                "previousCX=" + previousCX +
                ", previousCY=" + previousCY +
                ", currentCX=" + currentCX +
                ", currentCY=" + currentCY +
                ", piece=" + piece +
                '}';
    }
}
