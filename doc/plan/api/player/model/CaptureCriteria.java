package oogasalad;

public interface CaptureCriteria {

  /***
   * Determines if a piece should be capturing by moving to the designated tile
   *
   * @param piece to check for capture
   * @param tile that the piece moves to
   * @return map of updated tiles
   */
  boolean capture(Piece piece, Tile toTile);
}
