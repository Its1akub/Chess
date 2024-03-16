package Other.ChessPieces;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public abstract class Piece extends JPanel {

    private int x;
    private int y;
    private boolean isWhite;
    private PieceType type;
    private Image image;

    public Piece(int x, int y, boolean isWhite, PieceType type, String path) {
        setX(x);
        setY(y);
        setWhite(isWhite);
        setType(type);
        setImage(path);
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

    public Image getImage() {
        return image;
    }

    public void setImage(String path) {
        image = new ImageIcon(path).getImage();
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), getX() * 100, getY() * 100, 100, 100, null);
    }

}
