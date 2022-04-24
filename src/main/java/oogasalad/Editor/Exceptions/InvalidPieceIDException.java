package oogasalad.Editor.Exceptions;

/**
 * Exception when there is query for piece with pieceID that does not exist
 * @author Leo Cao
 */
public class InvalidPieceIDException extends EditorException{

  public InvalidPieceIDException(String errorMessage){
    super(errorMessage);
  }

}
