package oogasalad.GamePlayer.Board;

import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

public record History(ChessBoard board, Set<Piece> movedPieces, Set<ChessTile> updatedTiles) {

  @Override
  public String toString() {
    return String.format("Moved pieces: %s; Updated tiles:%s", movedPieces, updatedTiles);
  }
}
