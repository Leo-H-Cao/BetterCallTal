package oogasalad.GamePlayer.EngineExceptions;

public class InvalidBoardSizeException extends EngineException {

  /***
   * Exception if the board size is invalid. For example, crazyhouse requires a certain bank
   * size to operate properly
   *
   * @param errorMessage is the String associated with the error
   */
  public InvalidBoardSizeException(String errorMessage) {
    super(errorMessage);
  }
}
