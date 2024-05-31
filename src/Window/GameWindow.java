package Window;

import Game.AI.MyAIPanel;
import Game.ServerClientMode.ClientPanel;
import Game.ServerClientMode.ServerPanel;
import Game.StockFishMode.StockFishPanel;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    IntroWindow introWindow = new IntroWindow();
    SelectingGame selectingGame = new SelectingGame();
    //RunMyAINotWorking runMyAINotWorking;
    MyAIPanel m;
    StockFishPanel stockfish;
    ServerPanel server;
    ClientPanel clientPanel;

    JButton exitButton;
    private JPanel currentPanel = introWindow;
    private int width, height;

    public GameWindow() {
        setPreferredSize(new Dimension());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (isUndecorated()) {
            width = (int) screenSize.getWidth();
            height = (int) screenSize.getHeight();
        }else{
            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
            width = (int) screenSize.getWidth();
            height = (int) screenSize.getHeight()-20 - insets.bottom;
        }
        System.out.println(width + " " + height);

        setFocusable(true);
        requestFocusInWindow();
        add(introWindow, BorderLayout.CENTER);
        setVisible(true);
        setButtons();

    }

    /**
     * Switches the current panel to the specified new panel.
     *
     * @param  newPanel  the new panel to switch to
     */
    public void switchToPanel(JPanel newPanel) {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = newPanel;
        add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Sets up the action listeners for the buttons in the game window.
     * This method adds action listeners to the exit button, play button, local host button,
     * local join button, stockfish button, and my AI button. When each button is clicked,
     * the corresponding action is performed.
     *
     */
    private void setButtons() {
        //exitButton.addActionListener(e -> System.exit(0));
        introWindow.playButton.addActionListener(e -> switchToPanel(selectingGame));
        selectingGame.localHostButton.addActionListener(e -> switchToPanel(server = new ServerPanel(width, height)));
        selectingGame.localJoinButton.addActionListener(e -> switchToPanel(clientPanel = new ClientPanel(width, height)));
        selectingGame.stockfishButton.addActionListener(e -> switchToPanel(stockfish = new StockFishPanel(width, height)));
        selectingGame.myAIButton.addActionListener(e -> switchToPanel(m = new MyAIPanel(width, height)));
    }
}
