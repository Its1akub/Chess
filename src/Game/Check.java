package Game;

import Other.ChessPieces.Piece;
import Other.Coordinates;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.ArrayList;
import java.util.List;

public class Check {

    /**
     * Returns a list of valid moves for a given piece on a chess board.
     *
     * @param  legalMoves  a list of legal moves for the piece
     * @param  piece       the piece for which to calculate valid moves
     * @param  board       the chess board
     * @param  isWhite     indicates whether the piece is white or black
     * @return             a list of valid moves for the piece
     */
    public static ArrayList<Coordinates> getValidMoves(List<Move> legalMoves, Piece piece, String [][] board, boolean isWhite) {
        ArrayList<Coordinates> validMoves = new ArrayList<>();
        for (Move move : legalMoves) {
            int pY = coordinatesFrom(move, isWhite)[1];
            int pX = coordinatesFrom(move, isWhite)[0];
            if (piece.getcX() == pX && piece.getcY() == pY) {
                ArrayList<Coordinates> coordinates = piece.allowedMovements(board, piece.isWhite());
                for (Coordinates c : coordinates){
                    int cY = coordinatesTo(move, isWhite)[1];
                    int cX = coordinatesTo(move, isWhite)[0];
                    if (c.getX() == cX && c.getY() == cY) {
                        validMoves.add(c);
                    }
                }
            }
        }
        return validMoves;
    }

    /**
     * Converts the coordinates of a move from a string representation to an integer array.
     *
     * @param  move        the move containing the coordinates to be converted
     * @param  isWhite     indicates whether the piece is white or black
     * @return             an integer array containing the converted coordinates
     */
    private static int[] coordinatesFrom(Move move, boolean isWhite){
        String from =move.getFrom().toString();
        int[] coordinates = new int[2];
        coordinates[0] = toInt(from.charAt(0), isWhite);
        if (isWhite){
            coordinates[1] = 7 - from.charAt(1) + '1';
        }else{
            coordinates[1] = from.charAt(1) - '1';
        }
        return coordinates;
    }

    /**
     * Converts the coordinates of a move from a string representation to an integer array.
     *
     * @param  move        the move containing the coordinates to be converted
     * @param  isWhite     indicates whether the piece is white or black
     * @return             an integer array containing the converted coordinates
     */
    private static int[] coordinatesTo(Move move, boolean isWhite){
        String to = move.getTo().toString();
        int[] coordinates = new int[2];
        coordinates[0] = toInt(to.charAt(0), isWhite);
        if (isWhite){
            coordinates[1] = 7 - to.charAt(1) + '1';
        }else{
            coordinates[1] = to.charAt(1) - '1';
        }
        return coordinates;
    }

    /**
     * Converts a character representing a column to its corresponding integer value.
     *
     * @param  column  the character representing the column (a-h)
     * @param  isWhite indicates whether the player is white or black
     * @return         the integer value of the column (0-7 for white, 7-0 for black)
     */
    private static int toInt(char column, boolean isWhite) {
        if (isWhite) {
            return switch (column) {
                case 'a','A' -> 0;
                case 'b','B' -> 1;
                case 'c','C' -> 2;
                case 'd','D' -> 3;
                case 'e','E' -> 4;
                case 'f','F' -> 5;
                case 'g','G' -> 6;
                case 'h','H' -> 7;
                default -> -1;
            };
        }else {
            return switch (column) {
                case 'a','A' -> 7;
                case 'b','B' -> 6;
                case 'c','C' -> 5;
                case 'd','D' -> 4;
                case 'e','E' -> 3;
                case 'f','F' -> 2;
                case 'g','G' -> 1;
                case 'h','H' -> 0;
                default -> -1;
            };
        }
    }
}