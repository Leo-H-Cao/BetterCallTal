package oogasalad.GamePlayer.Board.Tiles;

import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;
import oogasalad.Frontend.GamePlayer.Movement.Coordinate;

public interface Tile {
  void addPiece(Piece piece);
  Coordinate getCoordinates();
  void executeAction(ChessBoard board) throws OutsideOfBoardException;


  //void setModifer();

}
