package Game;

import Other.ChessPieces.Piece;
import Other.Coordinates;

import java.io.Serializable;

public class Move implements Serializable{
    private final int previousCX, previousCY;
    private int currentCX, currentCY;
    private Piece piece;

    public Move(int pCX, int pCY, Piece piece,int cCX,int cCY) {
        this.previousCX = pCX;
        this.previousCY = pCY;
        this.currentCX = cCX;
        this.currentCY = cCY;
        this.piece = piece;
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
