package Game.AI;

import Game.*;
import Game.StockFishMode.MechanicsStockFish;
import Other.AudioMethod;
import Other.ChessPieces.Piece;
import Other.Users.ColorSide;

import java.util.ArrayList;

import static Game.Ckeckmate.checkmate;
import static Game.StockFishMode.MechanicsStockFish.rotateChessboard180;

public class MyAIRunnable implements Runnable{
    private Chess chess;
    private int width;
    private int height;
    private int xMoveBy;
    private int yMoveBy;
    private AudioMethod audioMethod;
    public MyAIRunnable(Chess chess, int width, int height) {
        this.chess = chess;
        chess.getChessBoard().setMadeMove(true);
        this.width = width;
        this.height = height;
        xMoveBy = (width - 50 * 16) / 2;
        yMoveBy = (height - 50 * 16) / 2;
        audioMethod = new AudioMethod();
    }

    @Override
    public void run() {
        ColorSide color = ColorSide.WHITE;
        MyAI myAI = new MyAI(color);

        AudioMethod audioMethod = new AudioMethod();

        int halfMoveClock = 1;
        int fullMoveClock = 1;
        int round = 1;

        while (true) {
            chess.getChessBoard().setMadeMove(true);
            String[][] pieces = Mechanics.transformBoardToArray(chess.getChessBoard().getPieces());
            String fen = MechanicsStockFish.boardToFEN(pieces, "b", "KQkq", "-", halfMoveClock, fullMoveClock);
            System.out.println(fen);
            int size = chess.getChessBoard().getPieces().size();
            BoardMove aiBestMove = myAI.findBestMove(fen, 10000, 3);
            Move myAIMove = MechanicsForMyAI.myAIMove(aiBestMove);
            ArrayList<Piece> rePieces = MechanicsForMyAI.convertMyAIMoveToBoard(chess.getChessBoard().getPieces(), aiBestMove);
            chess.getChessBoard().setPieces(rePieces);
            chess.getChessBoard().setOpponentMove(myAIMove);
            String[][] rotatedBoard = rotateChessboard180(Mechanics.transformBoardToArray(chess.getChessBoard().getPieces()));
            if (checkmate(true, rotatedBoard)) break;
            chess.getMovePanel().addMove(MechanicsForMovePanel.convertNumbersToChessNotation(aiBestMove, rotatedBoard));
            chess.getChessBoard().setPieces(rePieces);
            int sizeAfter = chess.getChessBoard().getPieces().size();
            if (sizeAfter < size) {
                audioMethod.audioForChess("audio/capture.wav");
            } else {
                audioMethod.audioForChess("audio/move.wav");
            }
            chess.getChessBoard().setMadeMove(false);
            chess.repaint();

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
            String mInNotation = MechanicsForMovePanel.convertNumbersToChessNotation(m, chess.getChessBoard().getPreviousPosition(), chess.getChessBoard().getCurrentPosition(), false);
            String[][] board = MechanicsStockFish.convertBoardtoBoardFEN(chess.getChessBoard().getBoard());
            rotatedBoard = rotateChessboard180(Mechanics.transformBoardToArray(chess.getChessBoard().getPieces()));
            if (checkmate(false, rotatedBoard)) break;
            chess.getMovePanel().addMove(mInNotation);
            chess.getMovePanel().print();
        }
    }
}
