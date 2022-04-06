package oogasalad.Editor.API;

public interface ModifiesBoardState {

  /**
   * Executes necessary changes from user to alter board shape
   * @param height of board
   * @param width of board
   * @return BoardShape with new dimensions
   */
   void changeBoardSize(int height, int width);

  /**
   * Creates special effect that can be placed on board tiles as described by user
   * @return new tile effect
   */
  void addTileEffect(int x, int y, String effectString);

  /**
   * Deletes the specified tile effect from the board, no longer available for user to place on board
   * @param x coordinate of where effect should be added
   * @param y coordinate of where effect should be added
   * @return the deleted tileEffect object
   */
   void deleteTileEffect(int x, int y);


  int getBoardWidth();

  int getBoardHeight();

  void setPieceStartingLocation(String pieceID, int x, int y);

  void removePiece(String pieceID, int x, int y);
}
