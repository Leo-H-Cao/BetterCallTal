package oogasalad.Frontend.Game.Sections;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import java.util.ArrayList;
import java.util.function.Consumer;


/**
 * BoardTile class will contain all the information about each tile that must be displayed, and will hold the node.
 */

public class BoardTile {
    private StackPane myStackPane;
    private ArrayList<Node> myImages;
    private Rectangle myRectangle;
    private ArrayList<Piece> myPieces;
    private static Double myTileHeight;
    private static Double myTileWidth;
    private static Double HEIGHT_BOARD = 600.0;
    private static Double WIDTH_Board = 600.0;
    private  Coordinate myCoord;
    private Border myLitUpBorder;
    private Border myClearBorder;
    private Double BORDER_WIDTH = 5.0;
    private Boolean Lit;

    public BoardTile(int x, int y, int rows, int cols, Consumer<Piece> lightupCons, Runnable clearlitrun, Consumer<Piece> setSelPiece, Consumer<Coordinate> MoveCons) {
        myCoord = new Coordinate(y, x);
        myStackPane = new StackPane();
        addActionToSP(lightupCons, clearlitrun, setSelPiece, MoveCons);
        myTileHeight = HEIGHT_BOARD / cols;
        myTileWidth = WIDTH_Board / rows;
        myRectangle = new Rectangle(myTileWidth, myTileHeight);
        ColorRect(x, y);

        myPieces = new ArrayList<>();
        myImages = new ArrayList<>();
        myLitUpBorder = makeLitUpBorder();
        myClearBorder = makeClearBorder();
        Lit = false;
    }

    private void addActionToSP(Consumer<Piece> lightupCons, Runnable clearlitrun, Consumer<Piece> setSelPiece, Consumer<Coordinate> MoveCons) {
        ButtonFactory.addAction(myStackPane,
                (e) -> {
            if (!Lit && myPieces.isEmpty()) {clearlitrun.run();}
            if (!Lit && ! myPieces.isEmpty()){
                clearlitrun.run();
                lightupCons.accept(myPieces.get(0));
                setSelPiece.accept(myPieces.get(0));
            }
            if (Lit) {
                clearlitrun.run();
                MoveCons.accept(myCoord);
            }
                });
    }

    /**
     * updateTile will be called by BoardGrid whenever a tile needs to be updated.
     * @param ct Chess tile to be updated.
     */

    public void updateTile(ChessTile ct) {
        if (! myPieces.isEmpty()) {
            removePieceImages();
            myPieces.clear();
            myImages.clear();
        }
        myPieces.addAll(ct.getPieces());
        for (Piece p : myPieces) {
            ImageView pieceview = CreateImage(p.getImgFile());
            myImages.add(pieceview);
            myStackPane.getChildren().add(pieceview);
        }
    }

    /**
     * Used in BoardGrid to give Board tile the correct piece.
     * @param p Piece object to be held by BoardTile
     */
    public void givePiece(Piece p) {
        myPieces.add(p);
        ImageView pieceview = CreateImage(p.getImgFile());
        myImages.add(pieceview);
        myStackPane.getChildren().add(pieceview);
    }

    private ImageView CreateImage(String image) {
        //ImageView myPieceView = new ImageView(image);
        ImageView PieceView = new ImageView("Duvall.png");
        PieceView.setFitHeight(myTileHeight - 10);
        PieceView.setFitHeight(myTileWidth - 10);
        PieceView.setPreserveRatio(true);
        PieceView.setSmooth(true);
        PieceView.setCache(true);
        return PieceView;
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
        myStackPane.getChildren().add(myRectangle);
    }

    /**
     * "Lights up" the tile by setting the border pane.
     * @param b True for turn it on, false for turn it off.
     */
    public void LightUp(Boolean b) {
        if (b) {
            myStackPane.setBorder(myLitUpBorder);
            Lit = true;
        } else {
            myStackPane.setBorder(myClearBorder);
            Lit = false;
        }
    }

    private void removePieceImages() {
        myStackPane.getChildren().removeAll(myImages);
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
