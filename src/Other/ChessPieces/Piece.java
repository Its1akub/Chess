package Other.ChessPieces;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public abstract class Piece{


    private int x;
    private int y;
    private boolean isWhite;
    private PieceType type;
    private BufferedImage image;



    public Piece(int x, int y, boolean isWhite, PieceType type) {
        setX(x);
        setY(y);
        setWhite(isWhite);
        setType(type);
        setImage(computeImagePath());

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

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String computeImagePath() {
        String colorSuffix = isWhite ? "w" : "b";
        String typePrefix;
        if (type == PieceType.KNIGHT){
           typePrefix = String.valueOf(type.toString().toLowerCase().toCharArray()[1]);
        }else {
            typePrefix = String.valueOf(type.toString().toLowerCase().toCharArray()[0]);
        }
        return "src/main/resources/" + typePrefix + colorSuffix + ".png";
    }

}
