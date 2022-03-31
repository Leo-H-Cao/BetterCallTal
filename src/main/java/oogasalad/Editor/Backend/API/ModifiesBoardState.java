package oogasalad.Editor.Backend.API;

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


  /**
   * Places a certain piece at a location specified by user on board for initial starting position
   */
  void addPieceStartingLocation(String pieceID, int x, int y);

  /**
   * Removes piece from board representing starting locations
   * @param x coordinate of piece to be removed
   * @param y coordinate of piece to be removed
   */
  void removePiece(int x, int y);


  int getBoardWidth();

  int getBoardHeight();
}
