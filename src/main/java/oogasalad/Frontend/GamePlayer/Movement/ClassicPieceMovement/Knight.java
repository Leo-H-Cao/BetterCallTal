package oogasalad.Frontend.GamePlayer.Movement.ClassicPieceMovement;

import java.util.Collection;
import java.util.List;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Frontend.GamePlayer.UnboundedMovement;
import oogasalad.Frontend.GamePlayer.Movement.Coordinate;

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
