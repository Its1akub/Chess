package Game.StockFishMode;

import Other.ChessPieces.*;

import java.util.ArrayList;
import java.util.Iterator;

public class MechanicsStockFish {
    /**
     * Converts a chess board representation to the Forsyth-Edwards Notation (FEN) string.
     *
     * @param  board            the chess board represented as a 2D array of strings
     * @param  colorMove        the color of the side to move ("w" for white, "b" for black)
     * @param  castlingRights   the castling rights of the current position (e.g. "KQkq")
     * @param  enPassantSquare  the en passant square (e.g. "e3")
     * @param  halfMoveClock    the halfmove clock (number of halfmoves since the last capture or pawn advance)
     * @param  fullMoveNumber   the fullmove number (number of the full move)
     * @return                  the FEN string representation of the chess board
     */
    public static String boardToFEN(String[][] board, String colorMove, String castlingRights, String enPassantSquare, int halfMoveClock, int fullMoveNumber) {
        StringBuilder fen = new StringBuilder();
        board = convertBoardtoBoardFEN(board);
        // Translate the board position to FEN string
        for (int row = 0; row < 8; row++) {
            int emptyCounter = 0;
            for (int col = 0; col < 8; col++) {
                String piece = board[row][col];
                if (piece.equals(" ")) {
                    emptyCounter++;
                } else {
                    if (emptyCounter > 0) {
                        fen.append(emptyCounter);
                        emptyCounter = 0;
                    }
                    fen.append(piece);
                }
            }
            if (emptyCounter > 0) {
                fen.append(emptyCounter);
            }
            if (row < 7) {
                fen.append('/');
            }
        }

        // Add side to move, castling rights, en passant square, halfmove clock, and fullmove number
        fen.append(" " + colorMove + " " + castlingRights + " " + enPassantSquare + " " + halfMoveClock + " " + fullMoveNumber); // default values for initial position

        return fen.toString();
    }

    /**
     * Converts a Stockfish move to a board representation and updates the pieces accordingly.
     *
     * @param  move             the Stockfish move to be converted
     * @param  pieces            the list of pieces on the board
     * @param  isWhite           indicates whether the current player is white
     * @return                   the updated list of pieces on the board
     */
    public static ArrayList<Piece> stockfishMoveToBoard(String move, ArrayList<Piece> pieces,boolean isWhite) {
        if (!isWhite) move = rotateMove(move);
        ArrayList<Piece> piecesToRemove = new ArrayList<>();
        String previousPosition = move.substring(0, 2);
        String currentPosition = move.substring(2, 4);
        String promotion = "";
        if (move.length() == 5) {
            promotion = move.substring(4, 5);
        }
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
        if(isWhite) pPY = 8 - pPY;
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
        if(isWhite)  cPY = 8 - cPY;

        Iterator<Piece> iterator = pieces.iterator();
        while (iterator.hasNext()) {
            Piece piece = iterator.next();
            if (piece.getcX() == pPX && piece.getcY() == pPY) {
                if (move.length() == 5) {
                    iterator.remove();
                    switch (promotion) {
                        case "q" -> pieces.add(new Queen(cPX, cPY, piece.isWhite()));
                        case "n" -> pieces.add(new Knight(cPX, cPY, piece.isWhite()));
                        case "b" -> pieces.add(new Bishop(cPX, cPY, piece.isWhite()));
                        case "r" -> pieces.add(new Rook(cPX, cPY, piece.isWhite()));
                    }
                    for (Piece p : pieces) {
                        if (p.getcX() == cPX && p.getcY() == cPY) {
                            piecesToRemove.add(p);
                        }
                    }

                } else {
                    for (Piece p : pieces) {
                        if (p.getcX() == cPX && p.getcY() == cPY) {
                            piecesToRemove.add(p);
                        }
                    }
                    piece.setcX(cPX);
                    piece.setcY(cPY);

                }
            }
        }
        pieces.removeAll(piecesToRemove);
        return pieces;
    }
    /**
     * Converts a chess board representation to the Forsyth-Edwards Notation (FEN) board.
     *
     * @param  board   the chess board represented as a 2D array of strings
     * @return         the FEN board representation of the chess board
     */
    public static String[][] convertBoardtoBoardFEN(String[][] board) {
        String[][] boardFEN = new String[8][8];
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == null) {
                    boardFEN[row][column] = " ";
                } else {
                    if (board[row][column].contains("w")) {
                        boardFEN[row][column] = board[row][column].substring(0, 1).toUpperCase();
                    } else {
                        boardFEN[row][column] = board[row][column].substring(0, 1).toLowerCase();
                    }
                }

            }
        }
        return boardFEN;
    }
    /**
     * Rotates a chessboard 180 degrees clockwise.
     *
     * @param  chessboard  the original chessboard represented as a 2D array of strings
     * @return             the rotated chessboard represented as a 2D array of strings
     */
    public static String[][] rotateChessboard180(String[][] chessboard) {
        int n = chessboard.length;
        String[][] rotatedChessboard = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotatedChessboard[i][j] = chessboard[n - 1 - i][n - 1 - j];
            }
        }
        return rotatedChessboard;
    }
    /**
     * Rotates a chess move by 180 degrees.
     *
     * @param  move  the chess move to be rotated
     * @return       the rotated chess move
     */
    private static String rotateMove(String move) {

        String previousPosition = move.substring(0, 2);
        String currentPosition = move.substring(2, 4);
        String promotion = "";
        if (move.length() == 5) {
            promotion = move.substring(4, 5);
        }
        String pPX = switch (previousPosition.substring(0, 1)) {
            case "a" -> "h";
            case "b" -> "g";
            case "c" -> "f";
            case "d" -> "e";
            case "e" -> "d";
            case "f" -> "c";
            case "g" -> "b";
            case "h" -> "a";
            default -> "";
        };
        int pPY = Integer.parseInt(previousPosition.substring(1, 2));
        pPY -=1;
        String cPX = switch (currentPosition.substring(0, 1)) {
            case "a" -> "h";
            case "b" -> "g";
            case "c" -> "f";
            case "d" -> "e";
            case "e" -> "d";
            case "f" -> "c";
            case "g" -> "b";
            case "h" -> "a";
            default -> "";
        };
        int cPY = Integer.parseInt(currentPosition.substring(1, 2));
        cPY -= 1;
        StringBuilder sb = new StringBuilder();
        return sb.append(pPX).append(pPY).append(cPX).append(cPY).append(promotion).toString();
    }
    /**
     * Checks if the half move clock is different between the current and previous piece lists.
     *
     * @param  pieces   the current list of pieces
     * @param  previous the previous list of pieces
     * @return          true if the half move clock is different, false otherwise
     */
    public static boolean checkHalfMoveClock(ArrayList<Piece> pieces,ArrayList<Piece> previous) {
        int pawnCountCurrent = 0;
        int pawnCountPrevious = 0;
        for (Piece p : pieces) {
            if (p instanceof Pawn){
                pawnCountCurrent++;
            }
        }
        for (Piece p : previous) {
            if (p instanceof Pawn){
                pawnCountPrevious++;
            }
        }
        return (pawnCountCurrent != pawnCountPrevious || pieces.size() != previous.size());
    }


}
