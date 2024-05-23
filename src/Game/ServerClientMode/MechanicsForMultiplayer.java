package Game.ServerClientMode;

import Game.Move;
import Other.ChessPieces.*;

import java.util.ArrayList;

public class MechanicsForMultiplayer {
    public static SendMove convertMoveToSendMove(Move move) {
        return new SendMove(move.getPreviousCX(), move.getPreviousCY(), move.getCurrentCX(), move.getCurrentCY());
    }
}
