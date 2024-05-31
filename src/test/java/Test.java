
import Game.Mechanics;
import Game.ServerClientMode.SendMove;
import Game.StockFishMode.MechanicsStockFish;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.testng.AssertJUnit.assertTrue;

//import static org.junit.Assert.*;

/*public class Test {

    @org.junit.Test
    public void rotateCoordinates() {
        SendMove sendMove = new SendMove(1,6,1,5);
        sendMove.rotateCoordinates();
        assertEquals(6, sendMove.getCurrentCX());
        assertEquals(2, sendMove.getCurrentCY());

        assertEquals(6, sendMove.getPreviousCX());
        assertEquals(1, sendMove.getPreviousCY());
    }

    @org.junit.Test
    public void checkForPlayerMoves(){
        String[][] board = {{"rb", "nb", "bb", "qb", "kb", "bb", "nb", "rb"},
                            {"pb", "pb", "pb", "pb", "pb", "pb", "pb", "pb"},
                            {null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null},
                            {null, "pw", null, null, null, null, null, null},
                            {"pw", null, "pw", "pw", "pw", "pw", "pw", "pw"},
                            {"rw", "nw", "bw", "qw", "kw", "bw", "nw", "rw"}
        };

        String[][] previous = {{"rb", "nb", "bb", "qb", "kb", "bb", "nb", "rb"},
                               {"pb", "pb", "pb", "pb", "pb", "pb", "pb", "pb"},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {"pw", "pw", "pw", "pw", "pw", "pw", "pw", "pw"},
                                {"rw", "nw", "bw", "qw", "kw", "bw", "nw", "rw"}
        };
        boolean isWhite = true;
        assertTrue(Mechanics.checkForPlayerMoves(board, previous, isWhite));
    }
    @org.junit.Test
    public void boardToFEN(){
        String[][] board = {
                {"rb", "nb", "bb", "qb", "kb", "bb", "nb", "rb"},
                {"pb", "pb", "pb", "pb", "pb", "pb", "pb", "pb"},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {"pw", "pw", "pw", "pw", "pw", "pw", "pw", "pw"},
                {"rw", "nw", "bw", "qw", "kw", "bw", "nw", "rw"}
        };
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", MechanicsStockFish.boardToFEN(board,"w","KQkq","bw",0,1));
    }

    @org.junit.Test
    public void rotateChessboard180(){
        String[][] board ={
                {"rb", "nb", "bb", "qb", "kb", "bb", "nb", "rb"},
                {"pb", "pb", "pb", "pb", "pb", "pb", "pb", "pb"},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {"pw", "pw", "pw", "pw", "pw", "pw", "pw", "pw"},
                {"rw", "nw", "bw", "qw", "kw", "bw", "nw", "rw"}
        };
        String[][] boardRotated = {
                {"rw", "nw", "bw", "kw", "qw", "bw", "nw", "rw"},
                {"pw", "pw", "pw", "pw", "pw", "pw", "pw", "pw"},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {"pb", "pb", "pb", "pb", "pb", "pb", "pb", "pb"},
                {"rb", "nb", "bb", "kb", "qb", "bb", "nb", "rb"},
        };
        assertEquals(boardRotated, MechanicsStockFish.rotateChessboard180(board));
    }

    @org.junit.Test
    public void convertBoardtoBoardFEN(){
        String[][] board ={
                {"rb", "nb", "bb", "qb", "kb", "bb", "nb", "rb"},
                {"pb", "pb", "pb", "pb", "pb", "pb", "pb", "pb"},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {"pw", "pw", "pw", "pw", "pw", "pw", "pw", "pw"},
                {"rw", "nw", "bw", "qw", "kw", "bw", "nw", "rw"}
        };
        String[][] boardFEN ={
                {"r", "n", "b", "q", "k", "b", "n", "r"},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {"R", "N", "B", "Q", "K", "B", "N", "R"}
        };
        assertEquals(boardFEN, MechanicsStockFish.convertBoardtoBoardFEN(board));
    }

}*/