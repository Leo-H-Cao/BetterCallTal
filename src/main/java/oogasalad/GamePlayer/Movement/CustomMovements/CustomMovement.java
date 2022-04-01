package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementInterface;

public interface CustomMovement {

  /***
   * Returns a list of movements to be added to the current movement set if applicable
   *
   * @param piece that is moving
   * @param board referenced
   * @return new moves based on custom rules
   */
  List<MovementInterface> getMovements(Piece piece, ChessBoard board);

  /***
   *  Returns a list of captures to be added to the current capture set if applicable
   *
   * @param piece that is moving
   * @param board referenced
   * @return new moves based on custom rules
   */
  List<MovementInterface> getCaptures(Piece piece, ChessBoard board);
}
