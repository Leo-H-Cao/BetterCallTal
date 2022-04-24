package oogasalad.Editor.Exceptions;

/**
 * Exception for when user tries to create invalid movements on movement grid
 * @author Leo Cao
 */
public class MovementGridException extends EditorException{

  public MovementGridException(String errorMessage){
    super(errorMessage);
  }

}
