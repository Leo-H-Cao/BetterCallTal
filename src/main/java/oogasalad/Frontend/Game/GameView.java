package oogasalad.Frontend.Game;

import javafx.scene.Group;
import javafx.scene.Scene;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;
import java.util.Collection;

/**
 * This class will handle the View for the game being played.
 */


public class GameView {

    private Collection<ChessTile> myBoard;
    private Collection<Piece> myPieces;
    private Scene myScene;
    private Group myRoot;

    public GameView() {
        myRoot = new Group();
        myScene = new Scene(myRoot);
    }

    /**
     * setUpBoard method will be called when the user hosts a game and uploads a JSon game file.
     * The back-end will parse the information and call this method with objects that contain
     * where the starting pieces, power ups, etc. go, and any other information relavant to
     * setting up the board.
     */

    public void SetUpBoard(Collection<ChessTile> board, Collection<Piece> pieces) {
        myBoard = board;
        myPieces = pieces;
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

    private Scene getScene() {
        return myScene;
    }
}
