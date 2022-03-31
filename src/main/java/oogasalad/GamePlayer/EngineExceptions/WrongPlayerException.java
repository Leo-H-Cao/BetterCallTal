package oogasalad.GamePlayer.EngineExceptions;

public class WrongPlayerException extends EngineException {

  /***
   * Exception if an illegal move is played
   *
   * @param errorMessage is the String associated with the error
   */
  public WrongPlayerException(String errorMessage) {
    super(errorMessage);
  }
}
