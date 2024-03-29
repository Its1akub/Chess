package Window;

import Game.Chess;
import Game.ServerClientMode.ClientPanel;
import Game.ServerClientMode.ServerPanel;
import Other.EscapeKeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameWindow extends JFrame {
    IntroWindow introWindow = new IntroWindow();
    EscapeKeyAdapter escapeKeyAdapter = new EscapeKeyAdapter();
    SelectingGame selectingGame = new SelectingGame();
    Chess chess;
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
    }
}
