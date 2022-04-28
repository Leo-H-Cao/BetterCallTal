package oogasalad.GamePlayer.Movement;

import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

/***
 * Basic piece movement given relative coordinates
 *
 * @author Vincent Chen
 */
public class BasicMovement extends BasicMovementAbstract {

  /***
   * Creates a class representing a basic piece movement
   */
  public BasicMovement(List<Coordinate> possibleMoves, boolean infinite) {
    super(possibleMoves, infinite);
  }

  /***
   * Creates a class representing a basic piece movement with one coordinate provided
   */
  public BasicMovement(Coordinate possibleMove, boolean infinite) {
    super(possibleMove, infinite);
  }

  /**
   * Constructor for Jackson serialization and deserialization
   */
  public BasicMovement(){
    super();
  }

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @param board to move on
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return getFromMovesMap(piece, board, MOVE_KEY);
  }
}
