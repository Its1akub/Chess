package Game.ServerClientMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final int port;
    private boolean connected;

    public Server() {
        try {
            serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void start() {
        new Thread(() -> {
            try {
                clientSocket = serverSocket.accept();
                if (clientSocket.isConnected()) {
                    connected = true;
                }
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String greeting = in.readLine();
                if ("hello server".equals(greeting)) {
                    out.println("hello client");
                } else {
                    out.println("unrecognized greeting");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public int getPort() {
        return port;
    }

    public boolean isConnected() {
        return connected;
    }
}
