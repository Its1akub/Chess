package Game.ServerClientMode;

import Game.Chess;
import Other.Users.Player;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private BufferedReader in;

    private Player player;
    private int port;
    private boolean runChess;

    public Client( int port) {
        this.port = port;
        try {
            clientSocket = new Socket("localhost", port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //this.chess = chess;
    }


    public void connect() {
            try {
                oos = new ObjectOutputStream(clientSocket.getOutputStream());
                player = new Player();
                oos.writeObject(player);
                //chess.setVisible(true);
                runChess = true;
                //out = new PrintWriter(clientSocket.getOutputStream(), true);
                //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public boolean isRunChess() {
        return runChess;
    }
}
