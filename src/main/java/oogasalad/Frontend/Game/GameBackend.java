package oogasalad.Frontend.Game;

import java.io.File;
import java.util.Optional;

import oogasalad.Frontend.Menu.ErrorPopUp;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.BoardSetup;


/**
 * This class will hold the Game View Backend object instances that the FrontEnd needs to make the
 * game work.
 */
public class GameBackend {

  private ChessBoard myChessBoard;
  private String ERRORMESSAGESPACING = ": ";

  public Optional<ChessBoard> initalizeLocalChessBoard(File JSON) {
    try {
      myChessBoard = BoardSetup.createLocalBoard(JSON.getPath());
      return Optional.of(myChessBoard);
    } catch (Exception e) {
      showError(e.getClass().getSimpleName(), e.getMessage());
      return Optional.empty();
    }
  }

  public Optional<ChessBoard> initalizeHostServerChessBoard(File JSON, String RoomName, int player) {
    try {
      myChessBoard = BoardSetup.createRemoteBoard(JSON.getPath(), RoomName, player);
      return Optional.of(myChessBoard);
    } catch (Exception e) {
      showError(e.getClass().getSimpleName(), e.getMessage());
      return Optional.empty();
    }
  }

  public Optional<ChessBoard> initalizeJoinServerChessBoard(String RoomName) {
    try {
      myChessBoard = BoardSetup.joinRemoteBoard(RoomName);
      return Optional.of(myChessBoard);
    } catch (Exception e) {
      e.printStackTrace();
      showError(e.getClass().getSimpleName(), e.getMessage());
      return Optional.empty();
    }
  }

  /**
   * Getter for chessboard, needed primarily by BoardGrid / GameView classes.
   * @return the game chessboard / engine
   */
  public ChessBoard getChessBoard() {
    return myChessBoard;
  }

  /**
   * Method to be called to create an error pop up.
   * @param classname the name of the class of the specific error being caught.
   * @param errorMessage the message of the error.
   */
  public void showError(String classname, String errorMessage) {
    String message = classname + ERRORMESSAGESPACING + errorMessage;
    ErrorPopUp oops = new ErrorPopUp(message);
  }
}
