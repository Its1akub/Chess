package Game.AI;

import java.util.ArrayList;
import java.util.List;


public class PossibleMovesForType implements Runnable {

    private String type;
    private String[][] board;
    private ArrayList<BoardMove> moves = new ArrayList<>();

    public PossibleMovesForType(String type, String[][] board) {
        this.type = type;
        this.board = board;
    }

    @Override
    public void run() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x].toLowerCase().equals(type)) {
                    moves.addAll(getPieceMoves(y, x, board));
                }
            }
        }
    }

    /**
     * Retrieves the possible moves for a given piece on the chessboard.
     *
     * @param  y   the row index of the piece on the chessboard
     * @param  x   the column index of the piece on the chessboard
     * @param  board   the chessboard represented as a 2D array of strings
     * @return      a list of BoardMove objects representing the possible moves for the piece
     */
    public List<BoardMove> getPieceMoves(int y, int x, String[][] board) {
        List<BoardMove> pieceMoves = new ArrayList<>();
        String piece = board[y][x];
        int color = piece.matches("[A-Z]") ? 1 : -1; // 1 for white pieces, -1 for black pieces

        switch (piece) {
            case "p", "P":
                pieceMoves.addAll(getPawnMoves(x, y, color, board, piece));
                break;

            case "n", "N":
                pieceMoves.addAll(getKnightMoves(x, y, color, board, piece));
                break;

            case "b", "B","r", "R","q", "Q":
                pieceMoves.addAll(getSlideMoves(x, y, color, board, piece)); // Northeast
                break;
            case "k", "K":
                pieceMoves.addAll(getKingMoves(x, y, color, board, piece));
                break;
        }
        return pieceMoves;
    }

    /**
     * Retrieves the possible moves for a pawn on the chessboard.
     *
     * @param  x   the column index of the pawn on the chessboard
     * @param  y   the row index of the pawn on the chessboard
     * @param  color   the color of the pawn (1 for white, -1 for black)
     * @param  board   the chessboard represented as a 2D array of strings
     * @param  piece   the piece type of the pawn (always "p" or "P")
     * @return      a list of BoardMove objects representing the possible moves for the pawn
     */
    private List<BoardMove> getPawnMoves(int x, int y, int color, String[][] board, String piece) {
        List<BoardMove> moves = new ArrayList<>();
        int direction = (color > 0) ? -1 : 1; // Direction of pawn's movement

        // Check if pawn can move forward one square
        if (isValid(y + direction, x) && board[y + direction][x].equals(" ")) {
            moves.add(new BoardMove(piece, x, y, x, y + direction));
        }

        // Check if pawn can move forward two squares from starting position
        if ((y == 1 && color < 0) || (y == 6 && color > 0)) {
            if (isValid(y + 2 * direction, x) && board[y + (2 * direction)][x].equals(" ") && board[y + direction][x].equals(" ")) {
                moves.add(new BoardMove(piece, x, y, x, y + (2 * direction)));
            }
        }

        // Check if pawn can capture diagonally

        if (isValid(y + direction, x - 1) && !board[y + direction][x - 1].equals(" ")) {
            int pieceColor = board[y + direction][x - 1].matches("[A-Z]") ? 1 : -1;
            if (pieceColor != color) {
                moves.add(new BoardMove(piece, x, y, x - 1, y + direction));
            }

        }
        if (isValid(y + direction, x + 1) && !board[y + direction][x + 1].equals(" ")) {
            int pieceColor = board[y + direction][x + 1].matches("[A-Z]") ? 1 : -1;
            if (pieceColor != color) {
                moves.add(new BoardMove(piece, x, y, x + 1, y + direction));
            }
        }
        return moves;
    }

    /**
     * Returns a list of possible moves for a knight piece on the chessboard.
     *
     * @param  x         the x-coordinate of the current position of the knight piece
     * @param  y         the y-coordinate of the current position of the knight piece
     * @param  color     the color of the knight piece (1 for white, -1 for black)
     * @param  board     the chessboard represented as a 2D array of strings
     * @param  piece     the piece type of the knight piece
     * @return           a list of possible moves for the knight piece
     */
    private List<BoardMove> getKnightMoves(int x, int y, int color, String[][] board, String piece) {
        List<BoardMove> moves = new ArrayList<>();
        int[][] knightMoves = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        for (int[] move : knightMoves) {
            int newX = x + move[0];
            int newY = y + move[1];

            if (isValid(newY, newX)) {
                int pieceColor = 0;
                if (!board[newY][newX].equals(" ")) {
                    pieceColor = board[newY][newX].matches("[A-Z]") ? 1 : -1;
                }
                if (pieceColor != color) {
                    moves.add(new BoardMove(piece, x, y, newX, newY));
                }

            }
        }
        return moves;
    }

    /**
     * Retrieves the possible moves for a king piece on the chessboard.
     *
     * @param  x         the x-coordinate of the current position of the king piece
     * @param  y         the y-coordinate of the current position of the king piece
     * @param  color     the color of the king piece (1 for white, -1 for black)
     * @param  board     the chessboard represented as a 2D array of strings
     * @param  piece     the piece type of the king piece
     * @return           a list of possible moves for the king piece
     */
    private List<BoardMove> getKingMoves(int x, int y, int color, String[][] board, String piece) {
        List<BoardMove> moves = new ArrayList<>();
        int[][] kingMoves = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] move : kingMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            if (isValid(newY, newX)) {
                int pieceColor = 0;
                if (!board[newY][newX].equals(" ")) {
                    pieceColor = board[newY][newX].matches("[A-Z]") ? 1 : -1;
                }
                if (pieceColor != color) {
                    moves.add(new BoardMove(piece, x, y, newX, newY));
                }
            }
        }
        return moves;
    }

    /**
     * Retrieves the possible slide moves for a given piece on the chessboard.
     *
     * @param  x         the column index of the piece on the chessboard
     * @param  y         the row index of the piece on the chessboard
     * @param  color     the color of the piece (1 for white, -1 for black)
     * @param  board     the chessboard represented as a 2D array of strings
     * @param  piece     the piece type (e.g. "b" for bishop, "r" for rook, "q" for queen)
     * @return           a list of BoardMove objects representing the possible slide moves for the piece
     */
    private List<BoardMove> getSlideMoves(int x, int y, int color,String[][] board, String piece) {
        List<BoardMove> moves = new ArrayList<>();
        int[][] directions;
        if (piece.equalsIgnoreCase("b")) {
            directions = new int[][]{{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
        }else if (piece.equalsIgnoreCase("r")){
            directions = new int[][]{{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
        }else {
            directions = new int[][]{{0, -1}, {-1, 0}, {0, 1}, {1, 0},{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
        }

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int newX = x + dx;
            int newY = y + dy;

            while (isValid(newY, newX)) {
                if (board[newY][newX].equals(" ")) {
                    moves.add(new BoardMove(piece, x, y, newX, newY));
                } else{
                    int pieceColor = 0;
                    if (!board[newY][newX].equals(" ")) {
                        pieceColor = board[newY][newX].matches("[A-Z]") ? 1 : -1;
                    }
                    if (pieceColor != color) {
                        moves.add(new BoardMove(piece, x, y, newX, newY));
                    }
                    break;
                }
                newX += dx;
                newY += dy;
            }
        }
        return moves;
    }

    /**
     * Checks if the given coordinates are valid within the chessboard.
     *
     * @param  y  the row index of the chessboard
     * @param  x  the column index of the chessboard
     * @return    true if the coordinates are valid, false otherwise
     */
    private boolean isValid(int y, int x) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public ArrayList<BoardMove> getMoves() {
        return moves;
    }
}
