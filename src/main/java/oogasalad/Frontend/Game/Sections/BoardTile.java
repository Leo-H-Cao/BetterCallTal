package oogasalad.Frontend.Game.Sections;

import javafx.scene.shape.Rectangle;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.Editor.Movement.Coordinate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * BoardTile class will contain all the information about each tile that must be displayed, and will hold the node.
 */

public class BoardTile {
    private static Rectangle myRectangle;
    private static ArrayList<String> myImages;
    private static ArrayList<Piece> myPieces;
    private static Double HEIGHT_BOARD = 600.0;
    private static Double WIDTH_Board = 600.0;
    private static Coordinate myCoord;

    public BoardTile(int x, int y, int rows, int cols) {
        myCoord = new Coordinate(y, x);

        myRectangle = new Rectangle(WIDTH_Board / rows, HEIGHT_BOARD / cols);
        myPieces = new ArrayList<>();
        myImages = new ArrayList<>();
    }

    /**
     * updateTile will be called by BoardGrid whenever a tile needs to be updated.
     * @param ct Chess tile to be updated.
     */

    public void updateTile(ChessTile ct) {
        if (! myPieces.isEmpty()) {
            myPieces.clear();
            myImages.clear();
        }
        myPieces.addAll(ct.getPieces());
        for (Piece p : myPieces) {
            myImages.add(p.getImgFile());
        }
    }

    public void givePiece(Piece p) {
        myPieces.add(p);
        myImages.add(p.getImgFile());
    }

    public Rectangle getMyRectangle() {return myRectangle;}

    public Coordinate getCoordinate() {return myCoord;}
}
