package Game.StockFishMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Stockfish implements Runnable {
    //ProcessBuilder builder;
    BufferedReader reader;
    //OutputStreamWriter writer;
    Process process;
    private boolean waitForReady;


    public void run() {
        try {
            process = Runtime.getRuntime().exec("src/main/resources/stockfish/stockfish-windows-x86-64-avx2.exe");
            //process.waitFor();
            if (process != null) {
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                //writer = new OutputStreamWriter(process.getOutputStream());
                sendCommand(reader, "uci",process);
                sendCommand(reader, "isready",process);
                waitForReady = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Stop Stockfish
        //sendCommand(reader, "quit",process);
    }


    private static void sendCommand(BufferedReader reader, String command, Process process) {
        try {
            reader.readLine(); // Clear buffer
            System.out.println("Sending command: " + command);
            System.out.println();
            process.getOutputStream().write((command + "\n").getBytes());
            process.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String bestMove(String fen, int level) {
        String output = "";
        try {
            OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());
            writer.write("position fen " + fen + "\n");
            writer.write("go depth " + level + "\n");
            writer.flush();

            String line;
            boolean bestMoveFound = false;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line); // Debugging: Print Stockfish output
                if (line.startsWith("bestmove")) {
                    output = line.split(" ")[1];
                    System.out.println(output);
                    bestMoveFound = true;
                    break; // Exit the loop once the best move is found
                }
            }

            if (!bestMoveFound) {
                // Handle case where no best move was found
                output = "No best move found.";
                System.out.println(output);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }


    public boolean isWaitForReady() {
        return waitForReady;
    }
}