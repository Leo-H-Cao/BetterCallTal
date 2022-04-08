package oogasalad.Frontend.Game.Sections;

import javafx.scene.layout.*;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is handles the chess board GridPane.
 */

public class BoardGrid {

    private static final Logger LOG = LogManager.getLogger(BoardGrid.class);

    private GridPane myBoard;
    private static Double HEIGHT_BOARD = 600.0;
    private static Double WIDTH_Board = 600.0;
    private ArrayList<BoardTile> myBoardTiles;
    private ChessBoard myModelBoard;
    private Piece mySelectedPiece;
    private ArrayList<BoardTile> myLitTiles;
    private Runnable ClearLitTilesRun;
    private Consumer<Piece> setSelPiece;
    private Integer Turn;
    private Integer myID;


    public BoardGrid(ChessBoard cb, int PlayerID, Consumer<Piece> lightupCons, Consumer<Coordinate> MoveCons) {
        myLitTiles = new ArrayList<>();
        myBoard = new GridPane();
        makeRunAndCons();
        setUpGP(myBoard, cb.getBoardHeight(), cb.getBoardLength());
        makeBoard(myBoard, cb, PlayerID, lightupCons, MoveCons);
        myID = PlayerID;
    }

    private void setUpGP(GridPane gp, int rows, int cols) {
        for (int i=0; i < rows; i++) {
            gp.getRowConstraints().add(new RowConstraints(WIDTH_Board / rows));
        }
        for (int k=0; k < cols; k ++) {
            gp.getColumnConstraints().add(new ColumnConstraints(HEIGHT_BOARD / cols));
        }
    }

    private void makeBoard(GridPane gp, ChessBoard cb, int id, Consumer<Piece> lightupCons, Consumer<Coordinate> MoveCons) {
        myModelBoard = cb;
        myBoardTiles = new ArrayList<>();
        for (ChessTile ct : cb) {
            int grid_x = ct.getCoordinates().getCol();
            int grid_y = ct.getCoordinates().getRow();
            BoardTile tile = new BoardTile(ct.getCoordinates(), cb.getBoardHeight(), cb.getBoardLength(), lightupCons, ClearLitTilesRun, setSelPiece, MoveCons);
            if (! ct.getPieces().isEmpty()) {
                for (Piece p : ct.getPieces()) {
                    tile.givePiece(p);}}
            myBoardTiles.add(tile);

            if (id == 1) {
                grid_x = cb.getBoardLength() - grid_x;
                grid_y = cb.getBoardHeight() - grid_y;
            }
            gp.add(tile.getMyStackPane(), grid_x, grid_y);
        }
    }

    /**
     * update() called by GameView to update the BoardTile objects that changes between moves
     */
    public void updateTiles(Collection<ChessTile> tiles) {
        for (ChessTile ct : tiles) {
            BoardTile bt = grabTile(ct.getCoordinates());
            bt.updateTile(ct);
        }
    }

    private BoardTile grabTile(Coordinate coor) {
        for (BoardTile bt : myBoardTiles) {
            if (bt.getCoordinate().equals(coor)) {
                return bt;
            }
        }
        return null;
    }

    /**
     * Method to be called by Game View to tell the correct tiles to light up.
     * @param cts
     */
    public void lightSquares(Collection<ChessTile> cts) {
        LOG.debug("Got to lightsquares in Boardgrid");
        turnOffTiles();
        for (ChessTile ct : cts) {
            BoardTile bt = grabTile(ct.getCoordinates());
            bt.LightUp(true);
            myLitTiles.add(bt);
        }
    }

    /**
     * To be called by clicking on a square that is not lit up.
     */
    private void turnOffTiles() {
        LOG.debug("CLEARED LIT TILES!\n");
        if (! myLitTiles.isEmpty()) {
            for (BoardTile bt : myLitTiles) {
                bt.LightUp(false);
            }
            myLitTiles.clear();
        }
    }

    /**
     * method for GameView to use to retrieve GridPane of Board
     * @return GridPane myBoard
     */
    public GridPane getBoard() {return myBoard;}

    public Piece getSelectedPiece(){return mySelectedPiece;}

    private void makeRunAndCons() {
        ClearLitTilesRun = () -> turnOffTiles();
        setSelPiece = piece -> setSelectedPiece(piece);
    }

    /**
     * used in boardtile action setter. If the tile is not set up and there's a piece present, make it the selected piece.
     * selected piece must be tracked in order to complete move.
     * @param p piece to be made selected piece.
     */
    public void setSelectedPiece(Piece p) {
        LOG.debug(String.format("SELECTED PIECE IS NOW: %s (%d, %d)\n", p.getName(), p.getCoordinates().getRow(), p.getCoordinates().getCol()));
        LOG.debug("SELECTED PIECE MOVES: " + myModelBoard.getMoves(p));
//        if (p.getTeam() == myID) {
            mySelectedPiece = p;
//        }
    }

    /**
     * TESTING FROM HERE DOWNWARD
     */

    /**
     * creates a standard 8x8 board. Used for testing.
     */
    public BoardGrid(Consumer<Piece> lightupCons, int id, Consumer<Coordinate> MoveCons) {
        myLitTiles = new ArrayList<>();
        myBoard = new GridPane();
        makeRunAndCons();
        setUpGP(myBoard, 8, 8);
        makeBoard2(myBoard, 8, 8, lightupCons, MoveCons);
        myID = id;
    }
    private void makeBoard2(GridPane gp, int rows, int cols, Consumer<Piece> lightupCons, Consumer<Coordinate> MoveCons) {
        for (int r =0; r < rows; r++) {
            for (int c=0; c < cols; c++) {
                BoardTile tile = new BoardTile(new Coordinate(c,r), rows, cols, lightupCons, ClearLitTilesRun, setSelPiece, MoveCons);
                tile.LightUp(Boolean.FALSE);
                gp.add(tile.getMyStackPane(), r, c);}}}}