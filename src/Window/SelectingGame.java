package Window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class SelectingGame extends JPanel {
    JButton localHostButton, localJoinButton, myAIButton,stockfishButton;
    private BufferedImage backgroundImage;

    public SelectingGame() {

        try {
            backgroundImage = ImageIO.read(new File("src/main/resources/loading.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        localHostButton = new JButton("Local host");
        add(localHostButton, BorderLayout.CENTER);
        localJoinButton = new JButton("Local join");
        add(localJoinButton, BorderLayout.SOUTH);
        myAIButton = new JButton("My AI");
        add(myAIButton, BorderLayout.NORTH);
        stockfishButton = new JButton("Stockfish");
        add(stockfishButton, BorderLayout.WEST);

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}