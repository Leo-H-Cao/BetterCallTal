package oogasalad.Frontend.GamePlayer.Movement;

import java.util.Set;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;

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
