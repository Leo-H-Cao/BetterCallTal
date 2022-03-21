package oogasalad;

public interface Piece {

  /***
   * Moves piece to square
   * @param tile to move to
   * @return map of updated squares
   */
  Map<Tile, Piece> move(Tile tile);
}
