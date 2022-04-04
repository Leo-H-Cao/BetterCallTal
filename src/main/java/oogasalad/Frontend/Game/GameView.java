package oogasalad.Frontend.Game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.Game.Sections.BoardGrid;
import oogasalad.Frontend.Game.Sections.BoardTile;
import oogasalad.Frontend.Game.Sections.TopSection;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.SceneView;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Board.ChessBoard;

import java.util.Collection;

/**
 * This class will handle the View for the game being played.
 */


public class GameView extends SceneView {

    private ChessBoard myBoard;
    private Collection<ChessTile> myTiles;
    private BoardGrid myBoardGrid;
    private static Integer SCENE_WIDTH_SIZE = 1500;
    private static Integer SCENE_HEIGHT_SIZE = 1000;
    private static final String TITLE = "Title";

    public GameView(MainView mainView) {
        super(mainView);
    }

    /**
     * setUpBoard method will be called when the user hosts a game and uploads a JSon game file.
     * The back-end will parse the information and call this method with objects that contain
     * where the starting pieces, power ups, etc. go, and any other information relavant to
     * setting up the board.
     */

    public void SetUpBoard(ChessBoard chessboard) {
        myBoard = chessboard;
        for (ChessTile ct : myBoard) {
            BoardTile bt = new BoardTile(ct.getCoordinates().getRow(), ct.getCoordinates().getCol(), ct.getPiece());
        }
    }


    /**
     * lightUpSquares() will be called to make the correct squares light up when a piece is selected.
     * Only when a square is lit up and clicked will a move be made.
     */

    public void lightUpSquares(Collection<ChessTile> litTiles) {

    }

    /**
     * completeMove() will be called by the backend when a player clicks a lit up square. All the
     * the logic such as power ups, captures, etc will be registered on the backend, and an updated
     * board will be displayed.
     */
    public void completeMove(Collection<ChessTile> newboard, Collection<Piece> newpieces){}

    @Override
    protected Scene makeScene() {
        BorderPane bp = new BorderPane();
        bp.setTop(new TopSection(getClass().getSimpleName()).getGP());
        myBoardGrid = new BoardGrid(8, 8);
        myBoardGrid.getBoard().setAlignment(Pos.CENTER);
        bp.setCenter(myBoardGrid.getBoard());

        myRoot.getChildren().add(bp);
        return new Scene(myRoot, SCENE_WIDTH_SIZE, SCENE_HEIGHT_SIZE);
    }


}