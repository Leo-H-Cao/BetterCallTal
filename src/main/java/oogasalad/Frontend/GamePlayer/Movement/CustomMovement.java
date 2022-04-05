package oogasalad.Frontend.GamePlayer.Movement;

import java.util.List;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;

public interface CustomMovement {

  /***
   * Modifies a list of movements based on additional movements. Moves such as en passant would
   * add moves to the movement list in certain situations
   *
   * @param piece that is moving
   * @param board referenced
   * @return new moves based on custom rules
   */
  List<Movement> getMovements(Piece piece, ChessBoard board);
}
