package oogasalad.Frontend.game;

import java.util.Optional;

import oogasalad.Frontend.util.Controller;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.Server.BoardSetup;

import java.io.File;

/**
 * This class will hold the Game View Backend object instances that the
 * FrontEnd needs to make the game work.
 */

public class GameBackend extends Controller {

    private ChessBoard myChessBoard;

    public Optional<ChessBoard> initalizeChessBoard(File JSON) {
        try {
            BoardSetup bs = new BoardSetup(JSON.getPath());
            myChessBoard = bs.createBoard();
            System.out.println("noerror");
            return Optional.of(myChessBoard);
        } catch (Exception e){
            // myMainView.showError();
            System.out.println("error");
            return Optional.empty();
        }
    }

    public ChessBoard getChessBoard() {
        return myChessBoard;
    }
}
