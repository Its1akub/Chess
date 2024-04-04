package Game.ServerClientMode;

import Game.Chess;
import Game.Mechanics;
import Other.Users.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.WebSocket;
import java.util.ArrayList;

public class Server {
    private final ServerSocket serverSocket;
    private Socket clientSocket;
    private final int port;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private InputStream is;
    private Player pHost, pGuest;
    private boolean runChess;

    public Server() {
        try {
            serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //this.chess = chess;
    }


    public void start() throws IOException {
        try {
            clientSocket = serverSocket.accept();
            System.out.println(clientSocket);

            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ArrayList<Player> colors = Mechanics.setColorsOfPlayers();
            pHost = colors.get(0);
            pGuest = colors.get(1);
            oos.writeObject(pGuest);
            oos.writeObject(pHost);

        } catch ( IOException e) {
            throw new RuntimeException(e);
        }
        runChess = true;
    }

    public void stop() throws IOException {
        //in.close();
        //out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public int getPort() {
        return port;
    }

    public boolean isRunChess() {
        return runChess;
    }

    public Player getpHost() {
        return pHost;
    }



}
