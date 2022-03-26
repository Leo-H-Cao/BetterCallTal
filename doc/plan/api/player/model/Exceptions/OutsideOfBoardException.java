package oogasalad;

public class OutsideOfBoardException extends ChessModelException {

  /***
   * Exception if a move goes outside of the board
   *
   * @param errorMessage is the String associated with the error
   */
  public OutsideOfBoardException(String errorMessage) {
    super(errorMessage);
  }
}
