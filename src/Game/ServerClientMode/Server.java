package Game.ServerClientMode;

import Game.Chess;
import Other.Users.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.WebSocket;

public class Server {
    private final ServerSocket serverSocket;
    private Socket clientSocket;
    private final int port;
    ObjectInputStream ois;
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


        Object obj;
        try {
            clientSocket = serverSocket.accept();

            System.out.println(clientSocket);
            ois = new ObjectInputStream(clientSocket.getInputStream());
            obj = ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            if (obj instanceof Player) {
                pGuest = (Player) obj;
                pHost = new Player();
                break;
            }
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

    public Player getpGuest() {
        return pGuest;
    }
}
