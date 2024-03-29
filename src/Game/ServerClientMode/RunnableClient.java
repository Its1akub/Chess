package Game.ServerClientMode;

import java.io.IOException;

public class RunnableClient implements Runnable {
    private int port;
    private Client client;

    public RunnableClient(int port) {
        client = new Client(port);
    }

    @Override
    public void run() {
        try {
            client.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public Client getClient() {
        return client;
    }
}
