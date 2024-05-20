package Game;

import static Game.MechanicsForBoard.rotate;

public class MechanicsForMovePanel {
    /**
     * Converts a move in the form of numbers to chess algebraic notation.
     *
     * @param move     the move in the form of numbers
     * @param board    the chess board
     * @param isWhite  indicates if the player is white
     * @param isAI     indicates if the player is an AI
     * @return         the move in chess notation
     */
    public static String convertNumbersToChessNotation(String move, String[][] board, boolean isWhite, boolean isAI) {
        if (!isAI && !isWhite) move = rotate(move);
        String previousPosition = move.substring(0, 2);
        String currentPosition = move.substring(2, 4);
        int pPX = switch (previousPosition.substring(0, 1)) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            case "e" -> 4;
            case "f" -> 5;
            case "g" -> 6;
            case "h" -> 7;
            default -> -1;
        };
        int pPY = Integer.parseInt(previousPosition.substring(1, 2));
        int cPX = switch (currentPosition.substring(0, 1)) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            case "e" -> 4;
            case "f" -> 5;
            case "g" -> 6;
            case "h" -> 7;
            default -> -1;
        };
        int cPY = Integer.parseInt(currentPosition.substring(1, 2));
        String previous = board[8-pPY][pPX];
        String current = board[8-cPY][cPX];
        String capture;
        if (!current.equals(" ")) capture = "x";
        else capture = "";
        String piece = switch (previous){
            case "R","r" -> "R";
            case "N","n" -> "N";
            case "B","b" -> "B";
            case "Q","q" -> "Q";
            case "K","k" -> "K";
            default -> "";
        };
        if (!isAI && !isWhite) cPY = 8 - (cPY -1);
        StringBuilder sb = new StringBuilder();
        return sb.append(piece).append(capture).append(currentPosition.charAt(0)).append(cPY).toString();
    }

    /**
     * Converts a move in the form of numbers to chess algebraic notation.
     *
     * @param  move       the move in the form of numbers
     * @param  previuos   the previous position
     * @param  current    the current position
     * @param  isWhite    indicates if the player is white
     * @return            the move in chess notation
     */
    public static String convertNumbersToChessNotation(String move, String previuos, String current, boolean isWhite) {
        if (!isWhite) move = rotate(move);
        String currentPosition = move.substring(2, 4);
        int cPY = Integer.parseInt(currentPosition.substring(1, 2));
        String capture;
        if (!current.equals(" ")) capture = "x";
        else capture = "";
        String piece = switch (previuos){
            case "R","r" -> "R";
            case "N","n" -> "N";
            case "B","b" -> "B";
            case "Q","q" -> "Q";
            case "K","k" -> "K";
            default -> "";
        };
        if (!isWhite) cPY = 8 - (cPY -1);
        StringBuilder sb = new StringBuilder();
        return sb.append(piece).append(capture).append(currentPosition.charAt(0)).append(cPY).toString();
    }
}
