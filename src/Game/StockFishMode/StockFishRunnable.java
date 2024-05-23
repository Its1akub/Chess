package Game.StockFishMode;

import Game.*;
import Other.ChessPieces.King;
import Other.ChessPieces.Piece;
import Other.ChessPieces.Rook;

import java.util.ArrayList;

import static Game.MechanicsForBoard.castling;
import static Other.AudioMethod.audioForChess;


public class StockFishRunnable implements Runnable {
    private Chess chess;
    private Stockfish stockfish;
    private boolean x;
    private int width;
    private int height;
    private int xMoveBy;
    private int yMoveBy;

    public StockFishRunnable(Chess chess, boolean isWhite, int width, int height) {
        this.chess = chess;
        chess.getChessBoard().setMadeMove(true);
        this.x = isWhite;
        this.width = width;
        this.height = height;
        xMoveBy = (width - 50 * 16) / 2;
        yMoveBy = (height - 50 * 16) / 2;
    }

    @Override
    public void run() {
        stockfish = new Stockfish();

        Thread thread = new Thread(stockfish);
        thread.start();
        while (!stockfish.isWaitForReady()) {
            System.out.println("loading stockfish...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println("Loading complete");
        //enpassant is done on player side and eat his pieces
        int halfMoveClock = 1;
        int fullMoveClock = 1;
        ArrayList<Piece> currentPieces;
        ArrayList<Piece> previousPieces;
        int round = 1;

        boolean kingMoves = false;
        boolean lRookMoves = false;
        boolean rRookMoves = false;

        while (true) {
            previousPieces = chess.getChessBoard().getPieces();
            if (x) {
                chess.getChessBoard().setMadeMove(false);
                while (!chess.getChessBoard().isMadeMove()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                chess.getMovePanel().setLineNumber(round);
                chess.getMovePanel().newLine();
                Move playerMove = chess.getChessBoard().getLastMove();
                String m = MechanicsForBoard.convertMoveToString(playerMove);
                String mInNotation = MechanicsForMovePanel.convertNumbersToChessNotation(m, chess.getChessBoard().getPreviousPosition(), chess.getChessBoard().getCurrentPosition(), x);
                String[][] board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
                chess.getMovePanel().addMove(mInNotation);
                chess.getMovePanel().print();

                currentPieces = chess.getChessBoard().getPieces();
                if (!MechanicsStockFish.checkHalfMoveClock(currentPieces, previousPieces)) {
                    halfMoveClock++;
                } else {
                    halfMoveClock = 0;
                }

                String[][] pieces = Mechanics.transformBoardToArray(chess.getChessBoard().getPieces());
                String fen = MechanicsStockFish.boardToFEN(pieces, "b", "KQkq", "-", halfMoveClock, fullMoveClock);
                System.out.println(fen);
                int size = chess.getChessBoard().getPieces().size();
                String move = stockfish.bestMove(fen, 3);
                if (move.equals("No best move found.")) {
                    chess.getChessBoard().setMadeMove(true);
                    break;
                }
                int row =  x ? 8 : 1;
                boolean movingWithKing = move.charAt(0) == 'e' && Integer.parseInt(move.substring(1,2)) == row;
                boolean castlingMove = Integer.parseInt(move.substring(1,2)) == Integer.parseInt(move.substring(3,4)) ;
                board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
                Move stockfishMove = MechanicsForBoard.stockfishMove(move, x);
                ArrayList<Piece> rePieces = MechanicsStockFish.stockfishMoveToBoard(move, chess.getChessBoard().getPieces(), true);
                if (castlingMove && movingWithKing && !kingMoves && !lRookMoves && !rRookMoves) {
                    rePieces = castling(rePieces, xMoveBy, yMoveBy, stockfishMove, x, board, true);
                }
                chess.getChessBoard().setOpponentMove(stockfishMove);
                chess.getMovePanel().addMove(MechanicsForMovePanel.convertNumbersToChessNotation(move, board, x, true));

                chess.getChessBoard().setPieces(rePieces);
                board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());

                currentPieces = chess.getChessBoard().getPieces();
                if (!MechanicsStockFish.checkHalfMoveClock(currentPieces, previousPieces)) {
                    halfMoveClock++;
                } else {
                    halfMoveClock = 0;
                }
                int sizeAfter = chess.getChessBoard().getPieces().size();
                if (sizeAfter < size) {
                    audioForChess("src/main/resources/audio/capture.wav");
                } else {
                    audioForChess("src/main/resources/audio/move.wav");
                }
                for (String[] strings : board) {
                    for (String string : strings) {
                        System.out.print(string + " ");
                    }
                    System.out.println();
                }
                chess.revalidate();
                chess.repaint();

                if (movingWithKing) kingMoves = true;
                boolean movingWithLRook = move.charAt(0) == 'a' && Integer.parseInt(move.substring(1,2)) == row;
                boolean movingWithRRook = move.charAt(0) == 'h' && Integer.parseInt(move.substring(1,2)) == row;
                if (movingWithLRook)lRookMoves = true;
                if (movingWithRRook)rRookMoves = true;

            } else {

                chess.getChessBoard().setMadeMove(false);

                String[][] pieces = Mechanics.transformBoardToArray(chess.getChessBoard().getPieces());
                pieces = MechanicsStockFish.rotateChessboard180(pieces);
                String fen = MechanicsStockFish.boardToFEN(pieces, "w", "KQkq", "-", halfMoveClock, fullMoveClock);
                System.out.println(fen);
                int size = chess.getChessBoard().getPieces().size();
                String move = stockfish.bestMove(fen, 3);
                if (move.equals("No best move found.")) {
                    chess.getChessBoard().setMadeMove(true);
                    break;
                }

                int row =  x ? 8 : 1;
                boolean movingWithKing = move.charAt(0) == 'e' && Integer.parseInt(move.substring(1,2)) == row;
                boolean castlingMove = Integer.parseInt(move.substring(1,2)) == Integer.parseInt(move.substring(3,4)) ;
                chess.getMovePanel().setLineNumber(round);
                chess.getMovePanel().newLine();
                String[][] board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
                Move stockfishMove = MechanicsForBoard.stockfishMove(move, x);
                ArrayList<Piece> rePieces = MechanicsStockFish.stockfishMoveToBoard(move, chess.getChessBoard().getPieces(), false);
                if (castlingMove && movingWithKing && !kingMoves && !lRookMoves && !rRookMoves) {
                    rePieces = castling(rePieces, xMoveBy, yMoveBy, stockfishMove, x, board, true);
                }
                chess.getChessBoard().setOpponentMove(stockfishMove);
                chess.getMovePanel().addMove(MechanicsForMovePanel.convertNumbersToChessNotation(move, board, x, true));
                chess.getChessBoard().setPieces(rePieces);
                board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
                currentPieces = chess.getChessBoard().getPieces();
                if (!MechanicsStockFish.checkHalfMoveClock(currentPieces, previousPieces)) {
                    halfMoveClock++;
                } else {
                    halfMoveClock = 0;
                }

                int sizeAfter = chess.getChessBoard().getPieces().size();
                if (sizeAfter < size) {
                    audioForChess("src/main/resources/audio/capture.wav");
                } else {
                    audioForChess("src/main/resources/audio/move.wav");
                }
                while (!chess.getChessBoard().isMadeMove()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                Move playerMove = chess.getChessBoard().getLastMove();
                String m = MechanicsForBoard.convertMoveToString(playerMove);
                String mInNotation = MechanicsForMovePanel.convertNumbersToChessNotation(m, chess.getChessBoard().getPreviousPosition(), chess.getChessBoard().getCurrentPosition(), x);
                chess.getMovePanel().addMove(mInNotation);
                chess.getMovePanel().print();
                board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
                for (String[] strings : board) {
                    for (String string : strings) {
                        System.out.print(string + " ");
                    }
                    System.out.println();
                }
                currentPieces = chess.getChessBoard().getPieces();
                if (!MechanicsStockFish.checkHalfMoveClock(currentPieces, previousPieces)) {
                    halfMoveClock++;
                } else {
                    halfMoveClock = 0;
                }

                if (movingWithKing) kingMoves = true;
                boolean movingWithLRook = move.charAt(0) == 'h' && Integer.parseInt(move.substring(1,2)) == row;
                boolean movingWithRRook = move.charAt(0) == 'a' && Integer.parseInt(move.substring(1,2)) == row;
                if (movingWithLRook)lRookMoves = true;
                if (movingWithRRook)rRookMoves = true;
            }
            round++;
            fullMoveClock++;
        }
    }
}
