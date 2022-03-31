package oogasalad.GamePlayer.EngineExceptions;

public class BoardModificationAfterStartException extends EngineException {

  /***
   * Exception if the board is directly modified after a game has started
   *
   * @param errorMessage is the String associated with the error
   */
  public BoardModificationAfterStartException(String errorMessage) {
    super(errorMessage);
  }
}
