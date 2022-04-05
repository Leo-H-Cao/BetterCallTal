package oogasalad.GamePlayer;

import java.util.Collection;
import oogasalad.Editor.Movement.Coordinate;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;

public interface UnboundedMovement extends BoundedMovement{

  void setUnboundedMovementPattern(Coordinate direction);

  Collection<ChessTile> seekDirection(Coordinate position, Coordinate direction, ChessBoard board);


}
