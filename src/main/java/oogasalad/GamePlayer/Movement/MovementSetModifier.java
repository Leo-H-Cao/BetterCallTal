package oogasalad.GamePlayer.Movement;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;

public interface MovementSetModifier {

  /***
   * Modifies a set of movements based on some rule, such as check. All the moves that keep the
   * king in check would be removed. Moves such as en passant, on the other hand, would add moves
   * to the movement set.
   *
   * @param piece that is moving
   * @param movementSet to modify
   * @param board referenced
   * @return set of modified moves to fit rule determined by the exact implementation
   */
  Set<Movement> updateMovementSet(Piece piece, Set<Movement> movementSet, ChessBoard board);

}
