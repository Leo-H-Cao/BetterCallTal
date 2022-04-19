package oogasalad.Frontend.Game;

import java.io.File;
import java.util.Optional;
import oogasalad.Frontend.util.Controller;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Setup.BoardSetup;


/**
 * This class will hold the Game View Backend object instances that the FrontEnd needs to make the
 * game work.
 */
public class GameBackend extends Controller {

  private ChessBoard myChessBoard;

  public Optional<ChessBoard> initalizeLocalChessBoard(File JSON) {
    try {
      myChessBoard = BoardSetup.createLocalBoard(JSON.getPath());
      return Optional.of(myChessBoard);
    } catch (Exception e) {
      // myMainView.showError();
      return Optional.empty();
    }
  }

  public Optional<ChessBoard> initalizeHostServerChessBoard(File JSON) {
    try {
      myChessBoard = BoardSetup.createRemoteBoard(JSON.getPath(), "h", 0);
      return Optional.of(myChessBoard);
    } catch (Exception e) {
      // myMainView.showError();
      return Optional.empty();
    }
  }

  public Optional<ChessBoard> initalizeJoinServerChessBoard(File JSON) {
    try {
      myChessBoard = BoardSetup.joinRemoteBoard("h");
      int myColor = myChessBoard.getThisPlayer();
      return Optional.of(myChessBoard);
    } catch (Exception e) {
      // myMainView.showError();
      return Optional.empty();
    }
  }

  public ChessBoard getChessBoard() {
    return myChessBoard;
  }
}
