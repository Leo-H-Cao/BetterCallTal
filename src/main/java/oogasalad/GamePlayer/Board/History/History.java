package oogasalad.GamePlayer.Board.History;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

/***
 * Record containing updates of a board
 *
 * @author Vincent Chen
 */
public record History(ChessBoard board, Set<Piece> movedPieces, Set<ChessTile> updatedTiles) {

  /***
   * @return information in record
   */
  @Override
  public String toString() {
    return String.format("Moved pieces: %s; Updated tiles:%s", movedPieces, updatedTiles);
  }
}
