package oogasalad.GamePlayer.Movement;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

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
