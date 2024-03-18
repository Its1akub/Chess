package Other.ChessPieces;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;


public abstract class Piece{


    private int x;
    private int y;
    private int cX;
    private int cY;
    private boolean isWhite;
    private PieceType type;
    private BufferedImage image;
    private boolean hovering = false;

    public Piece(int cX, int cY, boolean isWhite, PieceType type) {
        setcX(cX);
        setcY(cY);
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

    public int getcX() {
        return cX;
    }

    public void setcX(int cX) {
        if (cX < 0 || cX > 7) throw new InputMismatchException();
        this.cX = cX;
    }

    public int getcY() {
        return cY;
    }

    public void setcY(int cY) {
        if (cY < 0 || cY > 7) throw new InputMismatchException();
        this.cY = cY;
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

    public boolean isHovering() {
        return hovering;
    }
    public void setHovering(boolean hovering) {
        this.hovering = hovering;
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


