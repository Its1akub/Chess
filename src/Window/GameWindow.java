package Window;

import Game.AI.MyAIPanel;
import Game.Chess;
import Game.AI.MyAI;
import Game.MyAIModeNotWorking.RunMyAINotWorking;
import Game.ServerClientMode.ClientPanel;
import Game.ServerClientMode.ServerPanel;
import Game.StockFishMode.StockFishPanel;
import Other.EscapeKeyAdapter;
import Other.Users.ColorSide;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    IntroWindow introWindow = new IntroWindow();
    EscapeKeyAdapter escapeKeyAdapter = new EscapeKeyAdapter();
    SelectingGame selectingGame = new SelectingGame();
    RunMyAINotWorking runMyAINotWorking;
    MyAIPanel m;
    StockFishPanel stockfish;
    ServerPanel server;
    ClientPanel clientPanel;

    JButton exitButton;
    private JPanel currentPanel = introWindow;
    private int width, height;

    public GameWindow() {
        exitButton = new JButton("Exit");
        setPreferredSize(new Dimension());
        //getContentPane().add(exitButton);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setUndecorated(true);
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
        addKeyListener(escapeKeyAdapter);
        add(introWindow, BorderLayout.CENTER);
        setVisible(true);
        escapeKeyAdapter.getStopMenu(Chess.stopMenu, Chess.stopMenuShown);
        setButtons();

    }

    public void switchToPanel(JPanel newPanel) {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = newPanel;
        add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void setButtons() {
        exitButton.addActionListener(e -> System.exit(0));
        introWindow.playButton.addActionListener(e -> switchToPanel(selectingGame));
        selectingGame.localHostButton.addActionListener(e -> switchToPanel(server = new ServerPanel(width, height)));
        selectingGame.localJoinButton.addActionListener(e -> switchToPanel(clientPanel = new ClientPanel(width, height)));
        selectingGame.stockfishButton.addActionListener(e -> switchToPanel(stockfish = new StockFishPanel(width, height)));
        selectingGame.myAIButton.addActionListener(e -> switchToPanel(m = new MyAIPanel(width, height)));
    }
}
