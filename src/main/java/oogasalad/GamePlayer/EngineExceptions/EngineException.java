package oogasalad.GamePlayer.EngineExceptions;

/***
 * Generic exception with the game engine
 *
 * @author Vincent Chen
 */
public abstract class EngineException extends Exception {

  /***
   * Exceptions with the chess model
   *
   * @param errorMessage is the String associated with the error
   */
  public EngineException(String errorMessage) {
    super(errorMessage);
  }
}
