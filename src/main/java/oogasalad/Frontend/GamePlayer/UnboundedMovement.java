package oogasalad.Frontend.GamePlayer;

import java.util.Collection;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Frontend.GamePlayer.Movement.Coordinate;

@Deprecated
public interface UnboundedMovement extends BoundedMovement{

  void setUnboundedMovementPattern(Coordinate direction);

  Collection<ChessTile> seekDirection(Coordinate position, Coordinate direction, ChessBoard board);

}
