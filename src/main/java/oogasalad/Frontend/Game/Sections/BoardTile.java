package oogasalad.Frontend.Game.Sections;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * BoardTile class will contain all the information about each tile that must be displayed, and will hold the node.
 */

public class BoardTile {

    private static final Logger LOG = LogManager.getLogger(BoardTile.class);

    private StackPane myStackPane;
    private ArrayList<Node> myImages;
    private Rectangle myRectangle;
    private ArrayList<Piece> myPieces;
    private static Double myTileHeight;
    private static Double myTileWidth;
    private  Coordinate myCoord;
    private Double BORDER_WIDTH = 2.0;
    private Double EMPTY_BORDER = 0.0;
    private int TILEMOD= -2;
    private Boolean Lit;
    private String Image_Path;
    private Color BlackSquare = Color.GREEN;
    private Color WhiteSquare = Color.LIGHTGREY;
    private String LitColor = "Cyan";
    private BiConsumer<String, String> ErrorRunnable;
    private int borderSpacing = 10;

    public BoardTile(ChessTile ct, double width, double height, Consumer<Piece> lightupCons, Runnable clearlitrun, Consumer<Piece> setSelPiece, Consumer<Coordinate> MoveCons, BiConsumer<String, String> errorRun, String pack) {
        Image_Path = "src/main/resources/images/pieces/" + pack + "/";
        myCoord = ct.getCoordinates();
        myStackPane = new StackPane();
        addActionToSP(lightupCons, clearlitrun, setSelPiece, MoveCons);
        ErrorRunnable = errorRun;
        myTileHeight = height;
        myTileWidth = width;
        myRectangle = new Rectangle(myTileWidth, myTileHeight);
        ColorRect(myCoord.getCol(), myCoord.getRow());

        myPieces = new ArrayList<>();
        myImages = new ArrayList<>();
        Lit = false;
        updateTile(ct);
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
        LOG.debug(String.format("Updating tile: (%d, %d)", this.getCoordinate().getRow(), this.getCoordinate().getCol()));
        removePieceImages();
        myPieces.clear();
        myImages.clear();

        if (ct.getCustomImg().isPresent()) {
            ImageView tilemod = CreateImage(ct.getCustomImg().get(), TILEMOD);
            myImages.add(tilemod);
            placeUnderRectangle(tilemod);
        }
        for (Piece p : ct.getPieces()) {
            givePiece(p);
        }
    }

    private void placeUnderRectangle(Node n) {
        myStackPane.getChildren().remove(myRectangle);
        myRectangle.setFill(Color.TRANSPARENT);
        myStackPane.getChildren().addAll(n, myRectangle);
    }

    private void givePiece(Piece p) {
        myPieces.add(p);
        ImageView pieceview = CreateImage(p.getName(), p.getTeam());
        myImages.add(pieceview);
        myStackPane.getChildren().add(pieceview);
    }

    private ImageView CreateImage(String name, int team) {
        try {
            String TEAM;
            if (team == 0) {TEAM = "white/";
            } else if (team == 1){TEAM = "black/";
            } else if (team == -1){TEAM = "piecemodifiers/";
            } else {TEAM = "tilemodifiers/";}
            System.out.println(Image_Path + TEAM + name.toLowerCase() + ".png");
            Image image = new Image(new FileInputStream(Image_Path + TEAM + name.toLowerCase() + ".png"));
            ImageView PieceView = new ImageView(image);
            if (team == -2) {
                PieceView.setFitHeight(myTileHeight);
                PieceView.setFitWidth(myTileWidth);
            } else {
                PieceView.setFitHeight(myTileHeight - borderSpacing);
                PieceView.setFitWidth(myTileWidth - borderSpacing);
            }
            PieceView.setPreserveRatio(true);
            PieceView.setSmooth(true);
            PieceView.setCache(true);
            return PieceView;
        } catch (Exception e) {
            ErrorRunnable.accept(e.getClass().getSimpleName(), e.getMessage());
        }
        return null;
    }

    /**
     * sets the color of the given tile
     * @param x x coor
     * @param y y coor
     */
    private void ColorRect(int x, int y) {
        if ((x + y) % 2 == 1) {
            myRectangle.setFill(BlackSquare);
        } else {
            myRectangle.setFill(WhiteSquare);
        }
        myRectangle.setStroke(Paint.valueOf(LitColor));
        myRectangle.setStrokeType(StrokeType.INSIDE);
        myRectangle.setStrokeWidth(EMPTY_BORDER);
        myStackPane.getChildren().add(myRectangle);
    }

    /**
     * "Lights up" the tile by setting the border pane.
     * @param b True for turn it on, false for turn it off.
     */
    public void LightUp(Boolean b) {
        Lit = b;
        LOG.debug(String.format("Lit up: %d, %d", myCoord.getRow(), myCoord.getCol()));
        if (Lit) {
            myRectangle.setStrokeWidth(BORDER_WIDTH);
        } else {
            myRectangle.setStrokeWidth(EMPTY_BORDER);
        }
    }

    private void removePieceImages() {
        myStackPane.getChildren().removeAll(myImages);
    }

    public StackPane getMyStackPane() {return myStackPane;}
    public Coordinate getCoordinate() {return myCoord;}
}
