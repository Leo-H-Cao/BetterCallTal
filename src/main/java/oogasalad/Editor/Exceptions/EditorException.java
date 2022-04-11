package oogasalad.Editor.Exceptions;

public abstract class EditorException extends RuntimeException{
  /***
   * Exceptions with the editor model
   *
   * @param errorMessage is the String associated with the error
   */
  public EditorException(String errorMessage) {
    super(errorMessage);
  }

}
