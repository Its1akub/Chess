package Game.AI;

import Game.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;



public class MyAIPanel extends JPanel {
    private  Chess chess;
    public MyAIPanel(int width, int height) {
        setBackground(new Color(64, 61, 57));
        setLayout(new BorderLayout());

        CountDownLatch latch = new CountDownLatch(1);
        Thread backgroundThread = new Thread(() -> {
            chess = new Chess(width, height, false);
            add(chess, BorderLayout.CENTER);
            chess.revalidate();
            revalidate();
            chess.repaint();

            latch.countDown();

        });
        backgroundThread.start();

        try {
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        backgroundThread.interrupt();
        MyAIRunnable runnable = new MyAIRunnable(chess,width,height);
        Thread thread1 = new Thread(runnable);
        thread1.start();


    }
}
