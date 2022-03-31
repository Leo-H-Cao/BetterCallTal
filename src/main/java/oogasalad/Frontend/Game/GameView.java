package oogasalad.Frontend.Game;

import javafx.stage.Stage;
import oogasalad.GamePlayer.Board.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

import java.util.Collection;

/**
 * This class will handle the View for the game being played.
 */


public class GameView {

    private Collection<ChessTile> myBoard;
    private Collection<Piece> myPieces;

    private Stage myStage;

    public GameView(Stage stage) {
        myStage = stage;
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

}
