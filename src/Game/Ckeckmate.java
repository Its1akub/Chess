package Game;

import Game.StockFishMode.MechanicsStockFish;
import com.github.bhlangonijr.chesslib.Board;
import java.util.Arrays;

public class Ckeckmate {

    /**
     * Checks if the given player is in checkmate on the given chess board.
     *
     * @param  isWhite  true if the player to check is white, false if it's black
     * @param  board    the chess board represented as a 2D array of strings
     * @return          true if the player is in checkmate, false otherwise
     */
    public static boolean checkmate(boolean isWhite,String[][] board) {
        Board validMoves = new Board();
        String colorMove = isWhite ? "w" : "b";
        String[][] boardForMoves = new String[8][8];
        for (int i = 0; i < board.length; i++) {
            boardForMoves[i] = Arrays.copyOf(board[i], board[i].length);
        }
        String fen = MechanicsStockFish.boardToFEN(boardForMoves, colorMove, "KQkq", "-", 1, 1);
        validMoves.loadFromFen(fen);
        return validMoves.legalMoves().isEmpty();
    }
}
