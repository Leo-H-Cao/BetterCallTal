package oogasalad.Editor.Validation;

import oogasalad.Editor.Exceptions.InvalidBoardSizeException;

public class Validator {
  private final int MAX_BOARD_SIZE = 25;
  private final int MIN_BOARD_SIZE = 1;
  private final String BIG_BOARD_SIZE_ERR_MSG = "The dimensions of the board exceed the maximum (25)";
  private final String SMALL_BOARD_SIZE_ERR_MSG = "The dimensions of the board are less than the minimum (1)";

  public Boolean checkBoardSizeValidity(int height, int width) {
    //board is too small
    if(height < MIN_BOARD_SIZE || width < MIN_BOARD_SIZE){
      throw new InvalidBoardSizeException(SMALL_BOARD_SIZE_ERR_MSG);
    }

    //board is too large
    if(height > MAX_BOARD_SIZE || width > MAX_BOARD_SIZE){
      throw new InvalidBoardSizeException(BIG_BOARD_SIZE_ERR_MSG);
    }
    return true;
  }
}
