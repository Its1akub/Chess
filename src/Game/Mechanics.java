package Game;

import Other.ChessPieces.Pawn;
import Other.ChessPieces.Piece;
import Other.ChessPieces.Queen;
import Other.Users.ColorSide;
import Other.Users.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static Other.Users.ColorSide.BLACK;
import static Other.Users.ColorSide.WHITE;

public class Mechanics {
    public static ArrayList<Player> setColorsOfPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        Random rand = new Random();

        if (rand.nextInt(2) == 0) {
            players.add(new Player(WHITE));
            players.add(new Player(BLACK));
        } else {
            players.add(new Player(BLACK));
            players.add(new Player(WHITE));
        }

        return players;
    }

    public static String[][] transformBoardToArray(ArrayList<Piece> pieces) {
        String[][] board = new String[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                for (Piece p : pieces) {
                    if (x == p.getcX() && y == p.getcY()) {
                        String color;
                        if (p.isWhite()) {
                            color = "w";
                        } else {
                            color = "b";
                        }
                        String type = switch (p.getType()) {
                            case KING:
                                yield "k";
                            case QUEEN:
                                yield "q";
                            case BISHOP:
                                yield "b";
                            case KNIGHT:
                                yield "n";
                            case ROOK:
                                yield "r";
                            case PAWN:
                                yield "p";
                        };
                        board[y][x] = type + color;
                    }
                }
            }
        }
        return board;
    }

    private static String getColor(String[][] board, int x, int y, boolean isWhite) {
        char[] color = board[y][x].toCharArray();
        if (!isWhite && color[1] == 'w') {
            return "w";
        } else if (isWhite && color[1] == 'b') {
            return "b";
        } else {
            return "";
        }
    }

    public static boolean possMoveToKill(String[][] board, int x, int y, boolean isWhite) {
        if (x > 7 || x < 0 || y > 7 || y < 0) {
            return false;
        } else if (isWhite) {
            return getColor(board, x, y, true).equals("b");
        } else {
            return getColor(board, x, y, false).equals("w");
        }
    }

    public static ArrayList<Piece> removePiece(ArrayList<Piece> pieces, int x, int y) {
        for (Piece p : pieces) {
            if (p.getcX() == x && p.getcY() == y) {
                pieces.remove(p);
                break;
            }
        }
        return pieces;
    }

    public static ArrayList<Piece> promotion(ArrayList<Piece> pieces, int xMoveBy, int yMoveBy) {
        // only promoted to queen
        for (Piece p : pieces) {
            if (p instanceof Pawn && p.getcY() == 0) {
                int x = p.getcX();
                int y = p.getcY();
                pieces.remove(p);
                Queen queen = new Queen(x, y, p.isWhite());
                queen.setX(x * 50 * 2 + xMoveBy + 50);
                queen.setY(y * 50 * 2 + yMoveBy + 50);
                pieces.add(queen);
                break;
            }
        }
        return pieces;
    }

    // public static Piece Castling
}
