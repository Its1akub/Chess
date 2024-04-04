package Game.ServerClientMode;

import Game.Chess;
import Game.Mechanics;
import Game.PiecesLoad;
import Game.ServerClientMode.Server;
import Other.ChessPieces.Piece;
import Other.Users.ColorSide;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class ServerPanel extends JPanel {
    //private Server server;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private Chess chess;
    private RunnableServer runnableServer;
    Label serverPort;
    Label waitingForClient;
    public ServerPanel(int width, int height) {
        setBackground(Color.YELLOW);
        setLayout(new BorderLayout());

        runnableServer = new RunnableServer();
        Thread thread = new Thread(runnableServer);

        thread.start();
        int port = runnableServer.getServer().getPort();


        //server = new Server();
        //int port = server.getPort();
         serverPort = new Label("Server Port: " + port);
        System.out.println(port);
         waitingForClient = new Label("Waiting for client...");
        add(serverPort, BorderLayout.NORTH);
        add(waitingForClient, BorderLayout.SOUTH);

        Timer timer = new Timer(2500,null);
        timer.addActionListener(e -> {
            if (runnableServer != null && runnableServer.getServer() != null && runnableServer.getServer().isRunChess()) {

                remove(serverPort);
                remove(waitingForClient);
                chess = new Chess(width, height,runnableServer.getServer().getpHost().getColor() == ColorSide.WHITE);
                add(chess, BorderLayout.CENTER);
                chess.setVisible(true);
                revalidate();
                repaint();
                timer.stop();
            }
        });
        timer.start();

    }
}
