package oogasalad.Editor.Exceptions;

public class InvalidBoardSizeException extends RuntimeException{

  public InvalidBoardSizeException(String errorMessage){
    super(errorMessage);
  }
}
