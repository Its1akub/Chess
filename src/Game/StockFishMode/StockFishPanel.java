package Game.StockFishMode;

import Game.Chess;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class StockFishPanel extends JPanel {

    Chess chess;

    public StockFishPanel(int width, int height) {
        setBackground(Color.YELLOW);
        setLayout(new BorderLayout());

        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean x = new AtomicBoolean(false);
        Thread backgroundThread = new Thread(() -> {
            Random random = new Random();
            x.set(random.nextBoolean());
            chess = new Chess(width, height, x.get());
            add(chess, BorderLayout.CENTER);
            revalidate();
            repaint();

            latch.countDown();
        });
        backgroundThread.start();

        try {
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        backgroundThread.interrupt();

        StockFishRunnable runnable = new StockFishRunnable(chess, x.get(),width,height);
        Thread thread1 = new Thread(runnable);
        thread1.start();
    }
}