package Game;

public class PositionOnBoard {

    private int x;
    private int y;
    private String typeOfPiece;

    public PositionOnBoard(int x, int y, String typeOfPiece) {
        this.x = x;
        this.y = y;
        this.typeOfPiece = typeOfPiece;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getTypeOfPiece() {
        return typeOfPiece;
    }

    public void setTypeOfPiece(String typeOfPiece) {
        this.typeOfPiece = typeOfPiece;
    }
}
