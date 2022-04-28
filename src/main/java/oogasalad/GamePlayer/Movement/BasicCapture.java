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
public class BasicCapture extends BasicMovementAbstract {

  /***
   * Creates a class representing a basic piece capture
   */
  public BasicCapture(List<Coordinate> possibleMoves, boolean infinite) {
    super(possibleMoves, infinite);
  }

  /***
   * Creates a class representing a basic piece capture with one coordinate provided
   */
  public BasicCapture(Coordinate possibleMove, boolean infinite) {
    super(possibleMove, infinite);
  }

  /**
   * Constructor for Jackson serialization and deserialization
   */
  public BasicCapture(){
    super();
  }

  /***
   * Returns all possible captures a piece can make
   *
   * @param piece to get captures from
   * @param board to move on
   * @return set of tiles the piece can capture on
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return getFromMovesMap(piece, board, CAPTURE_KEY);
  }
}
