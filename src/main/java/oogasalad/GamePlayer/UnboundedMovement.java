package oogasalad.GamePlayer;

import java.util.Collection;
import oogasalad.GamePlayer.Board.BoardCoordinate;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessTile;

public interface UnboundedMovement extends BoundedMovement{

  void setUnboundedMovementPattern(BoardCoordinate direction);

  Collection<ChessTile> seekDirection(BoardCoordinate position, BoardCoordinate direction, ChessBoard board);


}
