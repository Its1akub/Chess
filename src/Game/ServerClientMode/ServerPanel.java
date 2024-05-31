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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static Game.Ckeckmate.checkmate;
import static Game.MechanicsForBoard.castling;
import static Game.ServerClientMode.MechanicsForMultiplayer.convertMoveToSendMove;
import static Game.ServerClientMode.MechanicsForMultiplayer.convertSendMoveToMove;



public class ServerPanel extends JPanel {

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
    private boolean kingMoves = false;
    private boolean lRookMoves = false;
    private boolean rRookMoves = false;;
    private SendMove moveHost, moveGuest;
    private boolean isWhite;
    private AudioMethod audioMethod;

    public ServerPanel(int width, int height) {
        setBackground(Color.YELLOW);
        setLayout(new BorderLayout());
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        audioMethod = new AudioMethod();

        final int[] port = new int[1];
        port[0] = serverSocket.getLocalPort();
        System.out.println(serverSocket.getLocalSocketAddress());
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
                isWhite = pHost.getColor() == ColorSide.WHITE;
                chess = new Chess(width, height, isWhite);
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
                int round = 1;
                while (true) {
                    String[][] board = chess.getChessBoard().getBoard();
                    chess.getMovePanel().setLineNumber(round);
                    chess.getMovePanel().newLine();

                    if (pHost.getColor() == ColorSide.WHITE) {
                        hostMoveMethod();
                        if (checkmate(true, board)) break;
                        moveGuest = (SendMove) ois.readObject();
                        System.out.println("Received move: " + moveGuest);
                        board = chess.getChessBoard().getBoard();
                        printGuestMove(moveGuest);
                        if (checkmate(false, board)) break;
                        chess.getChessBoard().setMadeMove(false);
                    } else {
                        chess.getChessBoard().setMadeMove(true);
                        moveGuest = (SendMove) ois.readObject();
                        printGuestMove(moveGuest);
                        if (checkmate(false, board)) break;
                        System.out.println("Received move: " + moveGuest);
                        chess.getChessBoard().setMadeMove(false);
                        board = chess.getChessBoard().getBoard();
                        hostMoveMethod();
                        if (checkmate(true, board)) break;
                    }
                    round++;
                }
                chess.getChessBoard().setMadeMove(true);
                System.out.println("end");
            } catch (IOException | ClassNotFoundException | NullPointerException e) {
                throw new RuntimeException(e);
            }

        });
        thread.start();
    }

    /**
     * Executes the host's move in a chess game.
     *
     * @throws IOException if an I/O error occurs while writing the move to the output stream.
     */
    private void hostMoveMethod() throws IOException {
        String[][] boardFEN = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
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

        Move hostMove = chess.getChessBoard().getLastMove();

        String mH = MechanicsForBoard.convertMoveToString(hostMove);
        String mHInNotation = MechanicsForMovePanel.convertNumbersToChessNotation(mH, boardFEN, isWhite, false);
        chess.getMovePanel().addMove(mHInNotation);
        chess.getMovePanel().print();

        moveHost = null;

    }

    /**
     * Prints the guest's move on the chessboard.
     *
     * @param  moveGuest  the guest's move to be printed
     */
    private void printGuestMove(SendMove moveGuest) {
        int size = chess.getChessBoard().getPieces().size();
        String[][] boardFEN = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
        ArrayList<Piece> piecesToRemove = new ArrayList<>();
        for (Piece p : pieces) {
            if (moveGuest != null && p.getcX() == moveGuest.getCurrentCX() && p.getcY() == moveGuest.getCurrentCY()) {
                piecesToRemove.add(p);
            }
        }
        pieces.removeAll(piecesToRemove);
        ArrayList<Piece> piecesCopy = new ArrayList<>(pieces);
        for (Piece p : piecesCopy) {
            if (moveGuest != null && p.getcX() == moveGuest.getPreviousCX() && p.getcY() == moveGuest.getPreviousCY()) {
                boolean movingWithKing = p instanceof King;
                p.setcX(moveGuest.getCurrentCX());
                p.setcY(moveGuest.getCurrentCY());
                if (movingWithKing && !kingMoves && !lRookMoves && !rRookMoves) {
                    String[][] board = chess.getChessBoard().getBoard();
                    ArrayList<Piece> rePieces = new ArrayList<>(pieces);
                    Move move = new Move(moveGuest.getPreviousCX(), moveGuest.getPreviousCY(), p, moveGuest.getCurrentCX()+1, moveGuest.getCurrentCY());
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

        Move guestMove = convertSendMoveToMove(moveGuest);
        chess.getChessBoard().setOpponentMove(guestMove);
        chess.getChessBoard().repaint();
        String mG = MechanicsForBoard.convertMoveToString(guestMove);
        String mGInNotation = MechanicsForMovePanel.convertNumbersToChessNotation(mG, boardFEN, isWhite, false);

        chess.getMovePanel().addMove(mGInNotation);
        chess.getMovePanel().print();
    }
}
