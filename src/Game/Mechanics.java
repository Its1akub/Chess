package Game;

import Game.StockFishMode.MechanicsStockFish;
import Other.ChessPieces.*;
import Other.Users.Player;
import java.util.ArrayList;
import java.util.Random;

import static Other.Users.ColorSide.BLACK;
import static Other.Users.ColorSide.WHITE;

public class Mechanics {
    /**
     * Generates a list of players and sets their colors randomly.
     * @return the list of players with assigned colors
     */
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

    /**
     * Generates a 2D array representation of a chessboard based on the provided list of chess pieces.
     * @param  pieces   the list of chess pieces to populate the board with
     * @return a 2D array representing the chessboard with pieces placed accordingly
     */
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

    /**
     * Checks the color of the piece at the specified coordinates.
     * @param board    the board
     * @param x        the x coordinate
     * @param y        the y coordinate
     * @param isWhite  true if the piece we looking for is white
     * @return the color of the piece
     */
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

    /**
     * Checks if a piece can move to the specified coordinates and kill an opponent.
     * @param board    the board
     * @param x        the x coordinate
     * @param y        the y coordinate
     * @param isWhite  true if the piece we looking for is white
     * @return true if the piece can move to the specified coordinates and kill an opponent
     */
    public static boolean possMoveToKill(String[][] board, int x, int y, boolean isWhite) {
        if (x > 7 || x < 0 || y > 7 || y < 0) {
            return false;
        } else if (isWhite) {
            return getColor(board, x, y, true).equals("b");
        } else {
            return getColor(board, x, y, false).equals("w");
        }
    }

    /**
     * Removes a piece from the given list at the specified coordinates.
     * @param  pieces  the list of pieces
     * @param  x       the x-coordinate
     * @param  y       the y-coordinate
     * @return the modified list of pieces
     */
    public static ArrayList<Piece> removePiece(ArrayList<Piece> pieces, int x, int y) {
        for (Piece p : pieces) {
            if (p.getcX() == x && p.getcY() == y) {
                pieces.remove(p);
                break;
            }
        }
        return pieces;
    }

    /**
     * Promotes a pawn to a queen.
     * @param  pieces     ArrayList of pieces
     * @param  xMoveBy    integer for x coordinate movement
     * @param  yMoveBy    integer for y coordinate movement
     * @return ArrayList of pieces after promotion
     */
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

    /**
     * Adds a new piece to the given list of pieces after a move.
     * @param  pieces  the list of pieces
     * @param  piece   the piece to add
     * @param  x       the x-coordinate of the new position
     * @param  y       the y-coordinate of the new position
     */
    public static void addPieceAfterMove(ArrayList<Piece> pieces, Piece piece, int x, int y) {
        if (piece instanceof Pawn) {
            pieces.add(new Pawn(x, y, piece.isWhite()));
        } else if (piece instanceof Knight) {
            pieces.add(new Knight(x, y, piece.isWhite()));
        } else if (piece instanceof Bishop) {
            pieces.add(new Bishop(x, y, piece.isWhite()));
        } else if (piece instanceof Rook) {
            pieces.add(new Rook(x, y, piece.isWhite()));
        } else if (piece instanceof Queen) {
            pieces.add(new Queen(x, y, piece.isWhite()));
        } else if (piece instanceof King) {
            pieces.add(new King(x, y, piece.isWhite()));
        }
    }

    /**
     * Checks if the player has made any moves on the board.
     *
     * @param  board   the current state of the board
     * @param  previous the previous state of the board
     * @param  isWhite  whether the player is white or not
     * @return          true if the player has made any moves, false otherwise
     */
    public static boolean checkForPlayerMoves(String[][] board, String[][] previous, boolean isWhite) {
        ArrayList<PositionOnBoard> positions = new ArrayList<>();
        ArrayList<PositionOnBoard> previousPositions = new ArrayList<>();
        board = MechanicsStockFish.convertBoardtoBoardFEN(board);
        previous = MechanicsStockFish.convertBoardtoBoardFEN(previous);
        boolean isSame = true;
        // check for player moves
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (board[row][column].equals(" ")) continue;

                if (isWhite && board[row][column].equals(board[row][column].toUpperCase())) {
                    positions.add(new PositionOnBoard(row, column, board[row][column]));
                } else if (!isWhite && board[row][column].equals(board[row][column].toLowerCase())) {
                    positions.add(new PositionOnBoard(row, column, board[row][column]));
                }
            }
        }
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (previous[row][column].equals(" ")) continue;

                if (isWhite && previous[row][column].equals(previous[row][column].toUpperCase())) {
                    previousPositions.add(new PositionOnBoard(row, column, previous[row][column]));
                } else if (!isWhite && previous[row][column].equals(previous[row][column].toLowerCase())) {
                    previousPositions.add(new PositionOnBoard(row, column, previous[row][column]));
                }
            }
        }
        if (previousPositions.size() == positions.size()) {
            for (int i = 0; i < positions.size(); i++) {
                isSame = positions.get(i).getX() == previousPositions.get(i).getX() && positions.get(i).getY() == previousPositions.get(i).getY();
                if (!isSame) break;
            }
        } else {
            isSame = false;
        }
        return isSame;
    }
}
