package Game.AI;


import Other.Users.ColorSide;

import java.util.List;

public class MyAI{
    private ColorSide maximizingColor;
    private int nodesVisited;

    public MyAI(ColorSide maximizingColor) {
        this.maximizingColor = maximizingColor;
        /*Board gameBoard = new Board();
        gameBoard.setBoard("6N1/5P2/6K1/8/BRr4p/7Q/1Pp1p1PP/1n1b2k1 w - - 0 1");
        ArrayList<BoardMove> possibleMoves = gameBoard.getAllPossibleMoves();
        for (BoardMove possibleMove : possibleMoves) {
            System.out.println(possibleMove);
        }*/
    }

    /**
     * Finds the best move for AI on the given board state within the given time limit and depth.
     * Performs minimax search with alpha-beta pruning.
     *
     * @param  fen            the initial board state in FEN notation
     * @param  timeLimit      the maximum time in milliseconds to spend on the search
     * @param  depthMax       the maximum depth to search to
     * @return                the best move found, or null if no move was found within the time limit
     */
    public BoardMove findBestMove(String fen, long timeLimit, int depthMax) {
        Board board = new Board();
        board.setBoard(fen);
        long startTime = System.currentTimeMillis();
        boolean maximizingPlayer = maximizingColor == ColorSide.WHITE;
        BoardMove bestMove = null;
        nodesVisited = 0;
        for (int depth = 1; depth <= depthMax; depth++) {
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;
            int bestScore = Integer.MIN_VALUE;
            BoardMove currentBestMove = null;

            // Perform minimax search with alpha-beta pruning
            List<BoardMove> possibleMoves = board.getAllPossibleMoves(maximizingPlayer); // Get moves without printing
            for (BoardMove move : possibleMoves) {
                board.makeMove(move);
                int score = minimax(board, depth, alpha, beta, !maximizingPlayer);
                board.undoMove(move);

                if (score > bestScore) {
                    bestScore = score;
                    currentBestMove = move;
                }
                alpha = Math.max(alpha, bestScore);

                if (System.currentTimeMillis() - startTime >= timeLimit) {
                    System.out.println("Search stopped due to time limit.");
                    return bestMove;
                }

                nodesVisited++;
            }


            if (currentBestMove != null) {
                bestMove = currentBestMove;
            } else {

                break;
            }

            System.out.println("Nodes visited: " + nodesVisited + ", Depth: " + depth + ", Best score: " + bestScore + ", Best move: " + bestMove);
        }
        System.out.println("Total time: " + (System.currentTimeMillis() - startTime) + "ms");
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                System.out.print(board.getBoard()[i][j] + "| ");
            }
            System.out.println();
        }
        return bestMove;
    }

    /**
     * Calculates the minimax value for a given board state using the minimax algorithm.
     *
     * @param  board               the current board state
     * @param  depth               the depth of the current move
     * @param  alpha               the alpha value for alpha-beta pruning
     * @param  beta                the beta value for alpha-beta pruning
     * @param  maximizingPlayer    true if it is the maximizing player's turn, false otherwise
     * @return                     the minimax value of the board state
     */
    private int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
       // Board board = previousBoard;
        nodesVisited++;
        //|| board.isGameOver()
        if (depth == 0) {
            return evaluate(board);
        }

        if (maximizingPlayer) {
            int maxScore = Integer.MIN_VALUE;

            for (BoardMove move : board.getAllPossibleMoves(false)) {
                board.makeMove(move);
                int score = minimax(board, depth - 1, alpha, beta, false);
                board.undoMove(move);
                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (BoardMove move : board.getAllPossibleMoves(true)) {
                board.makeMove(move);
                int score = minimax(board, depth - 1, alpha, beta, true);
                board.undoMove(move);
                minScore = Math.min(minScore, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return minScore;
        }
    }

    /**
     * Evaluates the given board by summing the value of each piece on the board.
     *
     * @param  board  the board to be evaluated
     * @return        the score of the board, with a positive score for the white player and a negative score for the black player
     */
    private int evaluate(Board board) {
        // Simple evaluation function, can be improved
        int score = 0;
        String[][] boardState = board.getBoard();

        for (String[] row : boardState) {
            for (String piece : row) {
                if (!piece.equals(" ")) {
                    score += pieceValue(piece);
                }
            }
        }

        return maximizingColor == ColorSide.WHITE ? score : -score;
    }

    /**
     * Returns the value of a chess piece based on its type.
     *
     * @param  piece  the type of the chess piece (e.g., "P", "N", "B", "R", "Q", "K", "p", "n", "b", "r", "q", "k")
     * @return        the value of the chess piece (positive for white pieces, negative for black pieces)
     */
    private int pieceValue(String piece) {
        return switch (piece) {
            case "P" -> 10;
            case "N", "B" -> 30;
            case "R" -> 50;
            case "Q" -> 90;
            case "K" -> 900;
            case "p" -> -10;
            case "n", "b" -> -30;
            case "r" -> -50;
            case "q" -> -90;
            case "k" -> -900;
            default -> 0;
        };
    }
}
