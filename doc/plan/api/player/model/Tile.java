package oogasalad;

public interface Tile {

  /***
   * Gets piece(s) on a square
   *
   * @return list of pieces on the square
   */
  List<Piece> getPieces();

  /***
   * Gets coordinates of tile
   *
   * @return Coordinate of tile
   */
  Coordinate getCoords();
}
