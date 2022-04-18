package oogasalad.GamePlayer.EngineExceptions;

public class PieceNotFoundException extends EngineException {

  /***
   * Exception if a piece can't be found
   *
   * @param errorMessage is the String associated with the error
   */
  public PieceNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
