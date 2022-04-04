package oogasalad.GamePlayer.Board.Tiles;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

public interface Tile {
  void addPiece(Piece piece);
  Coordinate getCoordinates();


  //void setModifer();

}
