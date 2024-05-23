package Game.ServerClientMode;

import Game.Chess;
import Game.Move;
import Other.ChessPieces.Piece;
import Other.Users.ColorSide;
import Other.Users.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static Game.Mechanics.addPieceAfterMove;
import static Game.ServerClientMode.MechanicsForMultiplayer.convertMoveToSendMove;

public class ClientPanel extends JPanel {
    private Chess chess;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private JLabel portLabel;
    private JTextArea portArea;
    private JButton connectButton;
    private Socket clientSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private InputStream is;
    private OutputStream os;
    private Player pGuest, pHost;
    private SendMove moveHost, moveGuest;

    public ClientPanel(int width, int height) {
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());

        portLabel = new JLabel("Enter port: ");
        add(portLabel, BorderLayout.NORTH);
        portArea = new JTextArea(1, 6);
        add(portArea, BorderLayout.SOUTH);
        connectButton = new JButton("Connect");
        add(connectButton, BorderLayout.EAST);
        AtomicInteger port = new AtomicInteger();
        final Object lock = new Object();
        connectButton.addActionListener(e -> {
            if (portArea.getText().length() <= 6) {
                try {
                    port.set(Integer.parseInt(portArea.getText()));
                    synchronized (lock) {
                        lock.notify();
                        System.out.println("Button pressed, ClientPanel notified.");
                    }
                    System.out.println("Connecting to port... " + port.get());
                } catch (Exception ex) {
                    System.out.println("Couldn't connect to 'localhost' on port: " + portArea.getText());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Enter a valid port number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("ClientPanel waiting...");
                    lock.wait();  // Wait until notified
                    System.out.println("ClientPanel notified!");

                    try {
                        clientSocket = new Socket("localhost", port.get());
                        is = clientSocket.getInputStream();
                        os = clientSocket.getOutputStream();
                        oos = new ObjectOutputStream(os);
                        ois = new ObjectInputStream(is);
                        pGuest = (Player) ois.readObject();
                        pHost = (Player) ois.readObject();

                        remove(portLabel);
                        remove(portArea);
                        remove(connectButton);
                        //pHost.getColor() == ColorSide.BLACK
                        chess = new Chess(width, height,true);
                        add(chess, BorderLayout.CENTER);
                        chess.setVisible(true);
                        revalidate();
                        repaint();
                        pieces = chess.getChessBoard().getPieces();

                        while (true) {
                            if (pHost.getColor() == ColorSide.WHITE) {
                                chess.getChessBoard().setMadeMove(true);
                                moveHost = (SendMove) ois.readObject();
                                printHostMove(moveHost);
                                System.out.println("Received move: " + moveHost);
                                chess.getChessBoard().setMadeMove(false);
                                guestMoveMethod();
                            } else {
                                guestMoveMethod();
                                moveHost = (SendMove) ois.readObject();
                                System.out.println("Received move: " + moveHost);
                                printHostMove(moveHost);
                                chess.getChessBoard().setMadeMove(false);
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void guestMoveMethod() throws IOException {
        while (!chess.getChessBoard().isMadeMove()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        pieces = chess.getChessBoard().getPieces();
        moveGuest = convertMoveToSendMove(chess.getChessBoard().getLastMove());
        System.out.println("sent move: " + moveGuest);
        SendMove mgRotate = moveGuest;
        mgRotate.rotateCoordinates();

        oos.writeObject(mgRotate);
        oos.flush();
        moveGuest = null;

    }

    private void printHostMove(SendMove moveHost) {
        //ArrayList<Piece> piecesAfterMove = pieces;
        for (Piece p : pieces) {
            if (moveHost != null && p.getcX() == moveHost.getPreviousCX() && p.getcY() == moveHost.getPreviousCY()) {
                //piecesAfterMove.remove(p);
                //addPieceAfterMove(piecesAfterMove, p, moveHost.getCurrentCX(), moveHost.getCurrentCY());
                p.setcX(moveHost.getCurrentCX());
                p.setcY(moveHost.getCurrentCY());
            }
        }
        chess.getChessBoard().setPieces(pieces);
    }
}
