package oogasalad.Frontend.Game.Sections;

import oogasalad.GamePlayer.GamePiece.Piece;

import java.util.Collection;
import java.util.Optional;

/**
 * BoardTile class will contain all the information about each tile that must be displayed, and will hold the node.
 */

public class BoardTile {
    private static Integer myX;
    private static Integer myY;
    private static Optional<Piece> myPiece;

    public BoardTile(int x, int y, Optional<Piece> thepiece) {
        myX = x;
        myY = y;
        myPiece = thepiece;
    }
}
