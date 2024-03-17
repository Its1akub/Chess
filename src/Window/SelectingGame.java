package Window;

import Other.ChessPieces.Bishop;
import Other.ChessPieces.King;
import Other.ChessPieces.Knight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SelectingGame extends JPanel implements MouseMotionListener, MouseListener {
    JButton localHostButton, localJoinButton;

    Bishop b;
    Knight kn;
    int x = 0;
    int y = 0;
    int hitbox = 50;
    boolean hold = false;

    public SelectingGame() {
        setBackground(Color.RED);
        localHostButton = new JButton("Local host");
        add(localHostButton, BorderLayout.CENTER);
        localJoinButton = new JButton("Local join");
        add(localJoinButton, BorderLayout.SOUTH);

        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        this.b = new Bishop(0, 0, true);
        this.kn = new Knight(0, 0, true);
    }

    @Override
    public void paintComponent(Graphics g1d) {
        Graphics2D g = (Graphics2D) g1d;
        g.setColor(new Color(115, 56, 39));
        g.fillRect(0, 0, getWidth(), getHeight());

        int xMoveBy = (getWidth() - hitbox * 16) / 2;
        int yMoveBy = (getHeight() - hitbox * 16) / 2;

        g.setColor(new Color(66, 31, 21));
        g.fillRect(xMoveBy - hitbox, yMoveBy - hitbox, hitbox * 18, hitbox * 18);

        boolean white = false;
        for (int i = 0; i < 8; i++) {
            white = !white;
            for (int j = 0; j < 8; j++) {
                if (white) {
                    g.setColor(new Color(245, 205, 193));
                } else {
                    g.setColor(new Color(107, 66, 54));
                }
                g.fillRect(i * hitbox * 2 + xMoveBy, j * hitbox * 2 + yMoveBy, hitbox * 2, hitbox * 2);
                white = !white;
            }
        }

        if (hold) {
            g.setColor(new Color(0, 0, 0, 30));
            g.fillRect(x - hitbox, y - hitbox, hitbox * 2, hitbox * 2);
        }

        x = Math.min(x, getWidth());
        y = Math.min(y, getHeight());
        x = Math.max(x, 0);
        y = Math.max(y, 0);
        g.drawImage(b.getImage(), x - hitbox, y - hitbox, hitbox * 2, hitbox * 2, null);
        g.drawImage(kn.getImage(), x - hitbox, y - hitbox, hitbox * 2, hitbox * 2, null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println(e.getX() + ", " + e.getY());
        //System.out.println(x + ", " + y);
        if (e.getX() >= x - hitbox && e.getX() <= x + hitbox && e.getY() >= y - hitbox && e.getY() <= y + hitbox) {
            x = e.getX();
            y = e.getY();
            hold = true;
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //pokus o snapování
        /*int xMoveBy = (getWidth() - hitbox 16) / 2;
            int yMoveBy = (getHeight() - hitbox * 16) / 2;
            if (e.getX() >= xMoveBy && e.getX() <= hitbox * 16 + xMoveBy && e.getY() >= yMoveBy && e.getY() <= hitbox * 16 + yMoveBy){
                x = (int) Math.round(e.getX() / 8.0 - xMoveBy);
                y = (int) Math.round(e.getY() / 8.0 - yMoveBy);
                x = x * e.getX() + xMoveBy;
                y = y * e.getY() + yMoveBy;
            }*/
        hold = false;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}