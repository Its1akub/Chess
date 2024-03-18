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
    Chess chess ;
    ServerPanel server;
    ClientPanel clientPanel;

    JButton exitButton;
    private JPanel currentPanel;

    public GameWindow() {
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        setTitle("Chess");
        getContentPane().add(exitButton);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println( screenSize.getWidth() + ", " + screenSize.getHeight());
        chess = new Chess((int) screenSize.getWidth(), (int) screenSize.getHeight());


        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(escapeKeyAdapter);

        escapeKeyAdapter.getStopMenu(Chess.stopMenu,Chess.stopMenuShown);

        currentPanel = introWindow;

        introWindow.playButton.addActionListener(e -> switchToPanel(chess));
        selectingGame.localHostButton.addActionListener(e -> {
            try {
                switchToPanel(server = new ServerPanel());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        selectingGame.localJoinButton.addActionListener(e -> switchToPanel(clientPanel = new ClientPanel()));

        add(introWindow, BorderLayout.CENTER);
        setVisible(true);
    }

    private void switchToPanel(JPanel newPanel) {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = newPanel;
        add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
