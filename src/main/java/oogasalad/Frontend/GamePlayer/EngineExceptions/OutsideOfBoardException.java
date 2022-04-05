package oogasalad.Frontend.GamePlayer.EngineExceptions;

public class OutsideOfBoardException extends EngineException {

  /***
   * Exception if a move goes outside the board
   *
   * @param errorMessage is the String associated with the error
   */
  public OutsideOfBoardException(String errorMessage) {
    super(errorMessage);
  }
}
