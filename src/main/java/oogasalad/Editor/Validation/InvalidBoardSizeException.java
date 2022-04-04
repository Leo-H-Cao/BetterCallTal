package oogasalad.Editor.Validation;

public class InvalidBoardSizeException extends RuntimeException{

  public InvalidBoardSizeException(String errorMessage){
    super(errorMessage);
  }
}
