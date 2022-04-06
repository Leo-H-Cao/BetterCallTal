package oogasalad.Frontend.Game.Sections;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import java.util.ArrayList;


/**
 * BoardTile class will contain all the information about each tile that must be displayed, and will hold the node.
 */

public class BoardTile {
    private StackPane myStackPane;
    private static Rectangle myRectangle;
    private static ArrayList<String> myImages;
    private static ArrayList<Piece> myPieces;
    private static Double HEIGHT_BOARD = 600.0;
    private static Double WIDTH_Board = 600.0;
    private static Coordinate myCoord;
    private Border myLitUpBorder;
    private Border myClearBorder;
    private Double BORDER_WIDTH = 5.0;

    public BoardTile(int x, int y, int rows, int cols) {
        myCoord = new Coordinate(y, x);
        myStackPane = new StackPane();
        myRectangle = new Rectangle(WIDTH_Board / rows, HEIGHT_BOARD / cols);
        ColorRect(x, y);
        myStackPane.getChildren().add(myRectangle);
        myPieces = new ArrayList<>();
        myImages = new ArrayList<>();
        myLitUpBorder = makeLitUpBorder();
        myClearBorder = makeClearBorder();
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

    /**
     * Used in BoardGrid to give Board tile the correct piece.
     * @param p Piece object to be held by BoardTile
     */
    public void givePiece(Piece p) {
        myPieces.add(p);
        myImages.add(p.getImgFile());
    }


    /**
     * sets the color of the given tile
     * @param x x coor
     * @param y y coor
     */
    private void ColorRect(int x, int y) {
        if ((x + y) % 2 == 1) {
            myRectangle.setFill(Color.BLACK);
        } else {
            myRectangle.setFill(Color.WHITESMOKE);
        }
    }

    public void LightUp(Boolean b) {
        if (b) {
            myStackPane.setBorder(myLitUpBorder);
        } else {
            myStackPane.setBorder(myClearBorder);
        }
    }

    private Border makeLitUpBorder() {
        BorderStroke bordstroke = new BorderStroke(Color.CYAN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(BORDER_WIDTH));
        Border bord = new Border(bordstroke);
        return bord;
    }
    private Border makeClearBorder() {
        BorderStroke bordstroke = new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(BORDER_WIDTH));
        Border bord = new Border(bordstroke);
        return bord;
    }

    public StackPane getMyStackPane() {return myStackPane;}
    public Coordinate getCoordinate() {return myCoord;}
}
