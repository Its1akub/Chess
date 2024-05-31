package Game.AI;

import Game.Move;
import Other.ChessPieces.*;
import java.util.ArrayList;

public class MechanicsForMyAI {

    /**
     * Converts a FEN string representation of a chess board into a 2D array representation.
     *
     * @param  fen   the FEN string representation of the chess board
     * @return       a 2D array representation of the chess board
     */
    public static String[][] fenToBoard(String fen) {
        String[] parts = fen.split(" ");
        String[] rows = parts[0].split("/");
        String[][] board = new String[8][8];

        for (int i = 0; i < 8; i++) {
            int j = 0;
            for (char c : rows[i].toCharArray()) {
                if (Character.isDigit(c)) {
                    for (int k = 0; k < Character.getNumericValue(c); k++) {
                        board[i][j++] = " ";
                    }
                } else {
                    board[i][j++] = String.valueOf(c);
                }
            }
        }
        return board;
    }

    /**
     * Converts a move made by the AI in the board representation to the actual board representation.
     *
     * @param  pieces  the current state of the board represented as a list of pieces
     * @param  move   the move made by the AI in the board representation
     * @return        the updated board representation after applying the AI move
     */
    public static ArrayList<Piece> convertMyAIMoveToBoard(ArrayList<Piece> pieces, BoardMove move) {
        ArrayList<Piece> piecesToRemove = new ArrayList<>();
        for (Piece piece : pieces) {
            if (piece.getcX() == move.getStartX() && piece.getcY() == move.getStartY()) {
                if (piece.getcX() == move.getEndX() && piece.getcY() == move.getEndY()) {
                    piecesToRemove.add(piece);
                } else {
                    piece.setcX(move.getEndX());
                    piece.setcY(move.getEndY());
                    break;
                }

            }
        }
        pieces.removeAll(piecesToRemove);
        return pieces;
    }

    /**
     * Generates a Move object based on the given BoardMove object.
     *
     * @param  move  the BoardMove object containing the start and end coordinates of the move
     * @return       a Move object representing the move with the AI's piece's starting coordinates,
     *               null as the captured piece, and the opponent's piece's ending coordinates
     */
    public static Move myAIMove(BoardMove move) {
        int pPX = move.getStartX();
        int pPY = move.getStartY();
        int cPX = move.getEndX();
        int cPY = move.getEndY();
        return new Move(pPX, pPY, null, cPX, cPY);
    }


}
