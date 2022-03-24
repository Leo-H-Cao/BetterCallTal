package oogasalad;

public abstract class ChessModelException extends Exception {

  /***
   * Exceptions with the chess model
   *
   * @param errorMessage is the String associated with the error
   */
  public ChessModelException(String errorMessage) {
    super(errorMessage);
  }
}
