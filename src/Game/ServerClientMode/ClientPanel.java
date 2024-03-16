package Game.ServerClientMode;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class ClientPanel extends JPanel {
    public ClientPanel() {
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        JLabel portLabel = new JLabel("Enter port: ");
        add(portLabel, BorderLayout.NORTH);
        JTextArea portArea = new JTextArea(1, 6);
        add(portArea, BorderLayout.SOUTH);
        JButton connectButton = new JButton("Connect");
        add(connectButton, BorderLayout.EAST);
        connectButton.addActionListener(e -> {
            if (portArea.getText().length() <= 6) {
                try {
                    Socket socket = new Socket("localhost", Integer.parseInt(portArea.getText()));
                    System.out.println("Connecting to port... " + portArea.getText());
                    socket.close();

                    int port = Integer.parseInt(portArea.getText());
                    Client client = new Client();
                    client.startConnection(port);
                } catch (Exception ex) {
                    System.out.println("Couldn't connect to 'localhost' on port: " + portArea.getText());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Enter a valid port number", "Error", JOptionPane.ERROR_MESSAGE);
            }


        });

    }
}
