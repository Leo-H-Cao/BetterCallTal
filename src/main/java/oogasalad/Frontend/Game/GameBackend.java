package oogasalad.Frontend.Game;

import oogasalad.Frontend.MainView;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.Server.BoardSetup;

import java.io.File;

/**
 * This class will hold the Game View Backend object instances that the
 * FrontEnd needs to make the game work.
 */

public class GameBackend {
    private ChessBoard myChessBoard;
    private MainView myMainView;

    public GameBackend (MainView mv) {
        myMainView = mv;
    }

    public void initalizeChessBoard(File JSON) {
        try {
            BoardSetup bs = new BoardSetup("JSON");
        } catch (Exception e){
            // myMainView.showError();
        }
    }
}
