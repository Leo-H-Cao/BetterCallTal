package oogasalad.Frontend.Game;

import oogasalad.Frontend.ViewManager;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.Server.BoardSetup;

import java.io.File;

/**
 * This class will hold the Game View Backend object instances that the
 * FrontEnd needs to make the game work.
 */

public class GameBackend {

    private ChessBoard myChessBoard;
    private final ViewManager myViewManager;

    public GameBackend (ViewManager mv) {
        myViewManager = mv;
    }

    public void initalizeChessBoard(File JSON) {
        try {
            BoardSetup bs = new BoardSetup(JSON.getPath());
            myChessBoard = bs.createBoard();
            myViewManager.getViews().stream()
                    .filter(e -> e.getClass() == GameView.class)
                    .forEach(e -> ((GameView) e).SetUpBoard(myChessBoard, 0)); //TODO: Figure out player ID stuff
        } catch (Exception e){
            // myMainView.showError();
        }
    }

    public ChessBoard getChessBoard() {
        return myChessBoard;
    }
}
