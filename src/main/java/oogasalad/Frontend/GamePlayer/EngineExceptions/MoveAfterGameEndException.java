package oogasalad.Frontend.GamePlayer.EngineExceptions;

public class MoveAfterGameEndException extends EngineException {

  /***
   * Exception if a move is attempted after the game ends
   *
   * @param errorMessage is the String associated with the error
   */
  public MoveAfterGameEndException(String errorMessage) {
    super(errorMessage);
  }
}
