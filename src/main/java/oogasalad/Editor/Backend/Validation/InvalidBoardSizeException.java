package oogasalad.Editor.Backend.Validation;

public class InvalidBoardSizeException extends RuntimeException{

  public InvalidBoardSizeException(String errorMessage){
    super(errorMessage);
  }
}
