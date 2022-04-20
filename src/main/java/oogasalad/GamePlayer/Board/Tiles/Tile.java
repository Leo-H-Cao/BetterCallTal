package oogasalad.GamePlayer.Board.Tiles;

import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

@Deprecated
public interface Tile {

  /**
   * Adds a piece to the board
   * @param piece the Piece to be added
   */
  void addPiece(Piece piece);

  /**
   * Getter method for the Tile's coordintes
   * @return the Tile's Coordinates
   */
  Coordinate getCoordinates();
}
