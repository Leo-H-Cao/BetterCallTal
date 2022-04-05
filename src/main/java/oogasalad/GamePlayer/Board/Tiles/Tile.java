package oogasalad.GamePlayer.Board.Tiles;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.GamePiece.Piece;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;

public interface Tile {
  void addPiece(Piece piece);
  Coordinate getCoordinates();
  void executeAction(ChessBoard board) throws OutsideOfBoardException;


  //void setModifer();

}
