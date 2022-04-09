package oogasalad.GamePlayer.Movement.MovementModifiers;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

public interface MovementModifier {

  /***
   * Modifies a movement based on rules such as check
   *
   * @param piece that is referenced
   * @param finalTile the tile the piece is moving to
   * @param board that piece is on
   * @return Set of updated tiles after movement is modified
   */
  //TODO: some way to return piece's new square
  Set<ChessTile> updateMovement(Piece piece, ChessTile finalTile, ChessBoard board);
}
