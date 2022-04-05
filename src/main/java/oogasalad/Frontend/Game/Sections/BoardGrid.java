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

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is handles the chess board GridPane.
 */

public class BoardGrid {

    private GridPane myBoard;
    private static Double HEIGHT_BOARD = 600.0;
    private static Double WIDTH_Board = 600.0;
    private ArrayList<BoardTile> myBoardTiles;

    public BoardGrid(ChessBoard cb, int PlayerID) {
        myBoard = new GridPane();
        setUpGP(myBoard, cb.getBoardHeight(), cb.getBoardLength());
        makeBoard(myBoard, cb, PlayerID);
    }

    /**
     * creates a standard 8x8 board. Used for testing.
     */
    public BoardGrid() {
        myBoard = new GridPane();
        setUpGP(myBoard, 8, 8);
        makeBoard2(myBoard, 8, 8);
    }

    private void setUpGP(GridPane gp, int rows, int cols) {
        for (int i=0; i < rows; i++) {
            gp.getRowConstraints().add(new RowConstraints(WIDTH_Board / rows));
        }
        for (int k=0; k < cols; k ++) {
            gp.getColumnConstraints().add(new ColumnConstraints(HEIGHT_BOARD / cols));
        }
    }

    private void makeBoard(GridPane gp, ChessBoard cb, int id) {
        myBoardTiles = new ArrayList<>();
        for (ChessTile ct : cb) {
            BoardTile tile = new BoardTile(ct.getCoordinates().getRow(), ct.getCoordinates().getCol(), cb.getBoardHeight(), cb.getBoardLength());
            if (! ct.getPieces().isEmpty()) {
                for (Piece p : ct.getPieces()) {
                    tile.givePiece(p);
                }
            }

            myBoardTiles.add(tile);

            int grid_x = ct.getCoordinates().getRow();
            int grid_y = ct.getCoordinates().getCol();

            if (id == 1) {
                grid_x = cb.getBoardHeight() - grid_x;
                grid_y = cb.getBoardLength() - grid_y;
            }
            gp.add(tile.getMyRectangle(), grid_x, grid_y);
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
     * method for GameView to use to retrieve GridPane of Board
     * @return GridPane myBoard
     */
    public GridPane getBoard() {return myBoard;}

    /**
     * THIS METHOD SOLELY FOR TESTING
     */
    private void makeBoard2(GridPane gp, int rows, int cols) {
        for (int r =0; r < rows; r++) {
            for (int c=0; c < cols; c++) {
                Rectangle rect = new Rectangle(WIDTH_Board / rows,HEIGHT_BOARD / cols);
                if ((c+r) % 2 == 1) {
                    rect.setFill(Color.BLACK);
                } else {
                    rect.setFill(Color.WHITESMOKE);
                }
                gp.add(rect, r, c);
            }
        }
    }
}
