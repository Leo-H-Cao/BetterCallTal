package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.MovementHandler;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;

public class Demote implements TileAction {

  private static final MovementHandler pawnMoves = new MovementHandler(
      List.of(new Movement(List.of(new Coordinate(0, 1)), false)),
      List.of(new Movement(List.of(new Coordinate(1, 1), new Coordinate(1, -1)), false)),
      Collections.emptyList()
  );
  @Override

  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) {
    tile.getPieces().stream()
        .filter(Piece::isTargetPiece)
        .forEach(p -> p.setMovement(pawnMoves));

    return Set.of(tile);
  }
}
