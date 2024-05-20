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

    /**
     * Executes the Stockfish engine by running the specified executable file.
     * Sends the "uci" and "isready" commands to initialize the engine and wait for it to be ready.
     *
     * @throws RuntimeException if there is an error executing the process
     */
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

    /**
     * Sends a command to the process using the specified BufferedReader and Process.
     *
     * @param  reader   the BufferedReader used to read the output from the process
     * @param  command  the command to send to the process
     * @param  process  the Process to send the command to
     */
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
    /**
     * Retrieves the best move from the Stockfish engine for a given FEN (Forsyth-Edwards Notation) and search depth.
     *
     * @param  fen      the FEN string representing the current state of the chess board
     * @param  level    the search depth to determine the best move
     * @return          the best move in coordinate notation (e.g., "e2e4")
     */
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

    /**
     * Returns the value of the waitForReady flag.
     *
     * @return true if the engine is waiting for the "isready" command to be sent, false otherwise.
     */
    public boolean isWaitForReady() {
        return waitForReady;
    }
}