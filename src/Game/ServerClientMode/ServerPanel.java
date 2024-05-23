package Game.ServerClientMode;

import Game.Chess;
import Game.Mechanics;
import Game.Move;
import Game.PiecesLoad;
import Game.ServerClientMode.Server;
import Other.ChessPieces.Piece;
import Other.Coordinates;
import Other.Users.ColorSide;
import Other.Users.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static Game.Mechanics.addPieceAfterMove;
import static Game.ServerClientMode.MechanicsForMultiplayer.convertMoveToSendMove;


public class ServerPanel extends JPanel {
    //private Server server;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private Chess chess;
    private Label serverPort;
    private Label waitingForClient;
    private ServerSocket serverSocket;

    private Socket clientSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private InputStream is;
    private OutputStream os;
    private Player pHost, pGuest;
    private boolean runChess;
    private SendMove moveHost, moveGuest;

    public ServerPanel(int width, int height) {
        setBackground(Color.YELLOW);
        setLayout(new BorderLayout());
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final int[] port = new int[1];
        port[0] = serverSocket.getLocalPort();

        serverPort = new Label("Server Port: " + port[0]);
        System.out.println(port[0]);
        waitingForClient = new Label("Waiting for client...");
        add(serverPort, BorderLayout.NORTH);
        add(waitingForClient, BorderLayout.SOUTH);

        Thread thread = new Thread(() -> {
            try {

                ArrayList<Player> colors = Mechanics.setColorsOfPlayers();
                pHost = colors.get(0);
                pGuest = colors.get(1);
                clientSocket = serverSocket.accept();
                System.out.println(clientSocket);
                remove(serverPort);
                remove(waitingForClient);
                //pHost.getColor() == ColorSide.WHITE
                chess = new Chess(width, height, false);
                add(chess, BorderLayout.CENTER);
                chess.setVisible(true);
                revalidate();
                repaint();
                pieces = chess.getChessBoard().getPieces();

                is = clientSocket.getInputStream();
                os = clientSocket.getOutputStream();
                ois = new ObjectInputStream(is);
                oos = new ObjectOutputStream(os);
                oos.writeObject(pGuest);
                oos.writeObject(pHost);
                while (true) {

                    if (pHost.getColor() == ColorSide.WHITE) {
                        hostMoveMethod();
                        moveGuest = (SendMove) ois.readObject();
                        System.out.println("Received move: " + moveGuest);
                        printGuestMove(moveGuest);
                        chess.getChessBoard().setMadeMove(false);
                    } else {
                        chess.getChessBoard().setMadeMove(true);
                        moveGuest = (SendMove) ois.readObject();
                        printGuestMove(moveGuest);
                        System.out.println("Received move: " + moveGuest);
                        chess.getChessBoard().setMadeMove(false);
                        hostMoveMethod();
                    }
                }
            } catch (IOException | ClassNotFoundException | NullPointerException e) {
                throw new RuntimeException(e);
            }

        });
        thread.start();
    }

    private void hostMoveMethod() throws IOException {
        while (!chess.getChessBoard().isMadeMove()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }

        pieces = chess.getChessBoard().getPieces();
        moveHost = convertMoveToSendMove(chess.getChessBoard().getLastMove());
        System.out.println("sent move: " + moveHost);
        SendMove mhRotate = moveHost;
        mhRotate.rotateCoordinates();

        oos.writeObject(mhRotate);
        oos.flush();
        moveHost = null;

    }

    private void printGuestMove(SendMove moveGuest) {
        //ArrayList<Piece> piecesAfterMove = pieces;
        int i = 0;
        for (Piece p : pieces) {
            if (moveGuest != null && p.getcX() == moveGuest.getPreviousCX() && p.getcY() == moveGuest.getPreviousCY()) {
                p.setcX(moveGuest.getCurrentCX());
                p.setcY(moveGuest.getCurrentCY());
                //piecesAfterMove.add(i,);
                //addPieceAfterMove(piecesAfterMove, p, moveGuest.getCurrentCX(), moveGuest.getCurrentCY());
            }
            i++;
        }
        chess.getChessBoard().setPieces(pieces);
    }
}
