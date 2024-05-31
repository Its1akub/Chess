package Game.ServerClientMode;

import Game.Move;

public class MechanicsForMultiplayer {

    /**
     * Converts a Move object to a SendMove object.
     *
     * @param  move  the Move object to convert
     * @return       a new SendMove object with the same coordinates as the input Move object
     */
    public static SendMove convertMoveToSendMove(Move move) {
        return new SendMove(move.getPreviousCX(), move.getPreviousCY(), move.getCurrentCX(), move.getCurrentCY());
    }

    /**
     * Converts a SendMove object to a Move object.
     *
     * @param  move  the SendMove object to convert
     * @return       a new Move object with the same coordinates as the input SendMove object
     */
    public static Move convertSendMoveToMove(SendMove move) {
        return new Move(move.getPreviousCX(), move.getPreviousCY(), null,move.getCurrentCX(), move.getCurrentCY());
    }
}
