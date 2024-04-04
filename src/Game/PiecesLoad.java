package Game;

import Other.ChessPieces.*;

import java.util.ArrayList;

public class PiecesLoad {
    ArrayList<Piece> pieces = new ArrayList<>();
    public PiecesLoad(boolean white){
        if (white){
            pieces.add(new King(4,7,true));
            pieces.add(new Queen(3,7,true));
            pieces.add(new Rook(0,7,true));
            pieces.add(new Rook(7,7,true));
            pieces.add(new Bishop(2,7,true));
            pieces.add(new Bishop(5,7,true));
            pieces.add(new Knight(1,7,true));
            pieces.add(new Knight(6,7,true));
            pieces.add(new Pawn(0,6,true));
            pieces.add(new Pawn(1,6,true));
            pieces.add(new Pawn(2,6,true));
            pieces.add(new Pawn(3,6,true));
            pieces.add(new Pawn(4,6,true));
            pieces.add(new Pawn(5,6,true));
            pieces.add(new Pawn(6,6,true));
            pieces.add(new Pawn(7,6,true));
            //black
            pieces.add(new King(4,0,false));
            pieces.add(new Queen(3,0,false));
            pieces.add(new Rook(0,0,false));
            pieces.add(new Rook(7,0,false));
            pieces.add(new Bishop(2,0,false));
            pieces.add(new Bishop(5,0,false));
            pieces.add(new Knight(1,0,false));
            pieces.add(new Knight(6,0,false));
            pieces.add(new Pawn(0,1,false));
            pieces.add(new Pawn(1,1,false));
            pieces.add(new Pawn(2,1,false));
            pieces.add(new Pawn(3,1,false));
            pieces.add(new Pawn(4,1,false));
            pieces.add(new Pawn(5,1,false));
            pieces.add(new Pawn(6,1,false));
            pieces.add(new Pawn(7,1,false));
        }else {
            pieces.add(new King(3,0,true));
            pieces.add(new Queen(4,0,true));
            pieces.add(new Rook(0,0,true));
            pieces.add(new Rook(7,0,true));
            pieces.add(new Bishop(2,0,true));
            pieces.add(new Bishop(5,0,true));
            pieces.add(new Knight(1,0,true));
            pieces.add(new Knight(6,0,true));
            pieces.add(new Pawn(0,1,true));
            pieces.add(new Pawn(1,1,true));
            pieces.add(new Pawn(2,1,true));
            pieces.add(new Pawn(3,1,true));
            pieces.add(new Pawn(4,1,true));
            pieces.add(new Pawn(5,1,true));
            pieces.add(new Pawn(6,1,true));
            pieces.add(new Pawn(7,1,true));
            //black
            pieces.add(new King(3,7,false));
            pieces.add(new Queen(4,7,false));
            pieces.add(new Rook(0,7,false));
            pieces.add(new Rook(7,7,false));
            pieces.add(new Bishop(2,7,false));
            pieces.add(new Bishop(5,7,false));
            pieces.add(new Knight(1,7,false));
            pieces.add(new Knight(6,7,false));
            pieces.add(new Pawn(0,6,false));
            pieces.add(new Pawn(1,6,false));
            pieces.add(new Pawn(2,6,false));
            pieces.add(new Pawn(3,6,false));
            pieces.add(new Pawn(4,6,false));
            pieces.add(new Pawn(5,6,false));
            pieces.add(new Pawn(6,6,false));
            pieces.add(new Pawn(7,6,false));
        }

    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
}
