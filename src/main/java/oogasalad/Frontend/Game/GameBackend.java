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

    public ChessBoard initalizeChessBoard(File JSON) {
        try {
            BoardSetup bs = new BoardSetup(JSON.getPath());
            myChessBoard = bs.createBoard();
            return myChessBoard;
        } catch (Exception e){
            // myMainView.showError();
            return null;
        }
    }

    public ChessBoard getChessBoard() {
        return myChessBoard;
    }
}
