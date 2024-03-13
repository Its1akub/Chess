package Game.ServerClientMode;

import Game.ServerClientMode.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class ServerPanel extends JPanel {
    Server server;

    public ServerPanel() throws IOException {
        setBackground(Color.YELLOW);
        setLayout(new BorderLayout());

        server = new Server();
        int port = server.getPort();
        Label serverPort = new Label("Game.ServerClientMode.Server Port: " + port);
        System.out.println(port);
        Label waitingForClient = new Label("Waiting for client...");
        add(serverPort, BorderLayout.NORTH);
        add(waitingForClient, BorderLayout.SOUTH);
        revalidate();
        repaint();

        server.start();

    }
}
