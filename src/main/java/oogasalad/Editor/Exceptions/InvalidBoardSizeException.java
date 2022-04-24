package oogasalad.Editor.Exceptions;

/**
 * Exception for board dimensions that are too large/small set by user
 * @author Leo Cao
 */
public class InvalidBoardSizeException extends EditorException{

  public InvalidBoardSizeException(String errorMessage){
    super(errorMessage);
  }
}
