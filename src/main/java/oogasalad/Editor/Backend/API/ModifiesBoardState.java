package oogasalad.Editor.Backend.API;

import oogasalad.Editor.Backend.EditorPiece;
import oogasalad.Editor.Backend.TileEffect;

public interface ModifiesBoardState {

  /**
   * Executes necessary changes from user to alter board shape
   * @param height of board
   * @param width of board
   * @return BoardShape with new dimensions
   */
  public void changeBoardShape(int height, int width);

  /**
   * Creates special effect that can be placed on board tiles as described by user
   * @return new tile effect
   */
  public void addTileEffect(int x, int y, String effectString);

  /**
   * Deletes the specified tile effect from the board, no longer available for user to place on board
   * @return the deleted tileEffect object
   */
  public void deleteTileEffect(int x, int y);


  /**
   * Places a certain piece at a location specified by user on board for initial starting position
   */
  public void addPieceStartingLocation(EditorPiece piece, int x, int y);

  /**
   * Removes piece from board representing starting locations
   * @param piece to be removed
   * @return removed piece
   */
  public void removePiece(int x, int y);


}
