package oogasalad.Editor.Backend.API;

public interface ValidateSettings {

  /**
   * Validates user input for board size, makes sure it is within the bounds defined
   * @return true is board dimensions are valid, false otherwise
   */
  public Boolean checkBoardSizeValidity(int height, int width);

}