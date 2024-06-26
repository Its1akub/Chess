package Other.ChessPieces;

import Other.Coordinates;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;


public abstract class Piece implements Serializable {

    private int x;
    private int y;
    private int cX;
    private int cY;
    private boolean isWhite;
    private PieceType type;
    private transient BufferedImage image;
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

    /**
     * Sets the image of the piece based on the given path.
     *
     * @param  path  the path to the image file
     */
    public void setImage(String path) {
        try {
            URL url = getClass().getClassLoader().getResource(path);
            image =ImageIO.read(Objects.requireNonNull(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Computes the image path for the piece based on its color and type.
     *
     * @return The image path for the piece.
     */
    private String computeImagePath() {
        String colorSuffix = isWhite ? "w" : "b";
        String typePrefix;
        if (type == PieceType.KNIGHT){
            typePrefix = String.valueOf(type.toString().toLowerCase().toCharArray()[1]);
        }else {
            typePrefix = String.valueOf(type.toString().toLowerCase().toCharArray()[0]);
        }
        return typePrefix + colorSuffix + ".png";
    }

    /**
     * Calculates the allowed movements for the piece on the given board for the specified color.
     *
     * @param  board   the chess board represented as a 2D array of strings
     * @param  isWhite a boolean indicating whether the piece is white or black
     * @return         an ArrayList of Coordinates representing the allowed movements for the piece
     */
    public abstract ArrayList<Coordinates> allowedMovements(String[][] board, boolean isWhite);
}


