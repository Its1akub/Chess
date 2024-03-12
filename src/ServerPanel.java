import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerPanel extends JPanel {
    Server server;
    private int port;

    public ServerPanel() throws IOException {
        setBackground(Color.YELLOW);
        setLayout(new BorderLayout());

        server = new Server();
        port = server.getPort();
        Label serverPort = new Label("Server Port: " + port);
        System.out.println(port);
        Label waitingForClient = new Label("Waiting for client...");
        add(serverPort, BorderLayout.NORTH);
        add(waitingForClient, BorderLayout.SOUTH);
        revalidate();
        repaint();

        server.start();

    }
}
