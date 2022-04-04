package oogasalad.Editor.Movement.ClassicPieceMovement;

import java.util.Collection;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Editor.Movement.Coordinate;
import oogasalad.GamePlayer.UnboundedMovement;

@Deprecated
public class Knight implements UnboundedMovement {

  @Override
  public void setBoundedMovementRules(List<Coordinate> allowedMovement) {

  }

  @Override
  public void setUnboundedMovementPattern(Coordinate direction) {

  }

  @Override
  public Collection<ChessTile> seekDirection(Coordinate position, Coordinate direction, ChessBoard board) {
    return null;
  }
}
