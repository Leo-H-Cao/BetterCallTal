package oogasalad;

public interface Operation {

  /***
   * Executes some action on a piece, intended to be before/after a piece has moved (e.g. explode
   * pieces in atomic mode).
   *
   * @param piece that just moved
   * @return map of updated tiles
   */
  Map<Tile, Piece> execute(Piece piece);
}
