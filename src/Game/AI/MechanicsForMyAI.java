package Game.AI;

public class MechanicsForMyAI {

    public static String[][] fenToBoard(String fen) {
        String[] parts = fen.split(" ");
        String[] rows = parts[0].split("/");
        String[][] board = new String[8][8];

        for (int i = 0; i < 8; i++) {
            int j = 0;
            for (char c : rows[i].toCharArray()) {
                if (Character.isDigit(c)) {
                    for (int k = 0; k < Character.getNumericValue(c); k++) {
                        board[i][j++] = " ";
                    }
                } else {
                    board[i][j++] = String.valueOf(c);
                }
            }
        }
        return board;
    }
}
