package Game.ServerClientMode;

import Game.Chess;
import Other.ChessPieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientPanel extends JPanel {
    private Chess chess;
    private RunnableClient runnableClient;
    private ArrayList<Piece> pieces = new ArrayList<>();
    JLabel portLabel;
    JTextArea portArea;
    JButton connectButton;

    public ClientPanel(int width, int height) {
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        chess = new Chess(width, height);
        add(chess, BorderLayout.CENTER);
        chess.setVisible(false);

        portLabel = new JLabel("Enter port: ");
        add(portLabel, BorderLayout.NORTH);
        portArea = new JTextArea(1, 6);
        add(portArea, BorderLayout.SOUTH);
        connectButton = new JButton("Connect");
        add(connectButton, BorderLayout.EAST);

        connectButton.addActionListener(e -> {
            if (portArea.getText().length() <= 6) {
                try {
                    int port = Integer.parseInt(portArea.getText());
                    System.out.println("Connecting to port... " + port);
                    runnableClient = new RunnableClient(port);
                    Thread thread = new Thread(runnableClient);
                    thread.start();
                } catch (Exception ex) {
                    System.out.println("Couldn't connect to 'localhost' on port: " + portArea.getText());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Enter a valid port number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        Timer timer = new Timer(2500, null);
        timer.addActionListener(e -> {
            if (runnableClient != null && runnableClient.getClient() != null && runnableClient.getClient().isRunChess()) {
                remove(portLabel);
                remove(portArea);
                remove(connectButton);
                chess.setVisible(true);
                revalidate();
                repaint();
                timer.stop();
            }
        });
        timer.start();
    }
}
