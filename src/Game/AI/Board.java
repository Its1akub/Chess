package Game.AI;

import java.util.ArrayList;

public class Board {
    private String[][] board = new String[8][8];
    private String attackedPiece;

    public void setBoard(String fen){
        board = MechanicsForMyAI.fenToBoard(fen);
    }

    public String[][] getBoard() {
        return board;
    }

    /**
     * Retrieves all possible moves for AI on the board.
     *
     * @param  isWhite  a boolean indicating whether AI is white
     * @return          an ArrayList of BoardMove objects representing all possible moves
     */
    public ArrayList<BoardMove> getAllPossibleMoves(boolean isWhite) {
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<PossibleMovesForType> list = new ArrayList<>();
        ArrayList<BoardMove> moves = new ArrayList<>();
        char[] chars = {'p', 'n', 'b', 'r', 'q', 'k'};
        for (int i = 0; i < 6; i++) {
            PossibleMovesForType possibleMovesForType = new PossibleMovesForType(chars[i] + "", board);
            Thread t = new Thread(possibleMovesForType);
            t.start();
            list.add(possibleMovesForType);
            threads.add(t);
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (PossibleMovesForType movesForType : list) {
            for (int j = 0; j < movesForType.getMoves().size(); j++) {
                if (movesForType.getMoves().get(j).getType().matches("[A-Z]") && isWhite) {
                    moves.add(movesForType.getMoves().get(j));
                } else if (movesForType.getMoves().get(j).getType().matches("[a-z]") && !isWhite) {
                    moves.add(movesForType.getMoves().get(j));
                }
            }

        }
        /*for (PossibleMovesForType possibleMovesForType : list) {
            if (possibleMovesForType.getMoves().get())
            moves.addAll(possibleMovesForType.getMoves());
        }*/
        return moves;
    }

    /**
     * Makes a move on the board based on the given BoardMove object.
     *
     * @param  move  the BoardMove object representing the move to be made
     */
    public void makeMove(BoardMove move) {
        int startX = move.getStartX();
        int startY = move.getStartY();
        int endX = move.getEndX();
        int endY = move.getEndY();

        String piece = board[startY][startX];

        board[startY][startX] = " "; // Empty the start position
        if (!board[endY][endX].equals(" ")){
            boolean color = piece.matches("[A-Z]");
            String endPlace = board[endY][endX];
            boolean endColor = endPlace.matches("[A-Z]");
            if (color != endColor) attackedPiece = board[endY][endX];
        }
        board[endY][endX] = piece; // Move the piece to the end position
    }


    /**
     * Undoes a move on the board based on the given BoardMove object.
     *
     * @param  move  the BoardMove object representing the move to be undone
     */
    public void undoMove(BoardMove move) {
        int startX = move.getStartX();
        int startY = move.getStartY();
        int endX = move.getEndX();
        int endY = move.getEndY();

        String piece = board[endY][endX];
        if (attackedPiece == null){
            board[endY][endX] = " ";
        } else {
            board[endY][endX] = attackedPiece;
            attackedPiece = null;
        }
        board[startY][startX] = piece; // Move the piece back to the start position
    }

}
