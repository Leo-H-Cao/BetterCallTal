package oogasalad.GamePlayer.EngineExceptions;

/***
 * Exception due to invalid move
 *
 * @author Vincent Chen
 */
public class InvalidMoveException extends EngineException {

  /***
   * Exception if an illegal move is played
   *
   * @param errorMessage is the String associated with the error
   */
  public InvalidMoveException(String errorMessage) {
    super(errorMessage);
  }
}
