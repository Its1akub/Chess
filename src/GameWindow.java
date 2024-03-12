import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameWindow extends JFrame {
    IntroWindow introWindow = new IntroWindow();
    EscapeKeyAdapter escapeKeyAdapter = new EscapeKeyAdapter();
    SelectingGame selectingGame = new SelectingGame();
    Chess chess = new Chess();
    ServerPanel server;

    JButton exitButton;
    private JPanel currentPanel;

    public GameWindow() {
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        setTitle("Chess");
        getContentPane().add(exitButton);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(escapeKeyAdapter);

        escapeKeyAdapter.getStopMenu(Chess.stopMenu);
        escapeKeyAdapter.getStopMenuShown(Chess.stopMenuShown);

        currentPanel = introWindow;
        introWindow.playButton.addActionListener(e -> switchToPanel(selectingGame));
        selectingGame.localButton.addActionListener(e -> {
            try {
                switchToPanel(server = new ServerPanel());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
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
