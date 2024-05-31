package Game.ServerClientMode;

import Game.*;
import Game.StockFishMode.MechanicsStockFish;
import Other.AudioMethod;
import Other.ChessPieces.King;
import Other.ChessPieces.Piece;
import Other.ChessPieces.Rook;
import Other.Users.ColorSide;
import Other.Users.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static Game.Ckeckmate.checkmate;
import static Game.MechanicsForBoard.castling;
import static Game.ServerClientMode.MechanicsForMultiplayer.convertMoveToSendMove;
import static Game.ServerClientMode.MechanicsForMultiplayer.convertSendMoveToMove;


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
    private boolean kingMoves = false;
    private boolean lRookMoves = false;
    private boolean rRookMoves = false;
    private boolean isWhite;
    private AudioMethod audioMethod;

    public ClientPanel(int width, int height) {
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());

        audioMethod = new AudioMethod();

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
                    lock.wait();
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

                        isWhite = pHost.getColor() == ColorSide.BLACK;
                        chess = new Chess(width, height,isWhite);

                        add(chess, BorderLayout.CENTER);
                        chess.setVisible(true);
                        revalidate();
                        repaint();
                        pieces = chess.getChessBoard().getPieces();
                        int round = 1;
                        while (true) {
                            String[][] board = chess.getChessBoard().getBoard();

                            chess.getMovePanel().setLineNumber(round);
                            chess.getMovePanel().newLine();

                            if (pHost.getColor() == ColorSide.WHITE) {

                                chess.getChessBoard().setMadeMove(true);
                                moveHost = (SendMove) ois.readObject();
                                printHostMove(moveHost);
                                if (checkmate(true, board)) break;
                                System.out.println("Received move: " + moveHost);
                                chess.getChessBoard().setMadeMove(false);
                                board = chess.getChessBoard().getBoard();
                                guestMoveMethod();
                                if (checkmate(false, board)) break;
                            } else {
                                guestMoveMethod();
                                if (checkmate(true, board)) break;
                                moveHost = (SendMove) ois.readObject();
                                System.out.println("Received move: " + moveHost);
                                board = chess.getChessBoard().getBoard();
                                printHostMove(moveHost);
                                if (checkmate(false, board)) break;
                                chess.getChessBoard().setMadeMove(false);
                            }
                            round++;
                        }
                        chess.getChessBoard().setMadeMove(true);
                        System.out.println("end");
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

    /**
     * Executes the guest's move in a chess game.
     *
     * @throws IOException if an I/O error occurs while writing the move to the output stream.
     */
    private void guestMoveMethod() throws IOException {
        String[][] boardFEN = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
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

        Move guestMove = chess.getChessBoard().getLastMove();
        String mG = MechanicsForBoard.convertMoveToString(guestMove);
        String mGInNotation = MechanicsForMovePanel.convertNumbersToChessNotation(mG, boardFEN, isWhite, false);
        chess.getMovePanel().addMove(mGInNotation);
        chess.getMovePanel().print();

        moveGuest = null;

    }

    /**
     * Prints the move made by the host player on the client side.
     *
     * @param  moveHost  the move made by the host player
     */
    private void printHostMove(SendMove moveHost) {
        int size = chess.getChessBoard().getPieces().size();
        String[][] boardFEN = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
        ArrayList<Piece> piecesToRemove = new ArrayList<>();
        for (Piece p : pieces) {
            if (moveHost != null && p.getcX() == moveHost.getCurrentCX() && p.getcY() == moveHost.getCurrentCY()) {
                piecesToRemove.add(p);
            }
        }
        pieces.removeAll(piecesToRemove);
        ArrayList<Piece> piecesCopy = new ArrayList<>(pieces);
        for (Piece p : piecesCopy) {
            if (moveHost != null && p.getcX() == moveHost.getPreviousCX() && p.getcY() == moveHost.getPreviousCY()) {
                boolean movingWithKing = p instanceof King;
                p.setcX(moveHost.getCurrentCX());
                p.setcY(moveHost.getCurrentCY());
                if (movingWithKing && !kingMoves && !lRookMoves && !rRookMoves) {
                    String[][] board = chess.getChessBoard().getBoard();
                    ArrayList<Piece> rePieces = new ArrayList<>(pieces);
                    Move move = new Move(moveHost.getPreviousCX(), moveHost.getPreviousCY(), p, moveHost.getCurrentCX()+1, moveHost.getCurrentCY());
                    pieces = castling(rePieces, chess.getChessBoard().getxMoveBy(), chess.getChessBoard().getyMoveBy(), move, !chess.getChessBoard().isWhite(), board, false,isWhite);

                }
                if (p instanceof King) {
                    kingMoves = true;
                }
                if (p instanceof Rook) {
                    if (p.getcX() == 0) lRookMoves = true;
                    if (p.getcX() == 7) rRookMoves = true;
                }
            }
        }
        chess.getChessBoard().setPieces(pieces);
        int sizeAfter = chess.getChessBoard().getPieces().size();
        if (sizeAfter < size) {
            audioMethod.audioForChess("audio/capture.wav");
        } else {
            audioMethod.audioForChess("audio/move.wav");
        }

        Move hostMove = convertSendMoveToMove(moveHost);
        chess.getChessBoard().setOpponentMove(hostMove);
        chess.getChessBoard().repaint();
        String mH = MechanicsForBoard.convertMoveToString(hostMove);
        String mHInNotation = MechanicsForMovePanel.convertNumbersToChessNotation(mH, boardFEN, isWhite, false);
        chess.getMovePanel().addMove(mHInNotation);
        chess.getMovePanel().print();
    }
}
