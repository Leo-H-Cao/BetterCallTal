package oogasalad.GamePlayer.EngineExceptions;

/***
 * Exception due to a piece not being found
 *
 * @author Vincent Chen
 */
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
