package oogasalad.GamePlayer.Movement.MovementModifiers;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

/***
 * Abstraction that allows functionality to happen after a move is made
 *
 * @author Vincent Chen
 */
public interface MovementModifier {

  /***
   * Modifies a movement based on rules such as check
   *
   * @param piece that is referenced
   * @param board that piece is on
   * @return Set of updated tiles after movement is modified
   */
  Set<ChessTile> updateMovement(Piece piece, ChessBoard board);
}
