package oogasalad.GamePlayer.Board.Tiles;

import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

public interface Tile {
  void addPiece(Piece piece);
  Coordinate getCoordinates();


  //void setModifer();

}
