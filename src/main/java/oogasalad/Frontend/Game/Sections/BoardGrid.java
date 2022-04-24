package oogasalad.Frontend.Game.Sections;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This class is handles the chess board GridPane.
 */

public class BoardGrid {

    private static final Logger LOG = LogManager.getLogger(BoardGrid.class);

    private GridPane myBoard;
    private static Double HEIGHT_BOARD = 600.0;
    private static Double WIDTH_Board = 600.0;
    private ArrayList<BoardTile> myBoardTiles;
    private Piece mySelectedPiece;
    private ArrayList<BoardTile> myLitTiles;
    private Runnable ClearLitTilesRun;
    private Consumer<Piece> setSelPiece;
    private Integer Turn;
    private Integer myID;


    public BoardGrid(ChessBoard cb, int PlayerID, Consumer<Piece> lightupCons, Consumer<Coordinate> MoveCons, BiConsumer<String, String> errorRun) {
        myLitTiles = new ArrayList<>();
        myBoard = new GridPane();
        makeRunAndCons();
        setUpGP(myBoard, cb.getBoardHeight(), cb.getBoardLength());
        makeBoard(cb, PlayerID, lightupCons, MoveCons, errorRun);
        myID = PlayerID;
    }

    private void setUpGP(GridPane gp, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            gp.getRowConstraints().add(new RowConstraints(HEIGHT_BOARD / Math.max(rows, cols)));
        }
        for (int k = 0; k < cols; k++) {
            gp.getColumnConstraints().add(new ColumnConstraints(WIDTH_Board / Math.max(rows, cols)));
        }
    }

    private void makeBoard(ChessBoard cb, int id, Consumer<Piece> lightupCons, Consumer<Coordinate> MoveCons, BiConsumer<String, String> errorRun) {
        myBoardTiles = new ArrayList<>();
        for (ChessTile ct : cb) {
            int grid_x = ct.getCoordinates().getCol();
            int grid_y = ct.getCoordinates().getRow();
            BoardTile tile = new BoardTile(ct,
                    WIDTH_Board / Math.max(cb.getBoardLength(), cb.getBoardHeight()),
                    HEIGHT_BOARD / Math.max(cb.getBoardLength(), cb.getBoardHeight()),
                    lightupCons, ClearLitTilesRun, setSelPiece, MoveCons, errorRun);
            myBoardTiles.add(tile);

            if (id == 1) {
                grid_x = cb.getBoardLength() - grid_x;
                grid_y = cb.getBoardHeight() - grid_y;
            }
            myBoard.add(tile.getMyStackPane(), grid_x, grid_y);
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
     *
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
        if (!myLitTiles.isEmpty()) {
            for (BoardTile bt : myLitTiles) {
                bt.LightUp(false);
            }
            myLitTiles.clear();
        }
    }

    /**
     * method for GameView to use to retrieve GridPane of Board
     *
     * @return GridPane myBoard
     */
    public GridPane getBoard() {
        return myBoard;
    }

    public Piece getSelectedPiece() {
        return mySelectedPiece;
    }

    private void makeRunAndCons() {
        ClearLitTilesRun = () -> turnOffTiles();
        setSelPiece = piece -> setSelectedPiece(piece);
    }

    /**
     * used in boardtile action setter. If the tile is not set up and there's a piece present, make it the selected piece.
     * selected piece must be tracked in order to complete move.
     *
     * @param p piece to be made selected piece.
     */
    public void setSelectedPiece(Piece p) {
        LOG.debug(String.format("SELECTED PIECE IS NOW: %s (%d, %d)\n", p.getName(), p.getCoordinates().getRow(), p.getCoordinates().getCol()));
//        LOG.debug("SELECTED PIECE MOVES: " + myModelBoard.getMoves(p));
//        if (p.getTeam() == myID) {
        mySelectedPiece = p;
//        }
    }

    /**
     * flip method called from GameView to flip the board for the player.
     */
    public void flip() {
        myBoard.setRotate(myBoard.getRotate() + 180.0);
        for (BoardTile bt : myBoardTiles) {
            bt.getMyStackPane().setRotate(bt.getMyStackPane().getRotate() + 180.0);
        }
    }
}