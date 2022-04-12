package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.MovementHandler;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementInterface;

public class PromotionQueen implements TileAction {

  private static final List<MovementInterface> queen = List.of(new Movement(List.of(
      new Coordinate(1, 0), new Coordinate(-1, 0), new Coordinate(0, 1), new Coordinate(0, -1),
      new Coordinate(1, 1), new Coordinate(1, -1), new Coordinate(-1, 1), new Coordinate(-1, -1)),
      true));

  private static final MovementHandler queenMoves = new MovementHandler(queen, queen, Collections.emptyList());

  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) {
    tile.getPieces().forEach(p -> p.setMovement(queenMoves));
    return Set.of(tile);
  }
}
