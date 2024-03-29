package Game.ServerClientMode;

import java.io.IOException;

public class RunnableServer implements Runnable {
    private Server server;

    public RunnableServer() {
        server = new Server();
    }

    @Override
    public void run() {

        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Server getServer() {
        return server;
    }
}
