package oogasalad.GamePlayer;

import java.util.List;
import oogasalad.GamePlayer.Board.BoardCoordinate;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessTile;

public interface BoundedMovement {



  void setBoundedMovementRules(List<BoardCoordinate> allowedMovement);

}
