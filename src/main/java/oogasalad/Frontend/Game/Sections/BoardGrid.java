package oogasalad.Frontend.Game.Sections;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

import java.util.Collection;

/**
 * This class is handlles the chess board GridPane.
 */

public class BoardGrid {

    private GridPane myBoard;
    private static Double HEIGHT_BOARD = 600.0;
    private static Double WIDTH_Board = 600.0;

    public BoardGrid(ChessBoard cb, int PlayerID) {
        myBoard = setUpGP(cb.getBoardHeight(), cb.getBoardLength());
        makeBoard(myBoard, cb, PlayerID);
    }

    public BoardGrid() {
        myBoard = setUpGP(8, 8);
    }

    private GridPane setUpGP(int rows, int cols) {
        GridPane gp = new GridPane();
        for (int i=0; i < rows; i++) {
            gp.getRowConstraints().add(new RowConstraints(WIDTH_Board / rows));
        }
        for (int k=0; k < cols; k ++) {
            gp.getColumnConstraints().add(new ColumnConstraints(HEIGHT_BOARD / cols));
        }
        return gp;
    }

    private void makeBoard(GridPane gp, ChessBoard cb, int id) {
        for (ChessTile ct : cb) {
            BoardTile tile = new BoardTile(ct.getCoordinates().getCol(), ct.getCoordinates().getRow(), cb.getBoardHeight(), cb.getBoardLength());
            if (! ct.getPieces().isEmpty()) {
                for (Piece p : ct.getPieces()) {
                    tile.givePiece(p);
                }
            }
            int grid_x = ct.getCoordinates().getRow();
            int grid_y = ct.getCoordinates().getCol();

            if (id == 1) {
                grid_x = cb.getBoardHeight() - grid_x;
                grid_y = cb.getBoardLength() - grid_y;
            }

            gp.add(tile.getMyRectangle(), grid_x, grid_y);
        }
    }

    private void placePiece() {

    }

    // TODO: grabTile() method
    private BoardTile grabTile(Coordinate coor) {
        return null;
    }

    /**
     * update() called by GameView to update the BoardTile objects that changes between moves
     */
    public void updateTiles(Collection<ChessTile> tiles) {

    }

    /**
     * method for GameView to use to retrieve GridPane of Board
     * @return GridPane myBoard
     */
    public GridPane getBoard() {return myBoard;}
}
