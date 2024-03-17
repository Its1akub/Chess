package Other.Users;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class StockFish {
    private Process engineProcess;
    private BufferedReader processReader;
    private OutputStreamWriter processWriter;

    public StockFish() {
        try {
            engineProcess = Runtime.getRuntime().exec("src/main/resources/stockfish/stockfish-windows-x86-64-avx2.exe");
            processReader = new BufferedReader(new InputStreamReader(engineProcess.getInputStream()));
            processWriter = new OutputStreamWriter(engineProcess.getOutputStream());
            // Initialize Stockfish engine
            sendCommand("uci");
            sendCommand("isready");
            waitForResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(String command) {
        try {
            processWriter.write(command + "\n");
            processWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String waitForResponse() {
        StringBuilder response = new StringBuilder();
        try {
            String line;
            while ((line = processReader.readLine()) != null) {
                response.append(line).append("\n");
                if (line.equals("readyok"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public String getBestMove(String fen) {
        sendCommand("position fen " + fen);
        sendCommand("go movetime 1000"); // Adjust the time control as needed
        return waitForResponse();
    }

    public void close() {
        try {
            sendCommand("quit");
            engineProcess.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


