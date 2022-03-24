package oogasalad.GamePlayer;

import java.util.Collection;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessTile;

public interface UnboundedMovement {

  void setMovementDirection(int relativeX, int relativeY);

  Collection<ChessTile> reachableSquareSet(int relativeX, int relativeY, int xPosition, int yPosition, ChessBoard board);


}
