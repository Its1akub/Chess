package Game;

import Other.ChessPieces.*;
import Other.Coordinates;

import java.util.ArrayList;

public class MechanicsForBoard {
    /**
     * Converts a Stockfish move string to a Move object.
     *
     * @param  move    the Stockfish move string
     * @param  isWhite true if the move is for the white player, false otherwise
     * @return the corresponding Move object
     */
    public static Move stockfishMove(String move, boolean isWhite) {
        if (!isWhite) move = rotate(move);
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
        if (isWhite) {
            pPY = 8 - pPY;
            cPY = 8 - cPY;
        } else {
            pPY = pPY - 1;
            cPY = cPY - 1;
        }
        return new Move(pPX, pPY, null, cPX, cPY);
    }

    /**
     * Rotates a move string by 180 degrees.
     *
     * @param  move    the move string to be rotated
     * @return the rotated move string
     */
    public static String rotate(String move) {
        String previousPosition = move.substring(0, 2);
        String currentPosition = move.substring(2, 4);
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
        StringBuilder sb = new StringBuilder();
        return sb.append(pPX).append(pPY).append(cPX).append(cPY).toString();
    }

    /**
     * Converts a Move object to a string representation of the move.
     *
     * @param  move    the Move object to be converted
     * @return the string representation of the move in the format "pPXpPYcPXcPY",
     *                 where pPX and cPX are the previous and current x-coordinates of the move
     *                 respectively, and pPY and cPY are the previous and current y-coordinates of the move
     *                 respectively
     */
    public static String convertMoveToString(Move move) {
        StringBuilder sb = new StringBuilder();
        String pPX = switch (move.getPreviousCX()) {
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            case 7 -> "h";
            default -> "";
        };
        String cPX = switch (move.getCurrentCX()) {
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            case 7 -> "h";
            default -> "";
        };
        return sb.append(pPX).append(8 - move.getPreviousCY()).append(cPX).append(8 - move.getCurrentCY()).toString();
    }

    /**
     * Performs castling on the board based on the given parameters.
     *
     * @param  pieces       the list of pieces on the board
     * @param  xMoveBy      the x-coordinate movement
     * @param  yMoveBy      the y-coordinate movement
     * @param  move         the move object representing the castling move
     * @param  isWhite      a boolean indicating if the player is white
     * @param  board        the board representation as a 2D array of strings
     * @param  playerOnBottom       a boolean indicating if the player is the active player
     * @return the updated list of pieces on the board after castling
     */
    public static ArrayList<Piece> castling(ArrayList<Piece> pieces, int xMoveBy, int yMoveBy, Move move, boolean isWhite, String[][] board, boolean playerOnBottom, boolean isWhiteOnBottom) {
        ArrayList<Coordinates> coordinates = new ArrayList<>();

        for (Piece piece : pieces) {
            if (piece.isWhite() != isWhite) {
                if (piece instanceof Pawn) ((Pawn) piece).setPieces(pieces);
                coordinates.addAll(piece.allowedMovements(board, piece.isWhite()));
            }
        }
        Rook rookLeft = null;
        Rook rookRight = null;
        King king = null;
        boolean moveToRight = false;
        boolean moveToLeft = false;
        int indexOfRL = 0;
        int indexOfRR = 0;
        int indexOfK = 0;
        int y = playerOnBottom ? 7 : 0;
        int x;
        boolean color = isWhite == isWhiteOnBottom;
        //boolean color = ((!isWhite) == isWhiteOnBottom) || (isWhite == (!isWhiteOnBottom));
        //boolean color = (isWhite == isWhiteOnBottom)||(!isWhite == !isWhiteOnBottom);

        if (color) {
            if (move.getPreviousCX() < move.getCurrentCX() && board[y][5] == null && board[y][6] == null)
                moveToRight = true;
            else if (move.getPreviousCX() > move.getCurrentCX() && board[y][1] == null && board[y][2] == null && board[y][3] == null)
                moveToLeft = true;

        } else {
            if (move.getPreviousCX() > move.getCurrentCX() && board[y][1] == null && board[y][2] == null)
                moveToLeft = true;
            else if (move.getPreviousCX() < move.getCurrentCX() && board[y][4] == null && board[y][5] == null && board[y][6] == null)
                moveToRight = true;

        }
        x = move.getCurrentCX();

        int i = 0;
        for (Piece piece : pieces) {
            if (piece instanceof Rook && piece.isWhite() == isWhite) {
                if (piece.getcX() == 0 && piece.getcY() == y && moveToLeft) {
                    rookLeft = (Rook) piece;
                    indexOfRL = i;
                }
                if (piece.getcX() == 7 && piece.getcY() == y && moveToRight) {
                    rookRight = (Rook) piece;
                    indexOfRR = i;
                }
            }
            if (piece instanceof King && piece.isWhite() == isWhite) {
                king = (King) piece;
                indexOfK = i;
            }
            i++;
        }
        boolean isCastlingLeft = false;
        boolean isCastlingRight = false;

        //left
        if (moveToLeft) {
            for (Coordinates coordinate : coordinates) {
                if (isWhite) {
                    if (rookLeft.getcY() == coordinate.getY() && rookLeft.getcX() == coordinate.getX() || coordinate.getY() == y && (coordinate.getX() == 1 || coordinate.getX() == 2 || coordinate.getX() == 3)) {
                        break;
                    } else isCastlingLeft = true;
                } else {
                    if (rookLeft.getcY() == coordinate.getY() && rookLeft.getcX() == coordinate.getX() || coordinate.getY() == y && (coordinate.getX() == 1 || coordinate.getX() == 2)) {
                        break;
                    } else isCastlingLeft = true;
                }

            }
        }
        //right
        if (moveToRight) {
            for (Coordinates coordinate : coordinates) {
                if (isWhite) {
                    if (rookRight.getcY() == coordinate.getY() && rookRight.getcX() == coordinate.getX() || coordinate.getY() == y && (coordinate.getX() == 5 || coordinate.getX() == 6)) {
                        break;
                    } else isCastlingRight = true;

                }else {
                    if (rookRight.getcY() == coordinate.getY() && rookRight.getcX() == coordinate.getX() || coordinate.getY() == y && (coordinate.getX() == 4 || coordinate.getX() == 5 || coordinate.getX() == 6)) {
                        break;
                    } else isCastlingRight = true;
                }
            }
        }

        if (moveToLeft && isCastlingLeft) {
            rookLeft.setcX(x);
            rookLeft.setX((x) * 50 * 2 + xMoveBy + 50);
           rookLeft.setY(y * 50 * 2 + yMoveBy + 50);
            pieces.remove(indexOfRL);
            pieces.add(indexOfRL, rookLeft);
            king.setcX(x - 1);
            king.setX((x - 1) * 50 * 2 + xMoveBy + 50);
        } else if (moveToRight && isCastlingRight) {
            rookRight.setcX(x);
            rookRight.setX(x * 50 * 2 + xMoveBy + 50);
            rookRight.setY(y * 50 * 2 + yMoveBy + 50);
            pieces.remove(indexOfRR);
            pieces.add(indexOfRR, rookRight);
            king.setcX(x + 1);
            king.setX((x + 1) * 50 * 2 + xMoveBy + 50);
        }

        king.setY(y * 50 * 2 + yMoveBy + 50);
        pieces.remove(indexOfK);
        pieces.add(indexOfK, king);

        return pieces;
    }
}
