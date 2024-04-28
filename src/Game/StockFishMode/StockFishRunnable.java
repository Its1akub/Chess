package Game.StockFishMode;

import Game.Chess;
import Game.Mechanics;
import Other.ChessPieces.Piece;

import java.util.ArrayList;

import static Other.AudioMethod.audioForChess;


public class StockFishRunnable implements Runnable {
    private Chess chess;
    private Stockfish stockfish;
    private boolean x = true;

    public StockFishRunnable(Chess chess, boolean isWhite) {
        this.chess = chess;
        chess.getChessBoard().setMadeMove(true);
        this.x = isWhite;
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
                String[][] board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
                for (String[] strings : board) {
                    for (String string : strings) {
                        System.out.print(string + " ");
                    }
                    System.out.println();
                }
                currentPieces = chess.getChessBoard().getPieces();
                if(!MechanicsStockFish.checkHalfMoveClock(currentPieces,previousPieces)){
                    halfMoveClock++;
                }else {
                    halfMoveClock = 0;
                }

                String[][] pieces = Mechanics.transformBoardToArray(chess.getChessBoard().getPieces());
                String fen = MechanicsStockFish.boardToFEN(pieces, "b", "KQkq", "-", halfMoveClock, fullMoveClock);
                System.out.println(fen);
                int size = chess.getChessBoard().getPieces().size();
                String move = stockfish.bestMove(fen, 3);
                if (move.equals("No best move found.")) {
                    chess.getChessBoard().setMadeMove(false);
                    break;
                }
                chess.getChessBoard().setPieces(MechanicsStockFish.stockfishMoveToBoard(move, chess.getChessBoard().getPieces(),true));

                currentPieces = chess.getChessBoard().getPieces();
                if(!MechanicsStockFish.checkHalfMoveClock(currentPieces,previousPieces)){
                    halfMoveClock++;
                }else {
                    halfMoveClock = 0;
                }
                int sizeAfter = chess.getChessBoard().getPieces().size();
                if (sizeAfter < size){
                    audioForChess("src/main/resources/audio/capture.wav");
                }else {
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

            } else {

                chess.getChessBoard().setMadeMove(false);

                String[][] pieces = Mechanics.transformBoardToArray(chess.getChessBoard().getPieces());
                pieces = MechanicsStockFish.rotateChessboard180(pieces);
                String fen = MechanicsStockFish.boardToFEN(pieces, "w", "KQkq", "-", halfMoveClock, fullMoveClock);
                System.out.println(fen);
                int size = chess.getChessBoard().getPieces().size();
                String move = stockfish.bestMove(fen, 3);
                if (move.equals("No best move found.")) {
                    chess.getChessBoard().setMadeMove(false);
                    break;
                }
                chess.getChessBoard().setPieces(MechanicsStockFish.stockfishMoveToBoard(move, chess.getChessBoard().getPieces(),false));

                currentPieces = chess.getChessBoard().getPieces();
                if(!MechanicsStockFish.checkHalfMoveClock(currentPieces,previousPieces)){
                    halfMoveClock++;
                }else {
                    halfMoveClock = 0;
                }

                int sizeAfter = chess.getChessBoard().getPieces().size();
                if (sizeAfter < size){
                    audioForChess("src/main/resources/audio/capture.wav");
                }else {
                    audioForChess("src/main/resources/audio/move.wav");
                } String[][] board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
                for (String[] strings : board) {
                    for (String string : strings) {
                        System.out.print(string + " ");
                    }
                    System.out.println();
                }
                while (!chess.getChessBoard().isMadeMove()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
                for (String[] strings : board) {
                    for (String string : strings) {
                        System.out.print(string + " ");
                    }
                    System.out.println();
                }
                currentPieces = chess.getChessBoard().getPieces();
                if(!MechanicsStockFish.checkHalfMoveClock(currentPieces,previousPieces)){
                    halfMoveClock++;
                }else {
                    halfMoveClock = 0;
                }
            }

            fullMoveClock++;
        }
    }
}
