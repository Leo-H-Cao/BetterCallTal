package oogasalad.GamePlayer.GamePiece;

import java.util.Collection;
import java.util.List;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessTile;
import oogasalad.GamePlayer.UnboundedMovement;

@Deprecated
public class ChessPiece implements UnboundedMovement {


  /**
   * set rules for bounded movement of a piece (e.g. knight). For setup purposes, the piece is placed at (0,0).
   * @param allowedMovement list of relative coordinates to which this piece may move (e.g. [(-1,-2), (-1,2), ... for a knight)
   */
  @Override
  public void setBoundedMovementRules(List<Coordinate> allowedMovement) {

  }

  /**
   * set rules for unbounded movement of a piece (e.g. queen). For setup purposes, the piece is placed at (0,0).
   * @param direction list of relative coordinates to which this piece may move (e.g. call with (-1,-1), then (-1,0), etc. for a queen)
   */
  @Override
  public void setUnboundedMovementPattern(Coordinate direction) {

  }

  /**
   * calculates all reachable squares for a piece in a given direction
   * @param position position of the piece
   * @param direction direction to search for movement
   * @param board current chessboard
   * @return a collection of chess tiles that are reachable in the given direction
   */
  @Override
  public Collection<ChessTile> seekDirection(Coordinate position, Coordinate direction, ChessBoard board) {
    return null;
  }
}
