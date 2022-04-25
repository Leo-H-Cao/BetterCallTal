package oogasalad.GamePlayer.EngineExceptions;

/***
 * Exception due to a given coordinate being outside of the board
 *
 * @author Vincent Chen
 */
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
